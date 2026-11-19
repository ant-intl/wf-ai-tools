package validation;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import okhttp3.*;
import okio.Buffer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import validation.wf.client.WfApiClient;
import validation.wf.client.WfRequestContext;
import validation.wf.config.WfClientConfig;
import validation.wf.security.WfSignatureUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import static org.junit.Assert.*;

/** Official fixtures plus actual request construction and RSA verification; no network. */
@RunWith(Parameterized.class)
public class ExtendedModuleContractTest {
    private static final JSONObject DATA;
    private static final KeyPair KEYS;
    static {
        try {
            Scanner scanner = new Scanner(ExtendedModuleContractTest.class.getResourceAsStream("/contracts.json"), "UTF-8").useDelimiter("\\A");
            DATA = JSON.parseObject(scanner.next());
            scanner.close();
            KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
            gen.initialize(2048);
            KEYS = gen.generateKeyPair();
        } catch (Exception e) { throw new ExceptionInInitializerError(e); }
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> cases() {
        List<Object[]> result = new ArrayList<>();
        for (Object value : DATA.getJSONArray("contracts")) {
            JSONObject c = (JSONObject) value;
            result.add(new Object[]{c.getString("api"), c});
        }
        return result;
    }

    private final JSONObject c;
    public ExtendedModuleContractTest(String name, JSONObject c) { this.c = c; }

    private Class<?> model(String side) throws Exception {
        return Class.forName("validation.wf.model." + side + "." + c.getString(side + "Class"));
    }

    private static Object json(Object bean) { return JSON.parse(JSON.toJSONString(bean)); }

    private static void equivalent(Object expected, Object actual) {
        if (expected instanceof Map) {
            assertTrue(actual instanceof Map);
            // Optional cursors may be explicit null in WF examples; fastjson omits null fields.
            Set<Object> expectedKeys = new HashSet<>();
            Set<Object> actualKeys = new HashSet<>();
            for (Object key : ((Map<?, ?>) expected).keySet()) if (((Map<?, ?>) expected).get(key) != null) expectedKeys.add(key);
            for (Object key : ((Map<?, ?>) actual).keySet()) if (((Map<?, ?>) actual).get(key) != null) actualKeys.add(key);
            assertEquals(expectedKeys, actualKeys);
            for (Object key : ((Map<?, ?>) expected).keySet()) equivalent(((Map<?, ?>) expected).get(key), ((Map<?, ?>) actual).get(key));
        } else if (expected instanceof List) {
            assertTrue(actual instanceof List);
            assertEquals(((List<?>) expected).size(), ((List<?>) actual).size());
            for (int i = 0; i < ((List<?>) expected).size(); i++) equivalent(((List<?>) expected).get(i), ((List<?>) actual).get(i));
        } else if (expected instanceof Number && actual instanceof Number) {
            assertEquals(0, new java.math.BigDecimal(expected.toString()).compareTo(new java.math.BigDecimal(actual.toString())));
        } else { assertEquals(expected, actual); }
    }

    private Object call(JSONObject response, boolean tampered, WfRequestContext context,
                        AtomicReference<Request> observed) throws Exception {
        WfClientConfig config = WfClientConfig.builder().clientId("local-test-client")
                .privateKey(KEYS.getPrivate()).publicKey(KEYS.getPublic())
                .baseUrl("https://wf-validation.invalid").build();
        WfApiClient client = new WfApiClient(config);
        // Transport is replaced only in the test. Request construction and verification are real.
        OkHttpClient transport = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request request = chain.request();
            observed.set(request);
            assertEquals("POST", request.method());
            assertEquals(c.getString("endpoint"), request.url().encodedPath());
            Buffer buffer = new Buffer();
            request.body().writeTo(buffer);
            String body = buffer.readString(StandardCharsets.UTF_8);
            equivalent(c.getJSONObject("request"), JSON.parse(body));
            String signed = "POST " + c.getString("endpoint") + "\nlocal-test-client."
                    + request.header("Request-Time") + "." + body;
            assertTrue(WfSignatureUtil.verifyResponse(signed, KEYS.getPublic(), request.header("Signature")));
            String responseBody = response.toJSONString();
            String time = "2026-11-18T00:00:00Z";
            String signature = WfSignatureUtil.buildSignatureHeader("POST " + c.getString("endpoint")
                    + "\nlocal-test-client." + time + "." + responseBody, KEYS.getPrivate(), 2);
            return new Response.Builder().request(request).protocol(Protocol.HTTP_1_1).code(200).message("OK")
                    .header("Response-Time", time).header("Signature", signature)
                    .body(ResponseBody.create(tampered ? "{}" : responseBody, MediaType.get("application/json"))).build();
        }).build();
        Field http = WfApiClient.class.getDeclaredField("httpClient");
        http.setAccessible(true);
        http.set(client, transport);
        Class<?> serviceType = Class.forName("validation.wf.service." + c.getString("service"));
        Object service = serviceType.getConstructor(WfApiClient.class).newInstance(client);
        Object request = JSON.parseObject(c.getJSONObject("request").toJSONString(), model("request"));
        if (context == null) return serviceType.getMethod(c.getString("method"), model("request")).invoke(service, request);
        return serviceType.getMethod(c.getString("method"), model("request"), WfRequestContext.class).invoke(service, request, context);
    }

    @Test public void officialExamplesPreserveEveryFieldAndReachCorrectEndpoint() throws Exception {
        for (String side : Arrays.asList("request", "response")) {
            JSONObject expected = c.getJSONObject(side);
            equivalent(expected, json(JSON.parseObject(expected.toJSONString(), model(side))));
        }
        AtomicReference<Request> observed = new AtomicReference<>();
        equivalent(c.getJSONObject("response"), json(call(c.getJSONObject("response"), false, null, observed)));
        assertNull(observed.get().header("account-id"));
        assertNull(observed.get().header("access-token"));
    }

    @Test public void businessFailureIsReturnedWithoutInventingSuccessOrRetry() throws Exception {
        JSONObject failure = JSON.parseObject("{\"result\":{\"resultCode\":\"PARAM_ILLEGAL\",\"resultStatus\":\"F\",\"resultMessage\":\"local failure\"}}");
        equivalent(failure, json(call(failure, false, null, new AtomicReference<>())));
    }

    @Test public void unknownResultRemainsUnknown() throws Exception {
        JSONObject unknown = JSON.parseObject("{\"result\":{\"resultCode\":\"UNKNOWN_EXCEPTION\",\"resultStatus\":\"U\",\"resultMessage\":\"unknown\"}}");
        equivalent(unknown, json(call(unknown, false, null, new AtomicReference<>())));
    }

    @Test public void tamperedSignedResponseIsRejected() throws Exception {
        try {
            call(c.getJSONObject("response"), true, null, new AtomicReference<>());
            fail("tampered response accepted");
        } catch (InvocationTargetException e) {
            assertTrue(e.getCause() instanceof validation.wf.model.exception.WfException);
            assertEquals("验签失败", e.getCause().getMessage());
        }
    }

    @Test public void accountAndTokenContextAreRequestHeaders() throws Exception {
        AtomicReference<Request> account = new AtomicReference<>();
        call(c.getJSONObject("response"), false, WfRequestContext.forAccount("account-local-1"), account);
        assertEquals("account-local-1", account.get().header("account-id"));
        assertNull(account.get().header("access-token"));
        AtomicReference<Request> token = new AtomicReference<>();
        call(c.getJSONObject("response"), false, WfRequestContext.forAccessToken("token-local-1"), token);
        assertEquals("token-local-1", token.get().header("access-token"));
        assertNull(token.get().header("account-id"));
    }
}

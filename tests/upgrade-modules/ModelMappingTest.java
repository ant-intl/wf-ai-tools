package validation;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.math.BigDecimal;
import java.util.*;
import static org.junit.Assert.*;

/** Fill every documented model field, including fields absent from official examples. */
@RunWith(Parameterized.class)
public class ModelMappingTest {
    private static final Map<String, JSONObject> MODELS = new LinkedHashMap<>();
    private static final Map<String, String> PACKAGES = new HashMap<>();
    static {
        Scanner scanner = new Scanner(ModelMappingTest.class.getResourceAsStream("/contracts.json"), "UTF-8").useDelimiter("\\A");
        JSONObject models = JSON.parseObject(scanner.next()).getJSONObject("models");
        scanner.close();
        for (String path : models.keySet()) {
            String className = path.substring(path.lastIndexOf('/') + 1).replace(".java", "");
            MODELS.put(className, models.getJSONObject(path));
            PACKAGES.put(className, "validation.wf." + path.substring(path.indexOf("java/") + 5).replace('/', '.').replace(".java", ""));
        }
    }
    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> cases() {
        List<Object[]> result = new ArrayList<>();
        for (String name : MODELS.keySet()) result.add(new Object[]{name});
        return result;
    }
    private final String name;
    public ModelMappingTest(String name) { this.name = name; }

    private static Object value(String type) {
        switch (type) {
            case "String": return "FUTURE_VALUE_2026-11-18T00:00:00+08:00";
            case "Long": return 5000000000L;
            case "Integer": return 3;
            case "Boolean": return true;
            case "BigDecimal": return new BigDecimal("1.123456");
            case "Map<String, Object>": return JSON.parseObject("{\"custom\":{\"enabled\":true,\"number\":7}}");
            case "Amount": return JSON.parseObject("{\"currency\":\"USD\",\"value\":5000000000}");
            case "Address": return JSON.parseObject("{\"region\":\"SG\",\"state\":\"SG\",\"city\":\"SG\",\"address1\":\"a\",\"address2\":\"b\",\"zipCode\":\"123456\"}");
            case "UserName": return JSON.parseObject("{\"firstName\":\"Test\",\"middleName\":\"M\",\"lastName\":\"User\",\"fullName\":\"Test M User\"}");
            case "Result": return JSON.parseObject("{\"resultStatus\":\"U\",\"resultCode\":\"FUTURE_CODE\",\"resultMessage\":\"unknown\"}");
            default:
                if (type.startsWith("List<")) return Collections.singletonList(value(type.substring(5, type.length() - 1)));
                JSONObject schema = MODELS.get(type);
                assertNotNull("Missing schema: " + type, schema);
                JSONObject object = new JSONObject();
                for (String field : schema.keySet()) object.put(field, value(schema.getString(field)));
                return object;
        }
    }

    @Test public void allFieldsRoundTripWithoutLossAndToStringIsRedacted() throws Exception {
        JSONObject expected = (JSONObject) value(name);
        Object bean = JSON.parseObject(expected.toJSONString(), Class.forName(PACKAGES.get(name)));
        assertEquals(expected, JSON.parseObject(JSON.toJSONString(bean)));
        assertEquals(name + "[REDACTED]", bean.toString());
    }
}

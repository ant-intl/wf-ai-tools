<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Client;

use InvalidArgumentException;
use {baseNamespace}\Wf\Model\Exception\WfErrorCode;
use {baseNamespace}\Wf\Model\Exception\WfException;
use {baseNamespace}\Wf\Model\Response\Result;
use {baseNamespace}\Wf\Model\WfModel;
use {baseNamespace}\Wf\Util\WfHttpClient;

/** 所有主动调用 API Client 的公共入口。 */
abstract class AbstractWfClient
{
    protected WfHttpClient $httpClient;

    public function __construct(WfHttpClient $httpClient)
    {
        $this->httpClient = $httpClient;
    }

    /**
     * @template T of WfModel
     * @param class-string<T> $responseClass
     * @return T
     */
    protected function post(string $apiPath, WfModel $request, string $responseClass): WfModel
    {
        try {
            if (method_exists($request, 'validate')) {
                $request->validate();
            }
        } catch (InvalidArgumentException $exception) {
            throw new WfException(WfErrorCode::PARAM_ILLEGAL, $exception->getMessage(), $exception);
        }

        try {
            $response = $responseClass::fromArray($this->httpClient->postJson($apiPath, $request));
        } catch (InvalidArgumentException $exception) {
            throw new WfException(WfErrorCode::INVALID_RESPONSE_FORMAT, 'Invalid WorldFirst response model.', $exception);
        }

        $result = $response->get('result');
        if (!$result instanceof Result || !is_string($result->resultStatus) || $result->resultStatus === '') {
            throw new WfException(WfErrorCode::INVALID_RESPONSE_FORMAT, 'WorldFirst response result is missing.');
        }

        if (in_array($result->resultStatus, ['F', 'U'], true)) {
            throw new WfException(
                WfErrorCode::fromCode($result->resultCode),
                is_string($result->resultMessage) ? $result->resultMessage : 'WorldFirst API call failed.'
            );
        }
        if ($result->resultStatus !== 'S') {
            throw new WfException(WfErrorCode::INVALID_RESPONSE_FORMAT, 'Unknown WorldFirst resultStatus.');
        }

        return $response;
    }
}

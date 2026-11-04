<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Exception;

use RuntimeException;
use Throwable;

final class WfException extends RuntimeException
{
    private WfErrorCode $errorCode;

    public function __construct(WfErrorCode $errorCode, string $message, ?Throwable $previous = null)
    {
        parent::__construct($message, 0, $previous);
        $this->errorCode = $errorCode;
    }

    public function getErrorCode(): WfErrorCode
    {
        return $this->errorCode;
    }

    public function isRetryable(): bool
    {
        return $this->errorCode->isRetryable();
    }
}

<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Response;

use {baseNamespace}\Wf\Model\WfModel;

final class Result extends WfModel
{
    protected const FIELDS = ['resultStatus', 'resultCode', 'resultMessage'];

    public static function success(): self
    {
        return new self([
            'resultStatus' => 'S',
            'resultCode' => 'SUCCESS',
            'resultMessage' => 'success',
        ]);
    }
}

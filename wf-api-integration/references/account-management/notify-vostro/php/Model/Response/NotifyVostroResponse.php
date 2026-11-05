<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Response;

use {baseNamespace}\Wf\Model\WfModel;

final class NotifyVostroResponse extends WfModel
{
    protected const FIELDS = ['result'];
    protected const RELATIONS = ['result' => Result::class];

    public static function success(): self
    {
        return new self(['result' => Result::success()]);
    }
}

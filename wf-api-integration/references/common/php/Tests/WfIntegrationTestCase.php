<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Tests;

use PHPUnit\Framework\TestCase;
use {baseNamespace}\Wf\Config\WfConfig;
use {baseNamespace}\Wf\Signer\WfSigner;
use {baseNamespace}\Wf\Util\WfHttpClient;

abstract class WfIntegrationTestCase extends TestCase
{
    protected function setUp(): void
    {
        parent::setUp();
        foreach (['WF_CLIENT_ID', 'WF_PRIVATE_KEY_PATH', 'WF_PUBLIC_KEY_PATH'] as $name) {
            if ($this->env($name) === '') {
                self::markTestSkipped('Set WF credentials in environment variables before running integration tests.');
            }
        }
    }

    protected function httpClient(): WfHttpClient
    {
        $config = new WfConfig();

        return new WfHttpClient($config, new WfSigner($config));
    }

    protected function env(string $name): string
    {
        $value = getenv($name);

        return $value === false ? '' : $value;
    }
}

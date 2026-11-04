<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model;

use ArrayAccess;
use InvalidArgumentException;
use JsonSerializable;

/**
 * API 模型基类。
 *
 * 子类通过 FIELDS 声明 API 字段，通过 RELATIONS 声明嵌套模型；字段名保持
 * WorldFirst JSON 契约中的 camelCase，null 字段不参与序列化。
 *
 * @implements ArrayAccess<string, mixed>
 */
abstract class WfModel implements JsonSerializable, ArrayAccess
{
    protected const FIELDS = [];

    /** 单对象使用 Model::class，对象数组使用 [Model::class]。 */
    protected const RELATIONS = [];

    /** @var array<string, mixed> */
    private array $attributes = [];

    public function __construct(array $data = [])
    {
        foreach ($data as $name => $value) {
            $this->set($name, $value);
        }
    }

    public static function fromArray(?array $data): static
    {
        $model = new static();
        foreach ($data ?? [] as $name => $value) {
            // 与其他语言模板一致，忽略服务端后续新增的可选字段。
            if (in_array($name, static::FIELDS, true)) {
                $model->set($name, $value);
            }
        }

        return $model;
    }

    public function get(string $name): mixed
    {
        $this->assertField($name);

        return $this->attributes[$name] ?? null;
    }

    public function set(string $name, mixed $value): static
    {
        $this->assertField($name);
        $this->attributes[$name] = $this->hydrate($name, $value);

        return $this;
    }

    protected function requireString(string $name): string
    {
        $value = $this->get($name);
        if (!is_string($value) || $value === '') {
            throw new InvalidArgumentException(sprintf('%s must be a non-empty string.', $name));
        }

        return $value;
    }

    protected function optionalString(string $name): ?string
    {
        $value = $this->get($name);
        if ($value !== null && !is_string($value)) {
            throw new InvalidArgumentException(sprintf('%s must be a string.', $name));
        }

        return $value;
    }

    public function __get(string $name): mixed
    {
        return $this->get($name);
    }

    public function __set(string $name, mixed $value): void
    {
        $this->set($name, $value);
    }

    public function __isset(string $name): bool
    {
        return isset($this->attributes[$name]);
    }

    public function offsetExists(mixed $offset): bool
    {
        return is_string($offset) && isset($this->attributes[$offset]);
    }

    public function offsetGet(mixed $offset): mixed
    {
        return $this->get((string) $offset);
    }

    public function offsetSet(mixed $offset, mixed $value): void
    {
        if (!is_string($offset)) {
            throw new InvalidArgumentException('Model field name must be a string.');
        }
        $this->set($offset, $value);
    }

    public function offsetUnset(mixed $offset): void
    {
        if (is_string($offset)) {
            $this->assertField($offset);
            unset($this->attributes[$offset]);
        }
    }

    public function toArray(): array
    {
        $data = [];
        foreach (static::FIELDS as $field) {
            if (!array_key_exists($field, $this->attributes) || $this->attributes[$field] === null) {
                continue;
            }
            $data[$field] = $this->normalize($this->attributes[$field]);
        }

        return $data;
    }

    public function jsonSerialize(): array
    {
        return $this->toArray();
    }

    private function assertField(string $name): void
    {
        if (!in_array($name, static::FIELDS, true)) {
            throw new InvalidArgumentException(sprintf('Unknown field "%s" on %s.', $name, static::class));
        }
    }

    private function hydrate(string $name, mixed $value): mixed
    {
        $relation = static::RELATIONS[$name] ?? null;
        if ($relation === null || $value === null || $value instanceof self) {
            return $value;
        }

        if (is_string($relation)) {
            if (!is_array($value)) {
                throw new InvalidArgumentException(sprintf('Field "%s" expects an object.', $name));
            }

            return $relation::fromArray($value);
        }

        if (!is_array($value)) {
            throw new InvalidArgumentException(sprintf('Field "%s" expects an array.', $name));
        }

        $modelClass = $relation[0];
        foreach ($value as $item) {
            if (!$item instanceof WfModel && !is_array($item)) {
                throw new InvalidArgumentException(sprintf('Field "%s" expects an array of objects.', $name));
            }
        }

        return array_map(
            static fn (mixed $item): WfModel => $item instanceof WfModel
                ? $item
                : $modelClass::fromArray($item),
            $value
        );
    }

    private function normalize(mixed $value): mixed
    {
        if ($value instanceof self) {
            return $value->toArray();
        }
        if (is_array($value)) {
            return array_map(fn (mixed $item): mixed => $this->normalize($item), $value);
        }

        return $value;
    }
}

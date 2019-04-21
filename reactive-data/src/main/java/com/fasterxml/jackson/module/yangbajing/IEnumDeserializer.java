package com.fasterxml.jackson.module.yangbajing;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.util.EnumResolver;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class IEnumDeserializer extends StdScalarDeserializer<Enum<?>> implements ContextualDeserializer {
    private final Enum<?>[] enums;
    private final EnumResolver enumResolver;

    public IEnumDeserializer(EnumResolver byNameResolver) {
        super(byNameResolver.getEnumClass());
        this.enumResolver = byNameResolver;
        this.enums = enumResolver.getRawEnums();
    }

    @Override
    public Enum<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Optional<Method> maybe = Arrays.stream(enumResolver.getEnumClass().getMethods())
                .filter(method -> method.getAnnotation(JsonValue.class) != null && method.getReturnType() == Integer.class)
                .findFirst();
        for (Enum<?> en : enums) {
            Enum<?> anEnum;
            if (maybe.isPresent()) {
                anEnum = findEnum(p, maybe.get(), en);
            } else {
                anEnum = en.toString().equals(p.getValueAsString()) ? en : null;
            }
            if (Objects.nonNull(anEnum))
                return anEnum;
        }

        throw new JsonParseException(p, "枚举值解析失败");
    }

    private Enum<?> findEnum(JsonParser p, Method method, Enum<?> en) {
        try {
            Class<?> rt = method.getReturnType();
            if (Integer.class.isAssignableFrom(rt)) {
                Integer value = (Integer) method.invoke(en);
                if (value == p.getIntValue()) {
                    return en;
                }
            } else {
                String value = p.getValueAsString();
                if (en.toString().equals(value)) {
                    return en;
                }
            }
            return null;
        } catch (IllegalAccessException | InvocationTargetException | IOException e) {
            log.error("findEnum error {}", e.getLocalizedMessage(), e);
            return null;
        }
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        return this;
    }

}

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
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class EnumDeserializer extends StdScalarDeserializer<Enum<?>> implements ContextualDeserializer {
    private static final long serialVersionUID = 1L;
    private final Enum<?>[] enums;
    private final EnumResolver enumResolver;

    public EnumDeserializer(EnumResolver byNameResolver) {
        super(byNameResolver.getEnumClass());
        this.enumResolver = byNameResolver;
        this.enums = enumResolver.getRawEnums();
    }

    @Override
    public Enum<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Class<Enum<?>> t = enumResolver.getEnumClass();
        Optional<Method> maybe = Arrays.stream(t.getMethods())
                .filter(method -> method.getAnnotation(JsonValue.class) != null && method.getReturnType() == Integer.class)
                .findFirst();
        if (!maybe.isPresent()) {
            Optional<Field> dealEnumType = getDealEnumType(t);
            if (dealEnumType.isPresent()) {
                Field field = dealEnumType.get();
                try {
                    maybe = Optional.of(t.getDeclaredMethod(getMethodCapitalize(field, field.getName())));
                } catch (NoSuchMethodException e) {
                    throw new JsonParseException(p, "@JsonValue字段需要定义 get/is 方法");
                }
            }
        }

        for (Enum<?> en : enums) {
            Enum<?> anEnum;
            if (maybe.isPresent()) {
                anEnum = findEnum(p, maybe.get(), en);
            } else {
                anEnum = en.toString().equals(p.getValueAsString()) ? en : null;
            }
            if (Objects.nonNull(anEnum)) {
                return anEnum;
            }
        }

        throw new JsonParseException(p, "枚举值解析失败");
    }

    private Enum<?> findEnum(JsonParser p, Method method, Enum<?> en) throws JsonParseException {
        try {
            Class<?> rt = method.getReturnType();
            if (Integer.class.isAssignableFrom(rt) || int.class.isAssignableFrom(rt)) {
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
            throw new JsonParseException(p, String.format("Failed to get an enum value from a %s.", method), e);
        }
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        return this;
    }

    public static Optional<Field> getDealEnumType(Class<?> clazz) {
        return clazz.isEnum() ? Arrays.stream(clazz.getDeclaredFields()).filter(field -> field.isAnnotationPresent(JsonValue.class)).findFirst() : Optional.empty();
    }

    public static String getMethodCapitalize(Field field, final String str) {
        Class<?> fieldType = field.getType();
        return (boolean.class.equals(fieldType) ? "is" : "get") + StringUtils.capitalize(str);
    }
}

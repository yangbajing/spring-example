package com.fasterxml.jackson.module.yangbajing;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.util.EnumResolver;

public class EnumDeserializers extends Deserializers.Base {
    @Override
    public JsonDeserializer<?> findEnumDeserializer(Class<?> type, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
        if (Enum.class.isAssignableFrom(type)) {
            return new EnumDeserializer(EnumResolver.constructUnsafe(type, config.getAnnotationIntrospector()));
        }
        return super.findEnumDeserializer(type, config, beanDesc);
    }
}

package com.fasterxml.jackson.module.yangbajing;

import com.baomidou.mybatisplus.core.enums.IEnum;
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

import java.io.IOException;
import java.util.Arrays;

public class IEnumDeserializer extends StdScalarDeserializer<IEnum<Integer>> implements ContextualDeserializer {
    private final IEnum<Integer>[] enums;
    private final EnumResolver enumResolver;

    public IEnumDeserializer(EnumResolver byNameResolver) {
        super(byNameResolver.getEnumClass());
        this.enumResolver = byNameResolver;
        this.enums = (IEnum<Integer>[]) enumResolver.getRawEnums();
    }

    @Override
    public IEnum<Integer> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        int value = p.getIntValue();

        return Arrays.stream(this.enums).filter(e -> e.getValue() == value).findFirst()
                .orElseThrow(() -> new JsonParseException(p, "枚举需要为整数类型"));
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        return this;
    }

}

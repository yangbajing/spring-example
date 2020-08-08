package com.fasterxml.jackson.module.yangbajing;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class YangbajingModule extends SimpleModule {
    @Override
    public String getModuleName() {
        return "YangbajingModule";
    }

    @Override
    public Version version() {
        return Version.unknownVersion();
    }

    @Override
    public void setupModule(SetupContext context) {
        context.addDeserializers(new EnumDeserializers());
    }
}


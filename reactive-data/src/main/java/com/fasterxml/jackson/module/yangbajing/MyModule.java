package com.fasterxml.jackson.module.yangbajing;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;

public class MyModule extends Module {
    @Override
    public String getModuleName() {
        return "MyModule";
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


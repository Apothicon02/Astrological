package com.Apothic0n.Inversia.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
    public static ForgeConfigSpec.BooleanValue teleporting;
    public static void registerCommonConfig(ForgeConfigSpec.Builder COMMON_BUILDER) {
        COMMON_BUILDER.comment("General settings for Inversia").push("common");

        teleporting = COMMON_BUILDER
                .comment("Enable default methods of entering / leaving this dimension. Default: true")
                .define("teleporting", true);

        COMMON_BUILDER.pop();
    }
}
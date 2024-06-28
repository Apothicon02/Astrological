package com.Apothic0n.Astrological.api;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.minecraftforge.fml.loading.FMLPaths;

import javax.annotation.Nullable;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AstrologicalJsonReader {
    //common
    public static boolean spawnInOuterEnd = true;
    public static boolean weatherBasedLevitationEffects = true;
    public static boolean endChestGeneratesBeneathGateways = true;

    //client
    public static boolean removeMippingOnTransparentBlocksToFixSleepBlocksNotRenderingPast50Blocks = true;
    public static boolean customEndLighting = true;
    public static boolean customEndSky = true;

    public static void main() throws Exception {
        makeAndReadCommonConfig(Path.of(FMLPaths.CONFIGDIR.get().toString() + "/astrol-common.json"));
        makeAndReadClientConfig(Path.of(FMLPaths.CONFIGDIR.get().toString() + "/astrol-client.json"));
    }

    public static void makeAndReadCommonConfig(Path path) throws IOException {
        Gson gson = new Gson();
        if (!Files.exists(path)) {
            JsonWriter writer = new JsonWriter(new FileWriter(path.toString()));
            JsonObject defaultData = gson.fromJson("{\"spawnInOuterEnd\":\"true\", \"weatherBasedLevitationEffects\":\"true\", \"endChestGeneratesBeneathGateways\":\"true\"}", JsonObject.class);
            gson.toJson(defaultData, writer);
            writer.close();
        }
        JsonReader reader = new JsonReader(new FileReader(path.toString()));
        JsonObject data = gson.fromJson(reader, JsonObject.class);
        if (data.get("spawnInOuterEnd") != null) {
            spawnInOuterEnd = data.get("spawnInOuterEnd").getAsBoolean();
        } else {
            data.addProperty("spawnInOuterEnd", spawnInOuterEnd);
        }
        if (data.get("weatherBasedLevitationEffects") != null) {
            weatherBasedLevitationEffects = data.get("weatherBasedLevitationEffects").getAsBoolean();
        } else {
            data.addProperty("weatherBasedLevitationEffects", weatherBasedLevitationEffects);
        }
        if (data.get("endChestGeneratesBeneathGateways") != null) {
            endChestGeneratesBeneathGateways = data.get("endChestGeneratesBeneathGateways").getAsBoolean();
        } else {
            data.addProperty("endChestGeneratesBeneathGateways", endChestGeneratesBeneathGateways);
        }
        JsonWriter writer = new JsonWriter(new FileWriter(path.toString()));
        gson.toJson(data, writer);
        writer.close();
    }
    public static void makeAndReadClientConfig(Path path) throws IOException {
        Gson gson = new Gson();
        if (!Files.exists(path)) {
            JsonWriter writer = new JsonWriter(new FileWriter(path.toString()));
            JsonObject defaultData = gson.fromJson("{\"removeMippingOnTransparentBlocksToFixSleepBlocksNotRenderingPast50Blocks\":\"true\", \"customEndLighting\":\"true\", \"customEndSky\":\"true\"}", JsonObject.class);
            gson.toJson(defaultData, writer);
            writer.close();
        }
        JsonReader reader = new JsonReader(new FileReader(path.toString()));
        JsonObject data = gson.fromJson(reader, JsonObject.class);
        if (data.get("removeMippingOnTransparentBlocksToFixSleepBlocksNotRenderingPast50Blocks") != null) {
            removeMippingOnTransparentBlocksToFixSleepBlocksNotRenderingPast50Blocks = data.get("removeMippingOnTransparentBlocksToFixSleepBlocksNotRenderingPast50Blocks").getAsBoolean();
        } else {
            data.addProperty("removeMippingOnTransparentBlocksToFixSleepBlocksNotRenderingPast50Blocks", removeMippingOnTransparentBlocksToFixSleepBlocksNotRenderingPast50Blocks);
        }
        if (data.get("customEndLighting") != null) {
            customEndLighting = data.get("customEndLighting").getAsBoolean();
        } else {
            data.addProperty("customEndLighting", customEndLighting);
        }
        if (data.get("customEndSky") != null) {
            customEndSky = data.get("customEndSky").getAsBoolean();
        } else {
            data.addProperty("customEndSky", customEndSky);
        }
        JsonWriter writer = new JsonWriter(new FileWriter(path.toString()));
        gson.toJson(data, writer);
        writer.close();
    }
}
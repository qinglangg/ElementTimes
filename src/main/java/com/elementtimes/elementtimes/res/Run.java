package com.elementtimes.elementtimes.res;

import com.google.common.io.Files;
import com.google.gson.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;



public class Run {

    private static final File blockstates = new File("D:\\projects\\MC\\Forge\\ElementTimes\\src\\main\\resources\\assets\\elementtimes\\blockstates");
    private static final File modelBlock = new File("D:\\projects\\MC\\Forge\\ElementTimes\\src\\main\\resources\\assets\\elementtimes\\models\\block");
    private static final File modelItem = new File("D:\\projects\\MC\\Forge\\ElementTimes\\src\\main\\resources\\assets\\elementtimes\\models\\item");

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static void main(String[] args) throws IOException {
        for (File file : new File("D:\\projects\\MC\\Forge\\ElementTimes\\src\\main\\resources\\assets\\elementtimes\\models\\block\\machine").listFiles()) {
            String name = file.getName();
            if (name.endsWith("_off.json")) {
                String machine = name.substring(0, name.length() - 9);
                File[] files = new File("D:\\projects\\MC\\Forge\\ElementTimes\\src\\main\\resources\\assets\\elementtimes\\models\\item").listFiles();
                boolean hasFile = false;
                System.out.println(machine);
                for (File itemFile : files) {
                    if ((machine + ".json").equals(itemFile.getName())) {
                        hasFile = true;
                        break;
                    }
                }
                if (!hasFile) {
                    File newFile = new File("D:\\projects\\MC\\Forge\\ElementTimes\\src\\main\\resources\\assets\\elementtimes\\models\\item\\" + machine + ".json");
                    JsonObject object = new JsonObject();
                    object.addProperty("parent", "elementtimes:block/machine/" + machine + "_off");
                    FileUtils.write(newFile, gson.toJson(object), StandardCharsets.UTF_8);
                }
            }
        }
        clear();
    }

    public static void fromForgeToVanilla() throws IOException {
        for (File file : blockstates.listFiles((file, name) -> name.endsWith(".json"))) {
            String block = file.getName().substring(0, file.getName().length() - 5);
            String json = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            JsonObject blockstate = gson.fromJson(json, JsonObject.class);
            if (!blockstate.has("forge_marker")) {
                continue;
            }
            JsonObject defaults = blockstate.getAsJsonObject("defaults");
            String model = defaults.get("model").getAsString();
            JsonObject textures = defaults.getAsJsonObject("textures");
            JsonObject variants = blockstate.getAsJsonObject("variants");
            if (variants.size() == 1 && variants.has("")) {
                JsonObject extra = variants.getAsJsonArray("").get(0).getAsJsonObject();
                if (extra.size() == 0) {
                    writeSimpleModel(file, block, model, textures);
                }
            } else if (variants.size() == 1 && variants.has("inventory")) {
                JsonObject extra = variants.getAsJsonArray("inventory").get(0).getAsJsonObject();
                if (extra.size() == 0) {
                    writeSimpleModel(file, block, model, textures);
                }
            }
        }
    }

    private static void fix() throws IOException {
        for (File file : blockstates.listFiles((file, name) -> name.endsWith(".json"))) {
            String json = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            JsonObject blockstate = gson.fromJson(json, JsonObject.class);
            if (blockstate.has("forge_marker") || blockstate.has("variants") || !blockstate.has("")) {
                continue;
            }
            JsonObject variants = new JsonObject();
            variants.add("", blockstate.get(""));
            blockstate.add("variants", variants);
            blockstate.remove("");
            file.delete();
            FileUtils.write(file, gson.toJson(blockstate), StandardCharsets.UTF_8);
        }
    }

    private static void writeSimpleModel(File file, String block, String model, JsonObject texture) throws IOException {
        // blockstate
        JsonObject newBlockstate = new JsonObject();
        JsonObject newDefModel = new JsonObject();
        newDefModel.addProperty("model", model);
        newBlockstate.add("", newDefModel);
        file.delete();
        FileUtils.write(file, gson.toJson(newBlockstate), StandardCharsets.UTF_8);
        // model
        File blockFile = new File(modelBlock, block + ".json");
        JsonObject newBlockModel = new JsonObject();
        newBlockModel.addProperty("parent", model);
        newBlockModel.add("textures", texture);
        FileUtils.write(blockFile, gson.toJson(newBlockModel), StandardCharsets.UTF_8);
        // item model
        File itemFile = new File(modelItem, block + ".json");
        JsonObject newItemModel = new JsonObject();
        newItemModel.addProperty("parent", "elementtimes:block/" + block);
        FileUtils.write(itemFile, gson.toJson(newItemModel), StandardCharsets.UTF_8);
    }

    public static void clear() throws IOException {
        FileUtils.deleteDirectory(new File("D:\\projects\\MC\\Forge\\ElementTimes\\out"));
        FileUtils.deleteDirectory(new File("D:\\projects\\MC\\Forge\\ElementTimes\\run\\crash-reports"));
        FileUtils.deleteDirectory(new File("D:\\projects\\MC\\Forge\\ElementTimes\\run\\logs"));
    }
}

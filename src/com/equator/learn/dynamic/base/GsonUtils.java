package com.equator.learn.dynamic.base;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class GsonUtils {
    private final static Gson GSON = new Gson();
    private final static JsonParser PARSER = new JsonParser();

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }

    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }

    public static JsonElement parseString(String json) {
        return PARSER.parse(json);
    }
}

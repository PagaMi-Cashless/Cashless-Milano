/**
 * @author Ayoub Chouak (a.chouak)
 * @file   JsonUtils.java
 * @brief  Json (de)serialization utilities
 */
package io.github.app.utils;

import com.google.gson.*;

public class JsonUtils
{
    private static Gson _gson = new GsonBuilder().create();

    public static String serialize(Object pod) {
        return _gson.toJson(pod);
    }

    public static <Ty> Ty parse(String json, Class<Ty> type) {
        return _gson.fromJson(json, type);
    }
}

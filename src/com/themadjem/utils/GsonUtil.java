package com.themadjem.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.Primitives;
import com.google.gson.stream.JsonReader;
import com.themadjem.Herd;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Utility for use along with Google's Gson library
 */
@SuppressWarnings("SameParameterValue")
public class GsonUtil {

    public static <T> T loadGson(String path, Class<T> classOfT) {
        Object res;
        try {
            res = new Gson().fromJson(new JsonReader(new FileReader(path)), classOfT);
            // TODO: 7/1/2017 Try to make json file part of jar 
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return Primitives.wrap(classOfT).cast(res);
    }

    public static void saveGson(String path, Herd herd) {
        try (Writer writer = new FileWriter(path)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(herd, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// --Commented out by Inspection START (7/1/2017 3:14 PM):
//    public <T> T loadGson(String path, Class<T> classOfT, T defaultObj) {
//        Object res;
//        try {
//            res = new Gson().fromJson(new JsonReader(new FileReader(path)), classOfT);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return defaultObj;
//        }
//        return Primitives.wrap(classOfT).cast(res);
//    }
// --Commented out by Inspection STOP (7/1/2017 3:14 PM)
}

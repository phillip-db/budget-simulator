package com.uiuc.budgetsimulator;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.google.gson.Gson;

public class Utils {

    public static <T> T fromJSON(Type typeToken, InputStream file) {
        T obj = null;
        try {
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(file, StandardCharsets.UTF_8);
            obj = gson.fromJson(reader, typeToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
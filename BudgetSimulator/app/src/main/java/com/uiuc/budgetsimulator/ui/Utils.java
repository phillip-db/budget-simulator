package com.uiuc.budgetsimulator.ui;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

    public static <T> T fromJSON(Class<T> objClass, InputStream file) {
        T obj = null;
        try {
            obj = new ObjectMapper().readValue(file, objClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static <T> ArrayList<T> objListFromFile(InputStream is)
    {
        try {
            ObjectInputStream ois = new ObjectInputStream(is);
            return (ArrayList<T>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
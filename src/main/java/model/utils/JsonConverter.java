package model.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonConverter {

    public static JsonObject getJson(String url) {

        JsonObject jsonObj = null;

        try {

            JsonParser json = new JsonParser();
            jsonObj = json.parse(jsonGetter(url).toString()).getAsJsonObject();

        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }

        return jsonObj;

    }

    public static JsonArray getJsonArray(String url) {

        JsonArray jsonObj = null;

        try {

            JsonParser json = new JsonParser();
            jsonObj = json.parse(jsonGetter(url).toString()).getAsJsonArray();

        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }

        return jsonObj;

    }

    private static StringBuilder jsonGetter(String url) {

        StringBuilder parsedJson = null;

        try {

            URL urlClass = new URL(url);
            HttpURLConnection urlC = (HttpURLConnection) urlClass.openConnection();
            urlC.setRequestMethod("GET");
            InputStream is = urlC.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);

            int readerBuffer = bis.read();

            parsedJson = new StringBuilder();

            while (readerBuffer != -1) {
                char u = (char) readerBuffer;
                parsedJson.append(u);
                readerBuffer = bis.read();
            }

            bis.close();
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return parsedJson;

    }

}

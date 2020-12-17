package model.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JsonConverter {

    public static JsonObject getJson(String url){

        JsonObject jsonObj = null;

        try {
            URL urlClass = new URL(url);

            HttpURLConnection urlC = (HttpURLConnection) urlClass.openConnection();
            urlC.setRequestMethod("GET");

            InputStream is = urlC.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);

            int readerBuffer = bis.read();

            StringBuilder parsedJson = new StringBuilder();

            while(readerBuffer != -1){
                char u = (char) readerBuffer;
                parsedJson.append(u);
                readerBuffer = bis.read();
            }

            bis.close();
            is.close();

            JsonParser json = new JsonParser();
            jsonObj = json.parse(parsedJson.toString()).getAsJsonObject();

        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException | NullPointerException ex) {
            System.out.println(ex.getMessage());
        } finally {
            return jsonObj;
        }

    }

    public static JsonArray getJsonArray(String url){

        JsonArray jsonObj = null;

        try {
            URL urlClass = new URL(url);

            HttpURLConnection urlC = (HttpURLConnection) urlClass.openConnection();
            urlC.setRequestMethod("GET");

            InputStream is = urlC.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);

            int readerBuffer = bis.read();

            StringBuilder parsedJson = new StringBuilder();

            while(readerBuffer != -1){
                char u = (char) readerBuffer;
                parsedJson.append(u);
                readerBuffer = bis.read();
            }

            bis.close();
            is.close();

            JsonParser json = new JsonParser();
            jsonObj = json.parse(parsedJson.toString()).getAsJsonArray();

        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException | NullPointerException ex) {
            System.out.println(ex.getMessage());
        } finally {
            return jsonObj;
        }

    }

}

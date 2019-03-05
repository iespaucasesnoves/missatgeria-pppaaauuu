package com.example.myapplication;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Controller {

    public static String logIn(String nom, String pass) {
        try {
            HashMap<String, String> claus = new HashMap<>();
            claus.put("nom", nom);
            claus.put("password", pass);
            String crida = montaParametres(claus);
            URL obj = new URL("https://iesmantpc.000webhostapp.com/public/login/");
            HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setDoOutput(true);
            postConnection.setReadTimeout(15000);
            postConnection.setConnectTimeout(25000);
            postConnection.setDoInput(true);
            OutputStream os = postConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(crida);
            writer.flush();
            writer.close();
            int responseCode = postConnection.getResponseCode();
            Log.d("DEVPAU", "POST Response Code :  " + responseCode);
            Log.d("DEVPAU", "POST Response Message : " + postConnection.getResponseMessage());
            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                Log.d("DEVPAU", response.toString());
                return response.toString();
            } else {
                Log.d("DEVPAU", "POST NOT WORKED");
            }
        }catch(IOException e){
            Log.d("DEVPAU", e.getMessage());
        }
        return "";
    }

    private static String montaParametres(HashMap<String, String> params) throws UnsupportedEncodingException {
        // A partir d'un hashmap clau-valor cream//
        // clau1=valor1&clau2=valor2&...
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first) {
                first = false;
            } else {
                result.append("&");
            }result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    public static JSONObject carregaMsgs(int clau){
        try {
            HashMap<String, String> claus = new HashMap<>();
            claus.put("id", Integer.toString(clau));
            String crida = montaParametres(claus);
            URL obj = new URL("https://iesmantpc.000webhostapp.com/public/missatge/");
            HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setDoOutput(true);
            postConnection.setReadTimeout(15000);
            postConnection.setConnectTimeout(25000);
            postConnection.setDoInput(true);
            OutputStream os = postConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(crida);
            writer.flush();
            writer.close();
            int responseCode = postConnection.getResponseCode();
            Log.d("DEVPAU", "POST Response Code :  " + responseCode);
            Log.d("DEVPAU", "POST Response Message : " + postConnection.getResponseMessage());
            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                Log.d("DEVPAU", response.toString());
                try {

                    return new JSONObject(response.toString());
                }catch(JSONException e){
                    Log.d("DEVPAU", e.getMessage());
                }
            } else {
                Log.d("DEVPAU", "POST NOT WORKED");
            }
        }catch(IOException e){
            Log.d("DEVPAU", e.getMessage());
        }
        return null;
    }

}

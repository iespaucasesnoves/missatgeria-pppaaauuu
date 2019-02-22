package com.example.myapplication;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Controller {

    public static void logIn(String nom, String pass) {
        try {
            final String POST_PARAMS = "{\\n\"nom\"=" + nom + "\\r\\n" + "\"password\"=" + pass +"\\n}";
            URL obj = new URL("https://iesmantpc.000webhostapp.com/public/login/");
            HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setRequestProperty("nom", nom);
            postConnection.setRequestProperty("password", pass);
            postConnection.setDoOutput(true);
            OutputStream os = postConnection.getOutputStream();
            os.write(POST_PARAMS.getBytes());
            os.flush();
            os.close();
            //int responseCode = postConnection.getResponseCode();
           // Log.d("DEVPAU", "POST Response Code :  " + responseCode);
            Log.d("DEVPAU", "POST Response Message : " + postConnection.getResponseMessage());
           // if (responseCode == HttpURLConnection.HTTP_CREATED) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                Log.d("DEVPAU", response.toString());
           // } else {
                Log.d("DEVPAU", "POST NOT WORKED");
          //  }
        }catch(IOException e){
            Log.d("DEVPAU", e.getMessage());
        }
    }
}

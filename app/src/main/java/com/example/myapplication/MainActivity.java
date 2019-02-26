package com.example.myapplication;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Preferencies pref;
    final int CLAULOGIN = 2;
    JSONObject jslogin = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = new Preferencies(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String login = Controller.logIn(pref.getUser(), pref.getPassword());
        try{
            jslogin = new JSONObject(login);
            if(jslogin.get("correcta").equals("false")) {
                Intent inte = new Intent(this, Login.class);
                inte.putExtra("nom", pref.getUser());
                startActivityForResult(inte, CLAULOGIN);
            }
        }catch(JSONException e){
            Log.d("DEVPAU", e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == CLAULOGIN) {
            if (data.hasExtra("nom")) {
                pref.setUser(data.getExtras().getString("nom"));
            }
            if (data.hasExtra("password")) {
                pref.setPassword(data.getExtras().getString("password"));
            }
        }
        String login = Controller.logIn(pref.getUser(), pref.getPassword());
        try{
            jslogin = new JSONObject(login);
            if(jslogin.get("correcta").equals("false")){
                Intent inte = new Intent(this, Login.class);
                startActivityForResult(inte, CLAULOGIN);
            }else{
                pref.setToken(jslogin.get("token").toString());
            }
        }catch(JSONException e){
            Log.d("DEVPAU", e.getMessage());
        }
    }




}
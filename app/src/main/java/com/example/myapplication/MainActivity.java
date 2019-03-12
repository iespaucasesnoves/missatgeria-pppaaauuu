package com.example.myapplication;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Preferencies pref;
    private ListView lv;
    final int CLAULOGIN = 2;
    private ArrayList<Missatge> missatges;
    JSONObject jslogin = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = new Preferencies(this);
        lv = findViewById(R.id.llista);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String login = Controller.logIn(pref.getUser(), pref.getPassword());
        try{
            jslogin = new JSONObject(login);
            if(jslogin == null ||  jslogin.get("correcta").equals("false")) {
                Intent inte = new Intent(this, Login.class);
                inte.putExtra("nom", pref.getUser());
                startActivityForResult(inte, CLAULOGIN);
            }else{
                pref.setToken(jslogin.get("token").toString());
                mostraMissatges();
            }
        }catch(JSONException e){
            Log.d("DEVPAU", e.getMessage());
        }catch(Exception e){
            Log.d("DEVPAU", e.getMessage());
            Intent inte = new Intent(this, Login.class);
            inte.putExtra("nom", pref.getUser());
            startActivityForResult(inte, CLAULOGIN);
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
                inte.putExtra("nom", pref.getUser());
                startActivityForResult(inte, CLAULOGIN);
            }else{
                pref.setToken(jslogin.get("token").toString());
                mostraMissatges();
            }
        }catch(JSONException e){
            Log.d("DEVPAU", e.getMessage());
        }
    }

    public void mostraMissatges(){
        enviarMissatges();

        Controller.carregaMsgs(pref.getCodiusuari(), pref.getToken());
        DataSourceMsg dataSource = new DataSourceMsg(this);
        try {
            dataSource.open();
            missatges = dataSource.getAllMsg();
            ArrayAdapter<Missatge> adap = new MissatgeArrayAdapter(this, R.layout.missatge_a_llista, missatges);
            lv.setAdapter(adap);
            dataSource.close();
        }catch(SQLException e){
            Log.d("DEVPAU", e.getMessage());
        }

    }

    public void enviarMissatges(){
        DataSourceMsg dataSource = new DataSourceMsg(this);
        ArrayList<Missatge> pendents = new ArrayList<>();
        pendents = dataSource.getAllPdt();
        if(pendents != null && pendents.size() > 0)
        Controller.enviar(missatges, pref.getCodiusuari(), pref.getToken());
    }






}
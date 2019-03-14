package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Preferencies pref;
    private ListView lv;
    final int CLAULOGIN = 2;
    FloatingActionButton fab;
    EditText et;
    Button but;
    Button can;
    private ArrayList<Missatge> missatges;
    JSONObject jslogin = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = new Preferencies(this);
        lv = findViewById(R.id.llista);
        et = findViewById(R.id.editText4);
        but = findViewById(R.id.button);
        can = findViewById(R.id.button2);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setVisibility(View.VISIBLE);
                but.setVisibility(View.VISIBLE);
                can.setVisibility(View.VISIBLE);
                et.requestFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et, 0);
            }
        });
        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setVisibility(View.INVISIBLE);
                but.setVisibility(View.INVISIBLE);
                can.setVisibility(View.INVISIBLE);
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
                et.setText(" ");
            }
        });
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setVisibility(View.INVISIBLE);
                but.setVisibility(View.INVISIBLE);
                can.setVisibility(View.INVISIBLE);
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
                Boolean flag = introduirMissatge(et.getText().toString());
                if(!flag){
                    Toast toast = Toast.makeText(getApplicationContext(), "No s'ha pogut enviar", Toast.LENGTH_SHORT);
                    toast.show();
                    Log.d("enviarclick DEVPAU", "no enviat");
                }else{
                    et.setText(" ");
                }
            }
        });
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

                JSONObject tok = new JSONObject(jslogin.get("dades").toString());
                int codi = tok.getInt("codiusuari");
                Log.d("DEVPAU", "Codiusuari de JSON " + codi);
                Log.d("DEVPAU", "Codiusuari de prefs " + pref.getCodiusuari());
                pref.setCodiusuari(codi);
                pref.setToken(tok.get("token").toString());
                mostraMissatges();
            }
        }catch(Exception e){
            Log.d("DEVPAU", e.getMessage());
            Intent inte = new Intent(this, Login.class);
            inte.putExtra("nom", pref.getUser());
            startActivityForResult(inte, CLAULOGIN);
        }

    }

    private Boolean introduirMissatge(String text) {
        Date date = new Date();
        Missatge msg = new Missatge(-1, text, date.toString(), pref.getCodiusuari(), 1);
        DataSourceMsg db = new DataSourceMsg(this);
        try{
            db.open();
            msg = db.createMsg(msg);
            db.close();
        }catch(Exception e){
            Log.d("intoduirMis DEVPAU ", e.toString());
        }
        if(msg.getCodi() > 0){
            enviarMissatges();
            mostraMissatges();
            Log.d("intoduirMis DEVPAU ", "INTRODUIT msg pdt");
        }else {
            Log.d("intoduirMis DEVPAU ", "NO INTRODUIT msg pdt");
        }
        return msg.getCodi() > 0;
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

                JSONObject tok = new JSONObject(jslogin.get("dades").toString());
                int codi = tok.getInt("codiusuari");
                Log.d("DEVPAU", "Codiusuari de JSON " + codi);
                Log.d("DEVPAU", "Codiusuari de prefs " + pref.getCodiusuari());
                pref.setCodiusuari(codi);
                pref.setToken(tok.get("token").toString());
                mostraMissatges();
            }
        }catch(JSONException e){
            Log.d("DEVPAU", e.getMessage());
        }
    }

    public void mostraMissatges(){
        enviarMissatges();
        JSONObject msgs = Controller.carregaMsgs(pref.getCodiusuari(), pref.getToken());
        DataSourceMsg dataSource = new DataSourceMsg(this);
        Log.d("mostraMissatge DEVPAU  ", dataSource.afegirMsgs(msgs)+ "missatges afegits");

        try {
            dataSource.open();
            missatges = dataSource.getAllMsg();
            ArrayAdapter<Missatge> adap = new MissatgeArrayAdapter(this, R.layout.missatge_a_llista, missatges);
            lv.setAdapter(adap);
            dataSource.close();
            Log.d("mostraMissatges DEVPAU", "carregada listview");
        }catch(SQLException e){
            Log.d("mostraMissatges DEVPAU", e.getMessage());
        }
    }

    public void enviarMissatges(){
        DataSourceMsg dataSource = new DataSourceMsg(this);
        ArrayList<Missatge> pendents = dataSource.getAllPdt();
        if(pendents != null && pendents.size() > 0)
        Controller.enviar(pendents, pref.getCodiusuari(), pref.getToken());
    }






}
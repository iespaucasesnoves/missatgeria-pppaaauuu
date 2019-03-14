package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;

public class DataSourceMsg {
    private SQLiteDatabase database;
    private HelperQuepassaeh dbAjuda;
    private String[] allColumnsMsg = {
            HelperQuepassaeh.COLUMN_CODI,
            HelperQuepassaeh.COLUMN_MSG,
            HelperQuepassaeh.COLUMN_DATAHORA,
            HelperQuepassaeh.COLUMN_FKCODIUSUARI,
            HelperQuepassaeh.COLUMN_PENDENT,
    };
    private String[] allColumnsUser = {
            HelperQuepassaeh.COLUMN_CODIUSUARI,
            HelperQuepassaeh.COLUMN_NOM,
            HelperQuepassaeh.COLUMN_EMAIL,
            HelperQuepassaeh.COLUMN_FOTO,
    };

    public DataSourceMsg(Context context) { //CONSTRUCTOR
        dbAjuda = new HelperQuepassaeh(context);
    }

    public void open() throws SQLException {
        database = dbAjuda.getWritableDatabase();
    }

    public void close() {
        dbAjuda.close();
    }

    public Missatge createMsg(Missatge msg){
        ContentValues values = new ContentValues();
        if(msg.getCodi() < 0) {
            values.put(HelperQuepassaeh.COLUMN_MSG, msg.getMsg());
            values.put(HelperQuepassaeh.COLUMN_DATAHORA, msg.getDatahora());
            values.put(HelperQuepassaeh.COLUMN_FKCODIUSUARI, msg.getFkuser());
            values.put(HelperQuepassaeh.COLUMN_PENDENT, msg.getPendent());

            long insertId = database.insert(HelperQuepassaeh.TABLE_MISSATGE, null, values);
            Log.d("createmsg DEVPAU  ", "MSG PDT"+insertId);
            msg.setCodi((int) insertId);
            return msg;
        }else{
            String text = msg.getMsg();
            if(text != null )
            values.put(HelperQuepassaeh.COLUMN_MSG, msg.getMsg());
            values.put(HelperQuepassaeh.COLUMN_CODI, msg.getCodi());
            values.put(HelperQuepassaeh.COLUMN_DATAHORA, msg.getDatahora());
            values.put(HelperQuepassaeh.COLUMN_FKCODIUSUARI, msg.getFkuser());
            values.put(HelperQuepassaeh.COLUMN_PENDENT, msg.getPendent());
            Long insert = database.insert(HelperQuepassaeh.TABLE_MISSATGE, null, values);
            Log.d("createmsg DEVPAU  ", insert.toString());
            return msg;
        }
    }

    public Missatge getMsg(long id){
        Missatge msg;
        Cursor cursor = database.query(HelperQuepassaeh.TABLE_MISSATGE, allColumnsMsg, HelperQuepassaeh.COLUMN_CODI + '=' + id, null, null, null, null );
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            msg = cursorToMsg(cursor);
            msg.setUsuari(getUser(msg.getFkuser()));
        }else{
            msg = new Missatge();
        }
        cursor.close();
        return msg;
    }

    public ArrayList<Missatge> getAllMsg(){
        ArrayList<Missatge> msgs = new ArrayList<Missatge>();
        Cursor cursor = database.query(HelperQuepassaeh.TABLE_MISSATGE, allColumnsMsg, null, null, null, null, HelperQuepassaeh.COLUMN_CODI + " DESC");
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Missatge msg = getMsg(cursor.getInt(0));
            msgs.add(msg);
            cursor.moveToNext();
        }
        cursor.close();
        return msgs;
    }

    public ArrayList<Missatge> getAllPdt(){
        ArrayList<Missatge> msgs = new ArrayList<Missatge>();
        try {
            open();

            Cursor cursor = database.query(HelperQuepassaeh.TABLE_MISSATGE, allColumnsMsg, HelperQuepassaeh.COLUMN_PENDENT + " = 1", null, null, null, HelperQuepassaeh.COLUMN_DATAHORA + " DESC");
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Missatge msg = getMsg(cursor.getInt(0));
                msgs.add(msg);
                cursor.moveToNext();
            }
            cursor.close();
            database.delete(HelperQuepassaeh.TABLE_MISSATGE, HelperQuepassaeh.COLUMN_PENDENT + " = ?", new String[]{"1"});
            close();
        }catch(Exception e){
            Log.d("DEVPAU", e.getMessage());
        }
        return msgs;
    }

    private Missatge cursorToMsg(Cursor cursor){
        Missatge msg = new Missatge();
        cursor.moveToPosition(0);
        msg.setCodi(cursor.getInt(0));
        msg.setMsg(cursor.getString(1));
        msg.setDatahora(cursor.getString(2));
        msg.setFkuser(cursor.getInt(3));
        msg.setPendent(cursor.getInt(4));
        return msg;
    }

    public Boolean createUser(Missatge msg, String nom) {
        ContentValues values = new ContentValues();
        values.put(HelperQuepassaeh.COLUMN_CODIUSUARI, msg.getFkuser());
        values.put(HelperQuepassaeh.COLUMN_NOM, nom);
        values.put(HelperQuepassaeh.COLUMN_EMAIL, "email no implementat");
        values.put(HelperQuepassaeh.COLUMN_FOTO, "foto no implementat");
        long insertId = database.insert(HelperQuepassaeh.TABLE_USUARI, null, values);
        return insertId > 0;
    }

    public String getUser(long id){
        String msg = "";
        try {

            open();
            Cursor cursor = database.query(HelperQuepassaeh.TABLE_USUARI, allColumnsUser, HelperQuepassaeh.COLUMN_CODIUSUARI + '=' + id, null, null, null, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                msg = cursor.getString(1);
            } else {
                msg = "Usuari desconegut";
            }
            cursor.close();
        }catch(Exception e){
            Log.d("DEVPAU", e.getMessage());
        }
        return msg;
    }

    public int afegirMsgs(JSONObject msgs){
        int contador = 0;
        try{
            JSONArray dades = new JSONArray(msgs.get("dades").toString());
            for(int i = 0; i < dades.length(); i++){
                JSONObject msgjson = dades.getJSONObject(i);
                int codi = msgjson.getInt("codi");
                String text = msgjson.get("msg").toString();
                String datahora = msgjson.get("datahora").toString();
                int codiusuari = msgjson.getInt("codiusuari");
                String nom = msgjson.get("nom").toString();
                Missatge msg = new Missatge(codi, text, datahora, codiusuari, 0);

                try {
                    open();
                    createMsg(msg);
                    createUser(msg, nom);
                    close();
                    contador++;
                }catch(SQLException e){
                    Log.d("DEVPAU", e.getMessage());
                }
            }
        }catch(Exception e){
            Log.d("IntrodueixMsgs DEVPAU", e.getMessage());
        }

        return contador;
    }


}

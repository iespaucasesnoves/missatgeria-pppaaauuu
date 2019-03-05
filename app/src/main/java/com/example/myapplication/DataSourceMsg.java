package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

    public Missatge createIng(Missatge msg){
        ContentValues values = new ContentValues();
        values.put(HelperQuepassaeh.COLUMN_MSG,          msg.getMsg());
        values.put(HelperQuepassaeh.COLUMN_DATAHORA,     msg.getDatahora());
        values.put(HelperQuepassaeh.COLUMN_FKCODIUSUARI, msg.getFkuser());
        values.put(HelperQuepassaeh.COLUMN_PENDENT,      msg.getPendent());

        long insertId = database.insert(HelperQuepassaeh.TABLE_MISSATGE,null,values);
        msg.setCodi((int)insertId);
        return msg;
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
        Cursor cursor = database.query(HelperQuepassaeh.TABLE_MISSATGE, allColumnsMsg, null, null, null, null, HelperQuepassaeh.COLUMN_CODI + " DES");
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Missatge msg = getMsg(cursor.getInt(0));
            msgs.add(msg);
            cursor.moveToNext();
        }
        cursor.close();
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

    public String getUser(long id){
        String msg;
        Cursor cursor = database.query(HelperQuepassaeh.TABLE_USUARI, allColumnsUser, HelperQuepassaeh.COLUMN_CODIUSUARI + '=' + id, null, null, null, null );
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            msg = cursor.getString(1);
        }else{
            msg = "Usuari desconegut";
        }
        cursor.close();
        return msg;
    }

    public int afegirMsgs(JSONObject msgs){
        int contador = 0;


        return contador;
    }


}

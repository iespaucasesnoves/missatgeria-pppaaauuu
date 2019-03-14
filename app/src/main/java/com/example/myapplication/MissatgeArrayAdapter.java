package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MissatgeArrayAdapter extends ArrayAdapter<Missatge> {
    private Context context;
    private List<Missatge> missatges;
    Missatge msg;
    TextView user;
    TextView datahora;
    TextView msgv;
    View view;


    public MissatgeArrayAdapter(Context context, int resource, ArrayList<Missatge> objects) {
        super(context, resource, objects);
        this.context = context;
        this.missatges = objects;
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {
        msg = missatges.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.missatge_a_llista, null);

        user = (TextView) view.findViewById(R.id.user);
        datahora = (TextView) view.findViewById(R.id.datahora);
        msgv = (TextView) view.findViewById(R.id.msg);


        msgv.setText(msg.getMsg());
        datahora.setText(msg.getDatahora());
        user.setText(msg.getUsuari());


        return view;
    }


}

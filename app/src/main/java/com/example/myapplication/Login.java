package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity implements View.OnClickListener{
Button but;
EditText etnom, etpass;
String nom, pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nom = extras.getString("nom", "Usuari");
        }
        setContentView(R.layout.activity_login);

        but = findViewById(R.id.button);
        etnom = findViewById(R.id.editText2);
        etnom.setText(nom);
        etpass = findViewById(R.id.editText);
        but.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        nom = etnom.getText().toString();
        pass = etpass.getText().toString();
        finish();
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra("nom", nom);
        data.putExtra("password", pass);
        setResult(RESULT_OK, data);
        super.finish();
    }

}

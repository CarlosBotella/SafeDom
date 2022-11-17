package com.example.safedom;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.security.PrivateKey;
import java.util.Objects;

public class Clave extends AppCompatActivity {
    private EditText etclave;
    private String clave = "";
    private String clavep = "1234";
    private String clavem = "9876";
    Button bs;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.codigo_inicio);
        etclave = (EditText) findViewById(R.id.clave);
        bs = (Button) findViewById(R.id.siguientem);

        bs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clave = etclave.getText().toString();
                if(Validar()) {
                if(!Objects.equals(clave, clavep)&&!Objects.equals(clave, clavem)){
                    etclave.setError("Introduce una clave valida");
                    }
                    else if (Objects.equals(clave, clavep)) {
                    //== no funciona por eso usamos .equals
                    setContentView(R.layout.registrar);
                } else if (Objects.equals(clave, clavem)) {
                    setContentView(R.layout.registro_medico);
                }
            }
            }
        });
    }


    public boolean Validar(){
        boolean s=true;
        clave = etclave.getText().toString();
        if(clave.isEmpty()){
            etclave.setError("Este campo no peude estar vacio");
            s= false;
        }
        if(clave.length() != 4){
            etclave.setError("Este campo tiene que contener 4 digitos");
            s= false;
        }
        return s;
    }


}

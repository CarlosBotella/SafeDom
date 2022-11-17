package com.example.safedom;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Clave extends AppCompatActivity {
    private EditText etclave;
    private String clave="";
    Button bs;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.codigo_inicio);
        etclave = (EditText) findViewById(R.id.clave);
        bs = (Button) findViewById(R.id.siguientem);

    }


    public boolean Validar(){
        boolean s=true;
        clave = etclave.getText().toString();
        if(clave.isEmpty()){
            etclave.setError("Este campo no peude estar vacio");
            s= false;
        }
        return s;
    }


        /*bs.setOnClickListener(new View.OnClickListener(){
        @Override
            public void onClick(View view){
            if(clave=="1234"){
                setContentView(R.layout.registrar);
            }else if(clave=="9876")
            {
                setContentView(R.layout.registro_medico);
            }
            }
        });*/

}

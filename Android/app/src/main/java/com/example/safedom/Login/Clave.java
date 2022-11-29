/* Clase para mover a los usuarios a su correspondiente
 registro dependiendo de la clave dada */
package com.example.safedom.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.safedom.R;

import java.util.Objects;

public class Clave extends AppCompatActivity {
    private EditText etclave;
    private String clave = "";
    private String clavep = "1234";
    private String clavem = "9876";
    Button bs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Log.e("Pruebas", "onCreate" );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.codigo_inicio);
        etclave = (EditText) findViewById(R.id.correol);
        bs = (Button) findViewById(R.id.siguientem);

        bs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                clave = etclave.getText().toString();
                //Log.e("Pruebas", clave );
                //Log.e("Pruebas", clavep );
                if(Validar()) {
                    if(!Objects.equals(clave, clavep)&&!Objects.equals(clave, clavem)){
                        etclave.setError("Introduce una clave valida");
                    }
                    else if (Objects.equals(clave, clavep)) {
                        Log.e("Pruebas", clavep );
                        //== no funciona por eso usamos .equals
                        startActivity(new Intent(Clave.this, RegistroPaciente.class));
                    } else if (Objects.equals(clave, clavem)) {
                        startActivity(new Intent(Clave.this, RegistroMedico.class));
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

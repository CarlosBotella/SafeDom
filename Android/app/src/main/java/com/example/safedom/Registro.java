package com.example.safedom;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class Registro extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        Log.e("Pruebas", "onCreate paciente " );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar);
    }}
    /*private String nombre = "";
    private String apellido = "";
    private String correo = "";
    private String contraseña = "";
    private String ccontraseña = "";
    private EditText etCorreo, etContraseña,etNombre,etApellido,etCcontraseña;
    private TextInputLayout tilCorreo, tilContraseña,tilCcontraseña,tilNombre,tilApellido;
    Button bs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar);
        etCorreo = (EditText) findViewById(R.id.CElec);
        etContraseña = (EditText) findViewById(R.id.pass);
        etCcontraseña = (EditText) findViewById(R.id.Reppass);
        etNombre = (EditText) findViewById(R.id.nombre);
        etApellido = (EditText) findViewById(R.id.apellidos);
        tilCorreo = (TextInputLayout) findViewById(R.id.til_CElec);
        tilContraseña = (TextInputLayout) findViewById(R.id.til_pass);
        tilCcontraseña = (TextInputLayout) findViewById(R.id.til_Reppass);
        tilNombre= (TextInputLayout) findViewById(R.id.til_nombre);
        tilApellido = (TextInputLayout) findViewById(R.id.til_apellidos);
        bs = (Button) findViewById(R.id.siguiente);

        bs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validar();
            }
        });
    }

    /*public void Validar() {
        boolean s=true;
        correo=etCorreo.getText().toString();
        contraseña=etContraseña.getText().toString();
        ccontraseña=etCcontraseña.getText().toString();
        nombre=etNombre.getText().toString();
        apellido=etApellido.getText().toString();
        if(correo.isEmpty()){
            etCorreo.setError("Este campo no peude estar vacio");
            s=false;
        }else if( Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            etCorreo.setError("Introducza un correo valido");
            s=false;
        }else if(contraseña.isEmpty()){
            s=false;
        } else if(contraseña.length()<8){
            etCorreo.setError("La contraseña ha de contener al menos 8 caracteres");
            s=false;
        }else if(ccontraseña.isEmpty()){
            s=false;
        } else if(!Objects.equals(ccontraseña,ccontraseña)){
            etCorreo.setError("No coincide con la contraseña");
            s=false;
        }else if(nombre.isEmpty()){
            etCorreo.setError("Este campo no puede estar vacio");
            s=false;
        }else if(apellido.isEmpty()){
            etCorreo.setError("Este campo no puede estar vacio");
            s=false;
        }

        }
    }
     */


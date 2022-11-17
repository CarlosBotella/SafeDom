package com.example.safedom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import static java.util.Map.entry;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class RegistroPaciente extends AppCompatActivity {
    private String nombre = "";
    private String apellido = "";
    private String correo = "";
    private String contraseña = "";
    private String ccontraseña = "";
    private String rol = "Paciente";//Paciente
    private EditText etCorreo, etContraseña, etNombre, etApellido, etCcontraseña;
    private TextInputLayout tilCorreo, tilContraseña, tilCcontraseña, tilNombre, tilApellido;
    Button bs;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        tilNombre = (TextInputLayout) findViewById(R.id.til_nombre);
        tilApellido = (TextInputLayout) findViewById(R.id.til_apellidos);
        bs = (Button) findViewById(R.id.siguiente);

        bs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Validar()){
                    db.collection("Users").document(correo).set(new User(correo,contraseña,nombre,apellido,rol));
                    mAuth.createUserWithEmailAndPassword(correo, ccontraseña);

                    startActivity(new Intent(RegistroPaciente.this,MainActivity.class));
                }
            }
        });
    }

    public boolean Validar() {
        boolean s = true;
        correo = etCorreo.getText().toString();
        contraseña = etContraseña.getText().toString();
        ccontraseña = etCcontraseña.getText().toString();
        nombre = etNombre.getText().toString();
        apellido = etApellido.getText().toString();
        if (correo.isEmpty()) {
            etCorreo.setError("Este campo no puede estar vacio");
            s = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            etCorreo.setError("Introduzca un correo valido");
            s = false;
        }
        if (contraseña.isEmpty()) {
            etContraseña.setError("Este campo no puede estar vacio");
            s = false;
        }else if (contraseña.length() < 8) {
            etContraseña.setError("La contraseña ha de contener al menos 8 caracteres");
            s = false;
        }

        if (nombre.isEmpty()) {
            etNombre.setError("Este campo no puede estar vacio");
            s = false;
        }
        if (apellido.isEmpty()) {
            etApellido.setError("Este campo no puede estar vacio");
            s = false;
        }
        if (ccontraseña.isEmpty()) {
            etCcontraseña.setError("Este campo no puede estar vacio");
            s = false;
        }else if (!Objects.equals(ccontraseña, contraseña)) {
            etCcontraseña.setError("No coincide con la contraseña");
            s = false;
        }
        return s;
    }
}

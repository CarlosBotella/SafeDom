package com.example.safedom.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.safedom.R;
import com.example.safedom.clases.Medico;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class RegistroMedico extends AppCompatActivity {
    private String nombre = "";
    private String apellido = "";
    private String correo = "";
    private String contraseña = "";
    private String ccontraseña = "";
    private String idmedico = "";
    private String rol = "Medico";//Paciente
    private EditText etCorreo, etContraseña, etNombre, etApellido, etCcontraseña,etIdMedico;
    private TextInputLayout tilCorreo, tilContraseña, tilCcontraseña, tilNombre, tilApellido,tilIdMedico;
    Button bs;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_medico);
        etCorreo = (EditText) findViewById(R.id.Clec);
        etContraseña = (EditText) findViewById(R.id.Pass);
        etCcontraseña = (EditText) findViewById(R.id.Repass);
        etNombre = (EditText) findViewById(R.id.Nombre);
        etApellido = (EditText) findViewById(R.id.Apellido);
        etIdMedico =(EditText) findViewById(R.id.id_medico);
        tilCorreo = (TextInputLayout) findViewById(R.id.til_Clec);
        tilContraseña = (TextInputLayout) findViewById(R.id.til_Pass);
        tilCcontraseña = (TextInputLayout) findViewById(R.id.til_Repass);
        tilNombre = (TextInputLayout) findViewById(R.id.til_nombre);
        tilApellido = (TextInputLayout) findViewById(R.id.til_apellido);
        tilIdMedico = (TextInputLayout) findViewById(R.id.til_id_medico);
        bs = (Button) findViewById(R.id.finalizar);
        bs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Validar()){
                    db.collection("Users").document(correo).set(new Medico(correo,contraseña,nombre,apellido,rol,idmedico));
                    mAuth.createUserWithEmailAndPassword(correo, ccontraseña);

                    startActivity(new Intent(RegistroMedico.this, CustomLoginActivity.class));
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
        idmedico=etIdMedico.getText().toString();
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
        }if (idmedico.isEmpty()) {
            etIdMedico.setError("Este campo no puede estar vacio");
            s = false;
        }
        return s;
    }
}

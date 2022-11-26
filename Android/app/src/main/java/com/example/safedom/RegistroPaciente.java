package com.example.safedom;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegistroPaciente extends AppCompatActivity {
    private String nombre = "";
    private String apellido = "";
    private String correo = "";
    private String contraseña = "";
    private String ccontraseña = "";
    private String rol = "Paciente";//Paciente
    private String telefono = "";
    private String genero = "";
    private String dof = "";
    private String altura = "";
    private String peso = "";


    private EditText etCorreo, etContraseña, etNombre, etApellido, etCcontraseña, etTelefono, etGenero, etDob, etAltura, etPeso;
    private TextInputLayout tilCorreo, tilContraseña, tilCcontraseña, tilNombre, tilApellido, tilTelefono, tilGenero, tilDob, tilAltura, tilPeso;
    Button bs;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final String[] generoLista = {"Hombre", "Mujer", "No Binario"};

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar);
        etCorreo = (EditText) findViewById(R.id.Clec);
        etContraseña = (EditText) findViewById(R.id.Pass);
        etCcontraseña = (EditText) findViewById(R.id.Repass);
        etNombre = (EditText) findViewById(R.id.Nombre);
        etApellido = (EditText) findViewById(R.id.Apellido);
        etTelefono = (EditText) findViewById(R.id.Telefono);
        etDob = (EditText) findViewById(R.id.Dof);
        etAltura = (EditText) findViewById(R.id.Altura);
        etPeso = (EditText) findViewById(R.id.Peso);
        tilCorreo = (TextInputLayout) findViewById(R.id.til_Clec);
        tilContraseña = (TextInputLayout) findViewById(R.id.til_Pass);
        tilCcontraseña = (TextInputLayout) findViewById(R.id.til_Repass);
        tilNombre = (TextInputLayout) findViewById(R.id.til_nombre);
        tilApellido = (TextInputLayout) findViewById(R.id.til_apellido);
        tilTelefono = (TextInputLayout) findViewById(R.id.til_Telefono);
        tilGenero = (TextInputLayout) findViewById(R.id.GeneroPaciente);
        tilDob = (TextInputLayout) findViewById(R.id.til_Dof);
        tilAltura = (TextInputLayout) findViewById(R.id.til_altura);
        tilPeso = (TextInputLayout) findViewById(R.id.til_peso);
        bs = (Button) findViewById(R.id.finalizar);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autocomplete);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegistroPaciente.this,
                android.R.layout.simple_spinner_dropdown_item, generoLista);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(RegistroMedico.this, autoCompleteTextView.getText().toString(), Toast.LENGTH_SHORT).show();
                if (autoCompleteTextView.getText().toString().equals("Hombre")) {
                    genero = generoLista[0];
                } else if (autoCompleteTextView.getText().toString().equals("Mujer")) {
                    genero = generoLista[1];
                } else if (autoCompleteTextView.getText().toString().equals("No Binario")) {
                    genero = generoLista[2];
                }
            }
        });
        bs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Validar()) {

                    db.collection("Users").document(correo).set(new User(correo, contraseña, nombre, apellido, rol, telefono, genero, dof, altura, peso));
                    mAuth.createUserWithEmailAndPassword(correo, ccontraseña);

                    startActivity(new Intent(RegistroPaciente.this, CustomLoginActivity.class));
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
        telefono = etTelefono.getText().toString();
        dof = etDob.getText().toString();
        altura = etAltura.getText().toString();
        peso = etPeso.getText().toString();
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
        } else if (contraseña.length() < 6) {
            etContraseña.setError("La contraseña ha de contener al menos 6 caracteres");
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
        } else if (!Objects.equals(ccontraseña, contraseña)) {
            etCcontraseña.setError("No coincide con la contraseña");
            s = false;
        }
        if (telefono.isEmpty()) {
            etTelefono.setError("Este campo no puede estar vacio");
            s = false;
        } else if (telefono.length() < 9) {
            etTelefono.setError("Ha de contener al menos 9 caracteres");
            s = false;
        }
        if (genero.isEmpty()) {
            etGenero.setError("Este campo no puede estar vacio");
            s = false;
        }/*else if (!Objects.equals(genero, "hombre") || !Objects.equals(genero, "mujer")){
            etGenero.setError("Elija entre hombre o mujer");
            s = false;
        }*/
        if (dof.isEmpty()) {
            etDob.setError("Este campo no puede estar vacio");
            s = false;
        }
        if (altura.isEmpty()) {
            etAltura.setError("Este campo no puede estar vacio");
            s = false;
        }
        if (peso.isEmpty()) {
            etPeso.setError("Este campo no puede estar vacio");
            s = false;
        }
        if (genero.equals("")) {
            tilGenero.setError("Por favor elija su genero");
            s = false;
        }

        return s;
    }
}

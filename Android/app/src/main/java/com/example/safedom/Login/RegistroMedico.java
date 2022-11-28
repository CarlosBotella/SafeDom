/* Clase para registrar al médico */
package com.example.safedom.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.safedom.R;
import com.example.safedom.clases.Medico;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class RegistroMedico extends AppCompatActivity {
    private String genero = "";
    private String nombre = "";
    private String apellido = "";
    private String correo = "";
    private String contraseña = "";
    private String ccontraseña = "";
    private String idmedico = "";
    private String rol = "Medico";//Paciente
    private EditText etCorreo, etContraseña, etNombre, etApellido, etCcontraseña, etIdMedico;
    private TextInputLayout tilCorreo, tilContraseña, tilCcontraseña, tilNombre, tilApellido, tilIdMedico, tilGenero;
    Button bs;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private static final String[] generoLista = {"Hombre", "Mujer", "No Binario"};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.registro_medico);
        etCorreo = (EditText) findViewById(R.id.Clec);
        etContraseña = (EditText) findViewById(R.id.Pass);
        etCcontraseña = (EditText) findViewById(R.id.Repass);
        etNombre = (EditText) findViewById(R.id.Nombre);
        etApellido = (EditText) findViewById(R.id.Apellido);
        etIdMedico = (EditText) findViewById(R.id.id_medico);
        tilCorreo = (TextInputLayout) findViewById(R.id.til_Clec);
        tilContraseña = (TextInputLayout) findViewById(R.id.til_Pass);
        tilCcontraseña = (TextInputLayout) findViewById(R.id.til_Repass);
        tilNombre = (TextInputLayout) findViewById(R.id.til_nombre);
        tilApellido = (TextInputLayout) findViewById(R.id.til_apellido);
        tilIdMedico = (TextInputLayout) findViewById(R.id.til_id_medico);
        tilGenero = (TextInputLayout) findViewById(R.id.spinner_genero);
        bs = (Button) findViewById(R.id.finalizar);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autocomplete);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegistroMedico.this,
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
//        spinner.setOnItemSelectedListener(this);
        bs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Validar()) {
                    db.collection("Users").document(correo).set(new Medico(correo, contraseña, nombre, apellido, rol, idmedico, genero));
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
        idmedico = etIdMedico.getText().toString();
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
        } else if (contraseña.length() < 8) {
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
        } else if (!Objects.equals(ccontraseña, contraseña)) {
            etCcontraseña.setError("No coincide con la contraseña");
            s = false;
        }
        if (idmedico.isEmpty()) {
            etIdMedico.setError("Este campo no puede estar vacio");
            s = false;
        }
        if (genero.equals("")) {
            tilGenero.setError("Por favor elija su genero");
            s = false;
        }
        return s;
    }
}

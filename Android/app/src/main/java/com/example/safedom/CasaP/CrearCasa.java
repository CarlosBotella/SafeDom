package com.example.safedom.CasaP;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.safedom.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class CrearCasa extends AppCompatActivity {
    String direccion="";
    String ciudad="";
    String cp="";
    String paciente="";
    String medico="";
    private EditText direccione,ciudade,cpe,pacientee,medicoe;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_casa);
        direccione=(EditText) findViewById(R.id.Direccion);
        ciudade=(EditText) findViewById(R.id.Ciudad);
        cpe=(EditText) findViewById(R.id.Cp);
        pacientee=(EditText) findViewById(R.id.paientec);
        medicoe=(EditText) findViewById(R.id.doctorc);

    }
}

package com.example.safedom.CasaP;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.safedom.AdminP.VistaAdmin;
import com.example.safedom.R;
import com.example.safedom.Tabs.TabCasas;
import com.example.safedom.clases.Casa;
import com.example.safedom.clases.Medico;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedReader;

public class CrearCasa extends AppCompatActivity {
    String direccion="";
    String ciudad="";
    String cp="";
    String paciente="";
    String medico="";
    private EditText direccione,ciudade,cpe,pacientee,medicoe;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button bs;
    Button bc;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_casa);
        direccione=(EditText) findViewById(R.id.Direccion);
        ciudade=(EditText) findViewById(R.id.Ciudad);
        cpe=(EditText) findViewById(R.id.Cp);
        pacientee=(EditText) findViewById(R.id.paientec);
        medicoe=(EditText) findViewById(R.id.doctorc);
        bs=(Button) findViewById(R.id.button7);
        bc=(Button)findViewById(R.id.button11);
        bs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Validar()) {
                    db.collection("Casa").document(direccion).set(new Casa(paciente,medico,direccion, ciudad, cp));
                    startActivity(new Intent(CrearCasa.this, VistaAdmin.class));
                }
            }
        });
        bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CrearCasa.this, VistaAdmin.class));
            }
        });


    }

    public boolean  Validar(){
        boolean s = true;
        direccion=direccione.getText().toString();
        ciudad=ciudade.getText().toString();
        cp=cpe.getText().toString();
        paciente=pacientee.getText().toString();
        medico=medicoe.getText().toString();
        if(direccion.isEmpty()){
            direccione.setError("Este campo no puede estar vacio");
            s = false;
        }
        if(ciudad.isEmpty()){
            ciudade.setError("Este campo no puede estar vacio");
            s = false;
        }
        if(cp.isEmpty()){
            cpe.setError("Este campo no puede estar vacio");
            s = false;
        }
        if(paciente.isEmpty()){
            pacientee.setError("Este campo no puede estar vacio");
            s = false;
        }
        if(medico.isEmpty()){
            medicoe.setError("Este campo no puede estar vacio");
            s = false;
        }
        return  s;
    }
}

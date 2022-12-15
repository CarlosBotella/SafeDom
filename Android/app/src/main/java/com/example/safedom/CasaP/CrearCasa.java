package com.example.safedom.CasaP;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.safedom.AdminP.VistaAdmin;
import com.example.safedom.MapaCasa;
import com.example.safedom.R;
import com.example.safedom.Tabs.TabCasas;
import com.example.safedom.clases.Casa;
import com.example.safedom.clases.Medico;
import com.example.safedom.clases.User;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CrearCasa extends AppCompatActivity {

    String direccion="";
    String ciudad="";
    String cp="";
    String paciente="";
    String medico="";
    String latitudc="";
    String longitudc="";
    String Lat;
    String Lon;
    String id="";
    private EditText direccione,ciudade,cpe,latitud,longitud;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button bs;
    Button bc;
    Button bm;
    User user;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_casa);
        Lat = getIntent().getStringExtra("Lat");
        Lon=getIntent().getStringExtra("Lon");

        direccione=(EditText) findViewById(R.id.Direccion);
        ciudade=(EditText) findViewById(R.id.Ciudad);
        cpe=(EditText) findViewById(R.id.Cp);
        latitud=(EditText) findViewById(R.id.Altitud);
        longitud=(EditText) findViewById(R.id.Longitud);
        latitud.setText(Lat);
        longitud.setText(Lon);
        //pacientee=(EditText) findViewById(R.id.paientec);
        //medicoe=(EditText) findViewById(R.id.doctorc);
        bs=(Button) findViewById(R.id.button7);
        bc=(Button)findViewById(R.id.button11);
        bm=(Button)findViewById(R.id.coordenadas);
        bs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Validar()) {
                    db.collection("Casa").document(direccion).set(new Casa(paciente,medico,direccion, ciudad, cp,latitudc,longitudc));
                    db.collection("Users")
                            .whereEqualTo("userEmail", paciente)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    Log.d("Pelochas","1");
                                    if (task.isSuccessful()) {
                                        Log.d("Pelochas","2");
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            id=document.getId();
                                            Log.d("Pelochas", document.getId() + " => " + document.getData());
                                            DocumentReference docRef = db.collection("Users").document(id);
                                            docRef.update("casa", "Si");
                                        }
                                    } else {

                                    }
                                }
                            });



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
        bm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CrearCasa.this, MapaCasa.class));
            }
        });


        CollectionReference usersRef = db.collection("Users");
        Query query = usersRef.whereEqualTo("rol", "Paciente");
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        List<String> users = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        String user = document.getString("userEmail");
                        users.add(user);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
        Query queryy= usersRef.whereEqualTo("rol", "Medico");
        Spinner spinnerr = (Spinner) findViewById(R.id.spinner2);
        List<String> medicos = new ArrayList<>();
        ArrayAdapter<String> adapterr = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, medicos);
        adapterr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerr.setAdapter(adapterr);
        queryy.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String medico = document.getString("userEmail");
                        medicos.add(medico);
                    }
                    adapterr.notifyDataSetChanged();
                }
            }
        });

    }

    public boolean  Validar(){
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Spinner spinnerr = (Spinner) findViewById(R.id.spinner2);
        boolean s = true;
        direccion=direccione.getText().toString();
        ciudad=ciudade.getText().toString();
        cp=cpe.getText().toString();
        paciente=spinner.getSelectedItem().toString();
        medico=spinnerr.getSelectedItem().toString();
        latitudc=latitud.getText().toString();
        longitudc=longitud.getText().toString();
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
        if(latitudc.isEmpty()){
            latitud.setError("Este campo no puede estar vacio");
            s = false;
        }
        if(longitudc.isEmpty()){
            longitud.setError("Este campo no puede estar vacio");
            s = false;
        }


        return  s;
    }
}

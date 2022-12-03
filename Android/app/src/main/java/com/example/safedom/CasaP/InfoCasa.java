package com.example.safedom.CasaP;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.safedom.R;
import com.example.safedom.clases.Casa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InfoCasa extends AppCompatActivity {
    private ImageView imgp,imgm;
    private TextView direccioninfo, cpinfo,ciudadinfo,medicoinfo,pacienteinfo,puertainfo;
    private Casa casa;
    private DatabaseReference dbSensores;
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_infocasa);
        setTitle(getClass().getSimpleName());
        initViews();
        initValues();
    }
    private void initViews() {
    imgp=findViewById(R.id.imgpinfo);
    imgm=findViewById(R.id.imgdinfo);
    direccioninfo=findViewById(R.id.direccioninfo);
    cpinfo=findViewById(R.id.cpinfo);
    ciudadinfo=findViewById(R.id.ciudadinfo);
    medicoinfo=findViewById(R.id.doctorinfo);
    pacienteinfo=findViewById(R.id.pacienteinfo);
    puertainfo=findViewById(R.id.puertainfo);

    }
    private void initValues() {
        casa=(Casa) getIntent().getExtras().getSerializable("casaInfo");
        direccioninfo.setText(casa.getDireccion());
        cpinfo.setText(casa.getCP());
        ciudadinfo.setText(casa.getCiudad());
        medicoinfo.setText(casa.getMedico());
        pacienteinfo.setText(casa.getPaciente());
        dbSensores = FirebaseDatabase.getInstance().getReference();
        dbSensores.child("Sensores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("Pelochas","Hola1");
                if(snapshot.exists()){
                    puertainfo.setText(snapshot.child("MC38").getValue().toString());
                    Log.e("Pelochas",snapshot.child("MC38").getValue().toString());
                    Log.e("Pelochas","Hola2");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}

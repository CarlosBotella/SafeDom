package com.example.safedom.CasaP;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.safedom.AdminP.VistaAdmin;
import com.example.safedom.PacienteP.InfoPaciente;
import com.example.safedom.R;
import com.example.safedom.clases.Casa;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Locale;

public class InfoCasa extends AppCompatActivity {
    private ImageView imgp, imgm;
    private TextView direccioninfo, cpinfo, ciudadinfo, medicoinfo, pacienteinfo, puertainfo, lat ,lon;
    private Casa casa;
    String dir = "";
    Button be;
    String puerta ="";
    String Lat="";
    String Lon="";
    String id="";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Casas");

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_infocasa);
        setTitle(getClass().getSimpleName());
        initViews();
        initValues();
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("¿ Desea borrar esta casa?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                aceptar();
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                cancelar();
            }
        });

        be.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo1.show();
            }
        });
    }

    private void initViews() {
        imgp = findViewById(R.id.imgpinfo);
        imgm = findViewById(R.id.imgdinfo);
        direccioninfo = findViewById(R.id.direccioninfo);
        cpinfo = findViewById(R.id.cpinfo);
        ciudadinfo = findViewById(R.id.ciudadinfo);
        medicoinfo = findViewById(R.id.doctorinfo);
        pacienteinfo = findViewById(R.id.pacienteinfo);
        be = (Button) findViewById(R.id.Eliinarc);


    }

    private void initValues() {
        casa = (Casa) getIntent().getExtras().getSerializable("casaInfo");
        direccioninfo.setText(casa.getDireccion());
        dir = casa.getDireccion().toString();
        cpinfo.setText(casa.getCP());
        ciudadinfo.setText(casa.getCiudad());
        medicoinfo.setText(casa.getMedico());
        pacienteinfo.setText(casa.getPaciente());
        Lat=casa.getLatitud().toString();
        Lon=casa.getLongitud().toString();

        myRef.child(casa.getDireccion()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = String.valueOf(dataSnapshot.getValue());
                Log.d("Pelochas", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Pelochas", "Failed to read value.", error.toException());
            }
        });
    }

    public void aceptar() {
        db.collection("Casa")
                .whereEqualTo("direccion", dir)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                db.collection("Casa").document(dir).delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                db.collection("Users")
                                                        .whereEqualTo("userEmail", casa.getPaciente())
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
                                                                        docRef.update("casa", "No");
                                                                    }
                                                                } else {

                                                                }
                                                            }
                                                        });
                                                startActivity(new Intent(InfoCasa.this, VistaAdmin.class));
                                            }
                                        });
                            }
                        }
                    }
                });
    }


    public void cancelar() {
        finish();
    }


}



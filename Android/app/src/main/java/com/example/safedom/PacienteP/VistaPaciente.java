package com.example.safedom.PacienteP;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.safedom.InfoActivity;
import com.example.safedom.R;
import com.example.safedom.clases.Casa;
import com.example.safedom.clases.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.Objects;

public class VistaPaciente extends AppCompatActivity {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    String id = "";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageRef;
    private TextView puertap, sp, tsp, temp, hum,PP,SP,TSP,TEMP,HUM,m,m2;

    Casa casa;
    Float min;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Casas");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_paciente);
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        id = usuario.getUid();
        puertap = findViewById(R.id.PP4);
        sp = findViewById(R.id.SP4);
        tsp = findViewById(R.id.TSP4);
        temp = findViewById(R.id.t3);
        hum = findViewById(R.id.h3);
        PP=findViewById(R.id.textView12);
        SP=findViewById(R.id.textView13);
        TSP=findViewById(R.id.textView14);
        TEMP=findViewById(R.id.textView15);
        HUM=findViewById(R.id.textView16);
        m=findViewById(R.id.Mensaje);
        m2=findViewById(R.id.Mensaje2);
        DocumentReference docRef = db.collection("Users").document(id);
        storageRef = FirebaseStorage.getInstance().getReference();

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User usuario = documentSnapshot.toObject(User.class);
                ImageView imgperfil = findViewById(R.id.imagenp);
                if (!usuario.getFoto().equals("")) {
                    Picasso.get().load(usuario.getFoto()).into(imgperfil);
                }
                TextView nombre = findViewById(R.id.nombrep);
                nombre.setText(usuario.getNombre() + " " + usuario.getApellido());
                TextView edad = findViewById(R.id.dobp);
                edad.setText(usuario.getDob());
                TextView tlf = findViewById(R.id.telefonop);
                tlf.setText(usuario.getTelefono());
                TextView email = findViewById(R.id.mailp);
                email.setText(usuario.getUserEmail());
                if (Objects.equals(usuario.getCasa(), "Si")) {
                    CollectionReference reference = db.collection("Casa");
                    Query query = reference.whereEqualTo("paciente", usuario.getUserEmail());
                    query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                casa = documentSnapshot.toObject(Casa.class);
                            }
                            myRef.child(casa.getDireccion()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String value = String.valueOf(dataSnapshot.getValue());
                                    String[] parts = value.split(",");
                                    String part1 = parts[0].split("=")[1];
                                    String part2 = parts[1].split("=")[1];
                                    String part3 = parts[2].split("=")[1];
                                    String part4 = parts[3].split("=")[1];
                                    String part5 = parts[4].split("=")[1];
                                    Log.d("Pelochas", part5);
                                    String parte5 = "";
                                    String[] lista = part5.split("");
                                    for (int i = 0; i < part5.length() - 1; i++) {
                                        parte5 += lista[i];

                                    }
                                    if (!Objects.equals(parte5, "0")) {
                                        min = Float.valueOf(parte5) / 60;
                                    } else {
                                        min = Float.valueOf(0);
                                    }
                                    puertap.setText(part1);
                                    sp.setText(part4);
                                    tsp.setText(df.format(min) + " minutos");
                                    temp.setText(part3 + " º");
                                    hum.setText(part2 + " %");
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    // Failed to read value
                                    Log.w("Pelochas", "Failed to read value.", error.toException());
                                }
                            });
                        }
                    });
                }else{
                    puertap.setVisibility(View.GONE);
                    sp.setVisibility(View.GONE);
                    tsp.setVisibility(View.GONE);
                    temp.setVisibility(View.GONE);
                    hum.setVisibility(View.GONE);
                    PP.setVisibility(View.GONE);
                    SP.setVisibility(View.GONE);
                    TSP.setVisibility(View.GONE);
                    HUM.setVisibility(View.GONE);
                    TEMP.setVisibility(View.GONE);
                    m.setVisibility(View.VISIBLE);
                    m2.setVisibility(View.VISIBLE);

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        if (id == R.id.menu_usuario) {
            Intent intent = new Intent(this, UsuarioActivity.class);
            startActivity(intent);

        }
        if (id == R.id.info) {
            Intent intent = new Intent(this, InfoActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}



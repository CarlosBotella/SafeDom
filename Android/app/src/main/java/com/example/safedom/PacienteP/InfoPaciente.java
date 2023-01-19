package com.example.safedom.PacienteP;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.safedom.AdminP.VistaAdmin;
import com.example.safedom.Citas.CalendarActivity;
import com.example.safedom.Mapas.Mapa;
import com.example.safedom.R;
import com.example.safedom.clases.Casa;
import com.example.safedom.clases.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.Objects;


public class InfoPaciente extends AppCompatActivity {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private ImageView imginfo;
    private TextView nombreinfo, correoinfo, telefonoinfo, generoinfo, dobinfo, alturainfo, pesoinfo, t, d, g, a, p, puertap, sp, tsp, temp, hum,Pp,Sp,Tsp,Temp,Hum;
    private User user;
    Button botonCalendario,be,bci,bc,bl;
    String id = "",rol = "",tel = "",correo = "",Lat = "",Lon = "",Pac = "",Dir = "", PP="",SP="",TSP="",TEMP="",HUM="";
    Casa casa;
    Float min;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final int SOLICITUD_PERMISO_CALL_PHONE = 0;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Casas");

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_infopaciente);
        setTitle(getClass().getSimpleName());
        initViews();
        initValues();

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("¿ Desea borrar este usuario?");
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
        bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(InfoPaciente.this, Mapa.class);
                intent.putExtra("Lat", Lat);
                intent.putExtra("Lon", Lon);
                intent.putExtra("Pac", Pac);
                intent.putExtra("Dir", Dir);
                Log.d("Pelochas", Lat + ", " + Lon);
                startActivity(intent);
            }
        });
        botonCalendario = (Button) findViewById(R.id.buttonCalendario);

        botonCalendario.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   startActivity(new Intent(InfoPaciente.this, CalendarActivity.class));
                                               }
                                           }
        );
    }

    private void initViews() {
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        id = usuario.getUid();
        DocumentReference docRef = db.collection("Users").document(id);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
                rol = user.getRol().toString();
                if (Objects.equals(rol, "Admin")) {
                    be.setVisibility(View.VISIBLE);
                    bci.setVisibility(View.INVISIBLE);
                }
            }

        });

        imginfo = findViewById(R.id.infoimg);
        nombreinfo = findViewById(R.id.infonombre);
        correoinfo = findViewById(R.id.infoacorreo);
        telefonoinfo = findViewById(R.id.infotelefono);
        generoinfo = findViewById(R.id.infogenero);
        dobinfo = findViewById(R.id.infodb);
        alturainfo = findViewById(R.id.infaltura);
        pesoinfo = findViewById(R.id.infopeso);
        be = (Button) findViewById(R.id.eliminar);
        bci = (Button) findViewById(R.id.buttonCalendario);
        bc = (Button) findViewById(R.id.casa);
        bl = (Button) findViewById(R.id.llamar);
        t = findViewById(R.id.tel);
        g = findViewById(R.id.gene);
        d = findViewById(R.id.dob);
        a = findViewById(R.id.alt);
        p = findViewById(R.id.pes);
        puertap=findViewById(R.id.PP);
        sp=findViewById(R.id.SP);
        tsp=findViewById(R.id.TSP);
        temp=findViewById(R.id.T);
        hum=findViewById(R.id.H);
        Pp=findViewById(R.id.textView4);
        Sp=findViewById(R.id.textView5);
        Tsp=findViewById(R.id.textView7);
        Temp=findViewById(R.id.textView9);
        Hum=findViewById(R.id.textView10);

    }

    private void initValues() {
        user = (User) getIntent().getExtras().getSerializable("pacienteInfo");

        if (Objects.equals(user.getRol(), "Paciente")) {
            nombreinfo.setText(user.getNombre() + " " + user.getApellido());
            correoinfo.setText(user.getUserEmail());
            correo = user.getUserEmail().toString();
            telefonoinfo.setText(user.getTelefono());
            tel = user.getTelefono().toString();
            generoinfo.setText(user.getGenero());
            dobinfo.setText(user.getDob());
            alturainfo.setText(user.getAltura());
            pesoinfo.setText(user.getPeso());
            if (!user.getFoto().equals("")) {
                Picasso.get().load(user.getFoto()).into(imginfo);
            }
            if(Objects.equals(user.getCasa(),"Si")){
                CollectionReference reference = db.collection("Casa");
                Query query = reference.whereEqualTo("paciente", correo);
                query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            casa = documentSnapshot.toObject(Casa.class);
                        }
                        Lat = casa.getLatitud();
                        Lon = casa.getLongitud();
                        Pac = casa.getPaciente();
                        Dir = casa.getDireccion();
                        Log.d("Pelochas", Lat + ",, " + Lon);
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
                                for (int i = 0; i < part5.length()-1; i++) {
                                    parte5 += lista[i];

                                }
                                if(!Objects.equals(parte5,"0")) {
                                    min = Float.valueOf(parte5) / 60;
                                }
                                else {
                                    min= Float.valueOf(0);
                                }
                                puertap.setText(part1);
                                sp.setText(part4);
                                tsp.setText(df.format(min)+" minutos");
                                temp.setText(part3+" º");
                                hum.setText(part2+" %");

                            }
                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value
                                Log.w("Pelochas", "Failed to read value.", error.toException());
                            }
                        });
                    }
                });
            } else {
                bc.setVisibility(View.GONE);
                puertap.setVisibility(View.GONE);
                sp.setVisibility(View.GONE);
                tsp.setVisibility(View.GONE);
                temp.setVisibility(View.GONE);
                hum.setVisibility(View.GONE);
                Pp.setVisibility(View.GONE);
                Sp.setVisibility(View.GONE);
                Tsp.setVisibility(View.GONE);
                Temp.setVisibility(View.GONE);
                Hum.setVisibility(View.GONE);
            }
        } else if (Objects.equals(user.getRol(), "Medico")) {
            nombreinfo.setText(user.getNombre() + " " + user.getApellido());
            correoinfo.setText(user.getUserEmail());
            if (!user.getFoto().equals("")) {
                Picasso.get().load(user.getFoto()).into(imginfo);
            }
            correo = user.getUserEmail().toString();
            telefonoinfo.setVisibility(View.INVISIBLE);
            generoinfo.setVisibility(View.INVISIBLE);
            dobinfo.setVisibility(View.INVISIBLE);
            alturainfo.setVisibility(View.INVISIBLE);
            pesoinfo.setVisibility(View.INVISIBLE);
            t.setVisibility(View.GONE);
            d.setVisibility(View.GONE);
            g.setVisibility(View.GONE);
            a.setVisibility(View.GONE);
            p.setVisibility(View.GONE);
            bc.setVisibility(View.GONE);
            puertap.setVisibility(View.GONE);
            sp.setVisibility(View.GONE);
            tsp.setVisibility(View.GONE);
            temp.setVisibility(View.GONE);
            hum.setVisibility(View.GONE);
            Pp.setVisibility(View.GONE);
            Sp.setVisibility(View.GONE);
            Tsp.setVisibility(View.GONE);
            Temp.setVisibility(View.GONE);
            Hum.setVisibility(View.GONE);

        }else{
            puertap.setVisibility(View.GONE);
            sp.setVisibility(View.GONE);
            tsp.setVisibility(View.GONE);
            temp.setVisibility(View.GONE);
            hum.setVisibility(View.GONE);

        }

    }

    public void aceptar() {
        db.collection("Users")
                .whereEqualTo("userEmail", correo)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.e("Pelochas", correo);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                db.collection("Users").document(document.getId()).delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                startActivity(new Intent(InfoPaciente.this, VistaAdmin.class));
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

    public void llamar(View view) {

        solicitarPermiso(Manifest.permission.CALL_PHONE, "Sin el permiso" +
                        " administrar llamadas no puedo borrar llamadas del registro.",
                SOLICITUD_PERMISO_CALL_PHONE, this);
        llamarTelefono();

    }

    public void llamarTelefono() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_CALL,
                    Uri.parse("tel:" + tel));
            startActivity(intent);
        } else {
            solicitarPermiso(Manifest.permission.CALL_PHONE, "Sin el permiso" +
                            " administrar llamadas no puedo borrar llamadas del registro.",
                    SOLICITUD_PERMISO_CALL_PHONE, this);
        }
    }

    public static void solicitarPermiso(final String permiso, String justificacion, final int requestCode, final Activity actividad) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(actividad,
                permiso)) {
            new AlertDialog.Builder(actividad)
                    .setTitle("Solicitud de permiso")
                    .setMessage(justificacion)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ActivityCompat.requestPermissions(actividad,
                                    new String[]{permiso}, requestCode);
                        }
                    }).show();
        } else {
            ActivityCompat.requestPermissions(actividad,
                    new String[]{permiso}, requestCode);

        }
    }
}
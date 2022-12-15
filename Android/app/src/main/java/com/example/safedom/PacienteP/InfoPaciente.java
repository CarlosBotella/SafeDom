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
import androidx.fragment.app.Fragment;

import com.example.safedom.Citas.CalendarActivity;
import com.example.safedom.Citas.WeekViewActivity;
import com.example.safedom.R;
import com.example.safedom.clases.Casa;
import com.example.safedom.clases.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Objects;


public class InfoPaciente extends AppCompatActivity {
    private ImageView imginfo;
    private TextView nombreinfo, correoinfo,telefonoinfo,generoinfo,dobinfo,alturainfo,pesoinfo,t,d,g,a,p;
    private User user;
    Button botonCalendario;
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_infopaciente);
        setTitle(getClass().getSimpleName());

        initViews();
        initValues();
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

        imginfo=findViewById(R.id.infoimg);
        nombreinfo=findViewById(R.id.infonombre);
        correoinfo=findViewById(R.id.infoacorreo);
                telefonoinfo=findViewById(R.id.infotelefono);
        generoinfo=findViewById(R.id.infogenero);
                dobinfo=findViewById(R.id.infodb);
        alturainfo=findViewById(R.id.infaltura);
                pesoinfo=findViewById(R.id.infopeso);
                be=(Button) findViewById(R.id.eliminar);
        bci=(Button) findViewById(R.id.cita);
        bc=(Button) findViewById(R.id.casa);
        bl=(Button) findViewById(R.id.llamar);
        t=findViewById(R.id.tel);
        g=findViewById(R.id.gene);
        d=findViewById(R.id.dob);
        a=findViewById(R.id.alt);
        p=findViewById(R.id.pes);

    }
    private void initValues() {
        user=(User) getIntent().getExtras().getSerializable("pacienteInfo");

        if(Objects.equals(user.getRol(),"Paciente")){
            nombreinfo.setText(user.getNombre() + " " +user.getApellido());
            correoinfo.setText(user.getUserEmail());
            correo= user.getUserEmail().toString();
            telefonoinfo.setText(user.getTelefono());
            tel=user.getTelefono().toString();
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
                    }
                });
            }else{
                bc.setVisibility(View.GONE);
            }
        }else if(Objects.equals(user.getRol(),"Medico")){
            nombreinfo.setText(user.getNombre() + " " +user.getApellido());
            correoinfo.setText(user.getUserEmail());
            correo= user.getUserEmail().toString();
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
        }

    }

public void aceptar(){
    db.collection("Users")
            .whereEqualTo("userEmail", correo)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.e("Pelochas",correo);
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
        public void llamar(View view){

                    solicitarPermiso(Manifest.permission.CALL_PHONE, "Sin el permiso"+
                                    " administrar llamadas no puedo borrar llamadas del registro.",
                            SOLICITUD_PERMISO_CALL_PHONE, this);
                    llamarTelefono();

        }

    public void llamarTelefono () {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_CALL,
                    Uri.parse("tel:"+tel));
            startActivity(intent);
        } else {
            solicitarPermiso(Manifest.permission.CALL_PHONE, "Sin el permiso" +
                            " administrar llamadas no puedo borrar llamadas del registro.",
                    SOLICITUD_PERMISO_CALL_PHONE, this);
        }
    }
    public static void solicitarPermiso(final String permiso, String justificacion, final int requestCode, final Activity actividad) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(actividad,
                permiso)){
            new AlertDialog.Builder(actividad)
                    .setTitle("Solicitud de permiso")
                    .setMessage(justificacion)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ActivityCompat.requestPermissions(actividad,
                                    new String[]{permiso}, requestCode);
                        }}).show();
        } else {
            ActivityCompat.requestPermissions(actividad,
                    new String[]{permiso}, requestCode);

        }
    }
}

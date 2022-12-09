package com.example.safedom.PacienteP;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.safedom.AdminP.VistaAdmin;
import com.example.safedom.CasaP.CrearCasa;
import com.example.safedom.R;
import com.example.safedom.clases.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class InfoPaciente extends AppCompatActivity {
    private ImageView imginfo;
    private TextView nombreinfo, correoinfo,telefonoinfo,generoinfo,dobinfo,alturainfo,pesoinfo;
    private User user;
    Button be;
    String id="";
    String rol="";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_infopaciente);
        setTitle(getClass().getSimpleName());
        initViews();
        initValues();


    }

    private void initViews() {
       /* FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        id = usuario.getUid();
        DocumentReference docRef = db.collection("Users").document(id);
      docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                              @Override
                                              public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                  user = documentSnapshot.toObject(User.class);
                                                  rol = user.getRol().toString();
                                                   if (Objects.equals(rol, "Admin")) {
                                                      be.setVisibility(View.VISIBLE);
                                                  }

                                          });*/

        imginfo=findViewById(R.id.infoimg);
        nombreinfo=findViewById(R.id.infonombre);
        correoinfo=findViewById(R.id.infoacorreo);
                telefonoinfo=findViewById(R.id.infotelefono);
        generoinfo=findViewById(R.id.infogenero);
                dobinfo=findViewById(R.id.infodb);
        alturainfo=findViewById(R.id.infaltura);
                pesoinfo=findViewById(R.id.infopeso);
    }
    private void initValues() {
        user=(User) getIntent().getExtras().getSerializable("pacienteInfo");
        nombreinfo.setText(user.getNombre() + " " +user.getApellido());
        correoinfo.setText(user.getUserEmail());
        telefonoinfo.setText(user.getTelefono());
        generoinfo.setText(user.getGenero());
        dobinfo.setText(user.getDob());
        alturainfo.setText(user.getAltura());
        pesoinfo.setText(user.getPeso());
    }
}

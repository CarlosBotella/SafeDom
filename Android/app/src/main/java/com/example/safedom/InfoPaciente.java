package com.example.safedom;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.safedom.clases.User;


public class InfoPaciente extends AppCompatActivity {
    private ImageView imginfo;
    private TextView nombreinfo, correoinfo,telefonoinfo,generoinfo,dobinfo,alturainfo,pesoinfo;
    private User user;
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_infopaciente);
        setTitle(getClass().getSimpleName());

        initViews();
        initValues();
    }

    private void initViews() {
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
        Log.e("markilongas",user.getUserEmail());
        nombreinfo.setText(user.getNombre() + " " +user.getApellido());
        correoinfo.setText(user.getUserEmail());
        telefonoinfo.setText(user.getTelefono());
        generoinfo.setText(user.getGenero());
        dobinfo.setText(user.getDob());
        alturainfo.setText(user.getAltura());
        pesoinfo.setText(user.getPeso());
    }
}

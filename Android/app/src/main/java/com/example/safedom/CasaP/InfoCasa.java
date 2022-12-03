package com.example.safedom;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.safedom.clases.Casa;
import com.example.safedom.clases.User;

public class InfoCasa extends AppCompatActivity {
    private ImageView imgp,imgm;
    private TextView direccioninfo, cpinfo,ciudadinfo,medicoinfo,pacienteinfo;
    private Casa casa;
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
    }
    private void initValues() {
        casa=(Casa) getIntent().getExtras().getSerializable("casaInfo");
        direccioninfo.setText(casa.getDireccion());
        cpinfo.setText(casa.getCP());
        ciudadinfo.setText(casa.getCiudad());

    }
}

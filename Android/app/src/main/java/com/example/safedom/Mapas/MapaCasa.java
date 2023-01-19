package com.example.safedom.Mapas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.safedom.CasaP.CrearCasa;
import com.example.safedom.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapaCasa extends FragmentActivity
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {
    private GoogleMap mapa;
    double lat;
    double lon;
    String Lat="";
    String Lon="";
    Button ba;
    Button bv;
    AlertDialog.Builder dialogo1;

    private final LatLng España = new LatLng(41.3115453, -3.2513285);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa_casa);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);
        ba=findViewById(R.id.AC);
        bv=findViewById(R.id.VOLVER);
        dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("¿Guardar estas coordenadas?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                aceptar();
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                cancelarr();
            }
        });
        bv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapaCasa.this, CrearCasa.class));
            }
        });
        ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo1.show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        mapa.setOnMapClickListener(this);
        mapa.setOnMapLongClickListener(this);
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(España, 6));
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            mapa.setMyLocationEnabled(true);
            mapa.getUiSettings().setCompassEnabled(true);
        }
    }

    @Override
    public void onMapClick(LatLng punto) {
        lat = punto.latitude;
        lon = punto.longitude;
        Toast.makeText(getApplicationContext(),"Coordenadas: "+lat+lon,Toast.LENGTH_SHORT).show();
        Lat=String.valueOf(lat);
        Lon=String.valueOf(lon);
        dialogo1.setMessage(Lat+", "+Lon);

    }

    @Override
    public void onMapLongClick(LatLng punto) {
        lat = punto.latitude;
        lon = punto.longitude;
        Toast.makeText(getApplicationContext(),"Coordenadas: "+lat+lon,Toast.LENGTH_SHORT).show();
        Lat=String.valueOf(lat);
        Lon=String.valueOf(lon);
        dialogo1.setMessage(Lat+", "+Lon);
        Log.d("Pelochas","ONMAPCLICK: "+ Lat+",, "+Lon);

    }

    public void aceptar(){
        Intent intent= new Intent(MapaCasa.this, CrearCasa.class);
        intent.putExtra("Lat",Lat);
        intent.putExtra("Lon",Lon);
        Log.d("Pelochas",Lat+",., "+Lon);
        startActivity(intent);
    }
    public void cancelarr(){
        finish();
    }

}

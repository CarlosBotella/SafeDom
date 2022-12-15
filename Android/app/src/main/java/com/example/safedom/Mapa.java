package com.example.safedom;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa extends FragmentActivity
            implements OnMapReadyCallback,GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {
    String Lat;
    String Lon;
    String Pac;
    String Dir;
    private GoogleMap mapa;
    private  LatLng Casa;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);
        Lat = getIntent().getStringExtra("Lat");
        Lon=getIntent().getStringExtra("Lon");
        Pac=getIntent().getStringExtra("Pac");
        Dir=getIntent().getStringExtra("Dir");
        Casa = new LatLng(Double.parseDouble(Lat), Double.parseDouble(Lon));
    }

    @Override public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        mapa.setOnMapClickListener(this);
        mapa.setOnMapLongClickListener(this);
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(Casa, 16));
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            mapa.setMyLocationEnabled(true);
            mapa.getUiSettings().setCompassEnabled(true);
        }
        mapa.addMarker(new MarkerOptions()
                .position(Casa)
                .title(Dir)
                .snippet(Pac)
                .icon(BitmapDescriptorFactory
                        .fromResource(android.R.drawable.ic_menu_compass))
                .anchor(0.5f, 0.5f));
    }

        @Override
        public void onMapClick(LatLng punto) {

        }
        @Override
        public void onMapLongClick(LatLng punto) {

        }
    }


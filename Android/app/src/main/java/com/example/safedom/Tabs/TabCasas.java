package com.example.safedom.Tabs;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safedom.CasaP.CasaAdapter;
import com.example.safedom.CasaP.CrearCasa;
import com.example.safedom.Login.CustomLoginActivity;
import com.example.safedom.Login.RegistroMedico;
import com.example.safedom.PacienteP.UsuarioActivity;
import com.example.safedom.R;
import com.example.safedom.clases.Casa;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TabCasas extends Fragment implements SearchView.OnQueryTextListener{
    SearchView txtBuscar;
    ArrayList<Casa> arrayCasa= new ArrayList<>();
    CasaAdapter casaAdapter;
    Button b;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista= inflater.inflate(R.layout.tab_casas, container, false);
        final RecyclerView recycler =(RecyclerView)vista.findViewById(R.id.recyclerView);
        txtBuscar=(SearchView)vista.findViewById(R.id.txtBuscar);

        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("Casa");
        reference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                    arrayCasa.add(documentSnapshot.toObject(Casa.class));
                }
            }
        }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                casaAdapter = new CasaAdapter(arrayCasa, getContext());
                recycler.setAdapter(casaAdapter);
            }
        });
        txtBuscar.setOnQueryTextListener(this);
        b=(Button)vista.findViewById(R.id.button10);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CrearCasa.class));
            }
        });


        return vista;
    }



    /*public void Anyadir(View v){
        Intent intent = new Intent(getActivity(), CrearCasa.class);
        startActivity(intent);
    }*/
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        casaAdapter.filtrado(s);
        return false;
    }
}

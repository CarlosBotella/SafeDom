package com.example.safedom.Tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safedom.CasaAdapter;
import com.example.safedom.PacienteAdapter;
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

public class TabCasas extends Fragment {
    ArrayList<Casa> arrayCasa= new ArrayList<>();
    CasaAdapter casaAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista= inflater.inflate(R.layout.tab_casas, container, false);
        final RecyclerView recycler =(RecyclerView)vista.findViewById(R.id.recyclerView);
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
        return vista;
    }
}

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

import com.example.safedom.PacienteAdapter;
import com.example.safedom.R;
import com.example.safedom.clases.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TabUsers extends Fragment {
    ArrayList<User> arrayUser= new ArrayList<>();
    PacienteAdapter pacienteAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista= inflater.inflate(R.layout.tab_users, container, false);
        final RecyclerView recycler =(RecyclerView)vista.findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("Users");
        reference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                    arrayUser.add(documentSnapshot.toObject(User.class));
                }
            }
        }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                pacienteAdapter = new PacienteAdapter(arrayUser, getContext());
                recycler.setAdapter(pacienteAdapter);
            }
        });
        return vista;
    }
}

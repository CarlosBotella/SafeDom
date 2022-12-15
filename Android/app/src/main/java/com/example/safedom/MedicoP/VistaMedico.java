package com.example.safedom.MedicoP;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safedom.CasaP.CasaAdapter;
import com.example.safedom.InfoActivity;
import com.example.safedom.MedicoP.MedicoActivity;
import com.example.safedom.PacienteP.PacienteAdapter;
import com.example.safedom.R;
import com.example.safedom.clases.Casa;
import com.example.safedom.clases.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VistaMedico extends AppCompatActivity implements SearchView.OnQueryTextListener{
    ArrayList<User> arrayUser= new ArrayList<>();
    ArrayList<Casa> arrayCasa= new ArrayList<>();
    List<String> ids = new ArrayList<String>();
    PacienteAdapter pacienteAdapter;
    CasaAdapter casaAdapter;
    SearchView txtBuscar;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private  Casa casa;

    protected View.OnClickListener onClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_medico);
        txtBuscar=(SearchView) findViewById(R.id.txtBuscar);
        final RecyclerView recycler =(RecyclerView)findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        FirebaseFirestore db= FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("Casa");
        CollectionReference referencee = db.collection("Users");
        Query query = reference.whereEqualTo("medico",auth.getCurrentUser().getEmail());
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                    arrayCasa.add(documentSnapshot.toObject(Casa.class));
                }
            }
        }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (Casa c:arrayCasa) {
                    ids.add(c.getPaciente().toString());
                    //Log.e("Pelochas", String.valueOf(ids));
                }
                Log.e("Pelochas", String.valueOf(ids));
                for (String i:ids) {
                    Query queryy = referencee.whereEqualTo("userEmail",i);
                    queryy.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                                arrayUser.add(documentSnapshot.toObject(User.class));
                            }
                        }
                    }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            pacienteAdapter = new PacienteAdapter(arrayUser, getApplicationContext());
                            recycler.setAdapter(pacienteAdapter);
                        }
                    });
                }
            }
        });

        txtBuscar.setOnQueryTextListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.menu_usuario) {
            Intent intent = new Intent(this, MedicoActivity.class);
            startActivity(intent);
        }
        if (id == R.id.info) {
            Intent intent = new Intent(this, InfoActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        pacienteAdapter.filtrado(s);
        return false;
    }
}

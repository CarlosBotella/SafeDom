package com.example.safedom;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safedom.clases.User;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class VistaMedico extends AppCompatActivity {
    RecyclerView pReclyer;
    PacienteAdapter pAdaper;
    FirebaseFirestore db ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_medico);
        db= FirebaseFirestore.getInstance();
        pReclyer = findViewById(R.id.recyclerView);
        pReclyer.setLayoutManager(new LinearLayoutManager(this));
        Query query = db.collection("Users");
        FirestoreRecyclerOptions<User> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<User>().setQuery(query,User.class).build();
        pAdaper = new PacienteAdapter(firestoreRecyclerOptions);
        pAdaper.notifyDataSetChanged();
        pReclyer.setAdapter(pAdaper);
    }
    @Override
    protected void onStart(){
        super.onStart();
        pAdaper.startListening();
    }
    @Override
    protected void onStop(){
        super.onStop();
        pAdaper.stopListening();
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
}

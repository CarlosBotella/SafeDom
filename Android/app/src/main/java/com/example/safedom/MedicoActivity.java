package com.example.safedom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.safedom.clases.User;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MedicoActivity extends AppCompatActivity {
    String mail = "";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        mail = usuario.getEmail();
        DocumentReference docRef = db.collection("Users").document(mail);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
          @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User medico= documentSnapshot.toObject(User.class);
                TextView nombre = findViewById(R.id.nombrem);
                nombre.setText(medico.getNombre());
                TextView correo = findViewById(R.id.correom);
                correo.setText(medico.getUserEmail());
                TextView apellido = findViewById(R.id.apellidom);
                apellido.setText(medico.getApellido());

            }

        });
    }
    public void cerrarSesion(View view) {
        AuthUI.getInstance().signOut(getApplicationContext())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent i = new Intent(
                                getApplicationContext (),CustomLoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                | Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    }
                });
    }
    public void back(View view){
        startActivity(new Intent(MedicoActivity.this,VistaMedico.class));
    }
}

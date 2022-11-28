/* Clase para la vista edit_medico, sirve para poder que el medico pueda
 editar sus datos */

package com.example.safedom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.safedom.clases.Medico;
import com.example.safedom.clases.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditMedico extends AppCompatActivity {
    String mail = "";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String correom="";String nombrem="";String apellidom="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_medico);
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        mail = usuario.getEmail();
        DocumentReference docRef = db.collection("Users").document(mail);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User medico= documentSnapshot.toObject(User.class);
                TextInputEditText correo = findViewById(R.id.correoe);
                correo.setText(medico.getUserEmail());
                TextInputEditText nombre = findViewById(R.id.nombree);
                nombre.setText(medico.getNombre());
                TextInputEditText apellido = findViewById(R.id.apellidoe);
                apellido.setText(medico.getApellido());
            }
        });
    }

    public void cancelar(View view){
        startActivity(new Intent(EditMedico.this,MedicoActivity.class));
    }
    public void aceptar(View view){
        TextInputEditText correo = findViewById(R.id.correoe);
        correom=correo.getText().toString();
        TextInputEditText nombre = findViewById(R.id.nombree);
        nombrem=nombre.getText().toString();
        TextInputEditText apellido = findViewById(R.id.apellidoe);
        apellidom=apellido.getText().toString();
        DocumentReference docRef = db.collection("Users").document(mail);
        docRef.update("userEmail",correom , "nombre",nombrem,"apellido",apellidom);

        startActivity(new Intent(EditMedico.this,MedicoActivity.class));
    }
}
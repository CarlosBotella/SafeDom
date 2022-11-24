package com.example.safedom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.safedom.clases.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditUsuario extends AppCompatActivity {
    String mail = "";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String newcorreo = "";
    String newnombre = "";
    String newapellido = "";
    String newpeso = "";
    String newaltura = "";
    String newtelefono = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_usuario);
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        mail = usuario.getEmail();
        DocumentReference docRef = db.collection("Users").document(mail);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User usuario = documentSnapshot.toObject(User.class);
                TextInputEditText correo = findViewById(R.id.ncorreo);
                correo.setText(usuario.getUserEmail());
                TextInputEditText nombre = findViewById(R.id.nnombre);
                nombre.setText(usuario.getNombre());
                TextInputEditText apellido = findViewById(R.id.napellido);
                apellido.setText(usuario.getApellido());
                TextInputEditText peso = findViewById(R.id.npeso);
                peso.setText(usuario.getPeso());
                TextInputEditText altura = findViewById(R.id.naltura);
                altura.setText(usuario.getAltura());
                TextInputEditText telefono = findViewById(R.id.ntelefono);
                telefono.setText(usuario.getTelefono());
            }
        });
    }

    public void cancelarUsuario(View view) {
        startActivity(new Intent(EditUsuario.this, UsuarioActivity.class));
    }

    public void aceptarUsuario(View view) {
        TextInputEditText correo = findViewById(R.id.ncorreo);
        newcorreo = correo.getText().toString();
        TextInputEditText nombre = findViewById(R.id.nnombre);
        newnombre = nombre.getText().toString();
        TextInputEditText apellido = findViewById(R.id.napellido);
        newapellido = apellido.getText().toString();
        TextInputEditText peso = findViewById(R.id.npeso);
        newpeso = peso.getText().toString();
        TextInputEditText altura = findViewById(R.id.naltura);
        newaltura = altura.getText().toString();
        TextInputEditText telefono = findViewById(R.id.ntelefono);
        newtelefono = telefono.getText().toString();
        DocumentReference docRef = db.collection("Users").document(mail);
        docRef.update("userEmail", newcorreo, "nombre", newnombre, "apellido", newapellido, "peso", newpeso, "altura", newaltura, "telefono", newtelefono);

        startActivity(new Intent(EditUsuario.this, UsuarioActivity.class));
    }
}

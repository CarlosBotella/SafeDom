package com.example.safedom;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
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

public class UsuarioActivity extends AppCompatActivity {
    String mail="";
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        mail=usuario.getEmail();
        DocumentReference docRef = db.collection("Users").document(mail);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                TextView nombre = findViewById(R.id.Nombre);
                nombre.setText(user.getNombre());
                TextView correo = findViewById(R.id.correom);
                correo.setText(user.getUserEmail());
                TextView apellido = findViewById(R.id.apellidom);
                apellido.setText(user.getApellido());
                TextView peso= findViewById(R.id.peso);
                peso.setText(user.getPeso());
                TextView altura = findViewById(R.id.altura);
                altura.setText(user.getAltura());
                TextView telefono = findViewById(R.id.telefonon);
                telefono.setText(user.getTelefono());
            }

        });






        RequestQueue colaPeticiones = Volley.newRequestQueue(this);
        ImageLoader lectorImagenes = new ImageLoader(colaPeticiones,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap> cache =
                            new LruCache<String, Bitmap>(10);
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }
                });
// Foto de usuario
        Uri urlImagen = usuario.getPhotoUrl();
        if (urlImagen != null) {
            NetworkImageView foto = (NetworkImageView) findViewById(R.id.imagen);
            foto.setImageUrl(urlImagen.toString(), lectorImagenes);
        }


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

    public void editarUsuario(View view) {
        DocumentReference docRef = db.collection("Users").document(mail);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                TextView nombre = findViewById(R.id.Nombre);
                user.setNombre(nombre.toString());
                TextView correo = findViewById(R.id.correom);
                user.setUserEmail(correo.toString());
                TextView apellido = findViewById(R.id.apellidom);
                user.setApellido(apellido.toString());
                TextView peso = findViewById(R.id.peso);
                user.setPeso(peso.toString());
                TextView altura = findViewById(R.id.altura);
                user.setAltura(altura.toString());
                TextView telefono = findViewById(R.id.telefonon);
                user.setTelefono(telefono.toString());
            }
        });
    }

    public void back(View view){
        startActivity(new Intent(UsuarioActivity.this,VistaMedico.class));
    }
}


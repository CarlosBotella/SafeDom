/* Clase para la vista edit_medico, sirve para poder que el paciente pueda
 editar sus datos */

package com.example.safedom.PacienteP;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.safedom.R;
import com.example.safedom.clases.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class EditUsuario extends AppCompatActivity {
    String id = "";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageRef;
    String newcorreo = "";
    String newnombre = "";
    String newapellido = "";
    String newpeso = "";
    String newaltura = "";
    String newtelefono = "";
    String newfoto = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_usuario);
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        id = usuario.getUid();
        DocumentReference docRef = db.collection("Users").document(id);
        storageRef = FirebaseStorage.getInstance().getReference();

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
                ImageView imgperfil = findViewById(R.id.imagen);
                if (!usuario.getFoto().equals("")) {
                    Picasso.get().load(usuario.getFoto()).into(imgperfil);
                }
            }
        });
    }

    public void ClicImg(View view) {
        final int cod = 3;
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, cod);
    }

    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView ImgPerfil = findViewById(R.id.imagen);
        if (requestCode == 3) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    ImgPerfil.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri));
                    UploadTask uploadTask;
                    StorageReference ref = storageRef.child("Perfil").child(id + ".jpg");
                    uploadTask = ref.putFile(uri);
                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            return ref.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                newfoto = task.getResult().toString();
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.e("Error", "" + e);
                }
            }
        }
    }

    public void cancelarUsuario(View view) {
        startActivity(new Intent(EditUsuario.this, UsuarioActivity.class));
    }

    public void aceptarUsuario(View view) {
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
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
        usuario.updateEmail(newcorreo);
        DocumentReference docRef = db.collection("Users").document(id);
        docRef.update("userEmail", newcorreo, "nombre", newnombre, "apellido", newapellido, "peso", newpeso, "altura", newaltura, "telefono", newtelefono, "foto", newfoto);
        startActivity(new Intent(EditUsuario.this, UsuarioActivity.class));
    }
}

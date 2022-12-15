
/* Clase para la vista edit_medico, sirve para poder que el medico pueda
 editar sus datos */
package com.example.safedom.MedicoP;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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

public class EditMedico extends AppCompatActivity {
    String id = "";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageRef;
    String correom = "";
    String nombrem = "";
    String apellidom = "";
    String fotom = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_medico);
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        id = usuario.getUid();
        DocumentReference docRef = db.collection("Users").document(id);
        storageRef = FirebaseStorage.getInstance().getReference();

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User medico = documentSnapshot.toObject(User.class);
                TextInputEditText correo = findViewById(R.id.correoe);
                correo.setText(medico.getUserEmail());
                TextInputEditText nombre = findViewById(R.id.nombree);
                nombre.setText(medico.getNombre());
                TextInputEditText apellido = findViewById(R.id.apellidoe);
                apellido.setText(medico.getApellido());
                ImageView imgperfil = findViewById(R.id.imageperfilmedico);
                if (!medico.getFoto().equals("")) {
                    Picasso.get().load(medico.getFoto()).into(imgperfil);
                }
                else Log.d("Foto","Foto no existe");
            }
        });
    }

    public void ClicBtnImg(View view) {
        final int cod = 3;
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, cod);
    }

    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView ImgPerfil = findViewById(R.id.imageperfilmedico);
        if (requestCode == 3) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    ImgPerfil.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri));
                    UploadTask uploadTask;
                    StorageReference ref = storageRef.child("Usuarios").child(id).child("foto.jpg");
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
                                fotom = task.getResult().toString();
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.e("Error", "" + e);
                }
            }
        }
    }

    public void cancelar(View view) {
        startActivity(new Intent(EditMedico.this, MedicoActivity.class));
    }

    public void aceptar(View view) {
        DocumentReference docRef = db.collection("Users").document(id);

        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        TextInputEditText correo = findViewById(R.id.correoe);
        correom = correo.getText().toString();
        TextInputEditText nombre = findViewById(R.id.nombree);
        nombrem = nombre.getText().toString();
        TextInputEditText apellido = findViewById(R.id.apellidoe);
        apellidom = apellido.getText().toString();
        usuario.updateEmail(correom);

        docRef.update("userEmail", correom, "nombre", nombrem, "apellido", apellidom, "foto", fotom);
        startActivity(new Intent(EditMedico.this, MedicoActivity.class));
    }
}
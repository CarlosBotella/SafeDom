<<<<<<< HEAD:Android/app/src/main/java/com/example/safedom/EditMedico.java
/* Clase para la vista edit_medico, sirve para poder que el medico pueda
 editar sus datos */

package com.example.safedom;
=======
package com.example.safedom.MedicoP;
>>>>>>> Cositas:Android/app/src/main/java/com/example/safedom/MedicoP/EditMedico.java

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.safedom.R;
import com.example.safedom.clases.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditMedico extends AppCompatActivity {
    String id = "";
    String pass = "";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String correom="";String nombrem="";String apellidom="";
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_medico);
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        id = usuario.getUid();
        DocumentReference docRef = db.collection("Users").document(id);
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
        startActivity(new Intent(EditMedico.this, MedicoActivity.class));
    }
    public void aceptar(View view){
       DocumentReference docRef = db.collection("Users").document(id);

        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        TextInputEditText correo = findViewById(R.id.correoe);
        correom=correo.getText().toString();
        TextInputEditText nombre = findViewById(R.id.nombree);
        nombrem=nombre.getText().toString();
        TextInputEditText apellido = findViewById(R.id.apellidoe);
        apellidom=apellido.getText().toString();
        usuario.updateEmail(correom);

        docRef.update("userEmail",correom, "nombre",nombrem,"apellido",apellidom);
        startActivity(new Intent(EditMedico.this,MedicoActivity.class));


    }
}
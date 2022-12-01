package com.example.safedom.Login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.safedom.R;
import com.example.safedom.VistaAdmin;
import com.example.safedom.VistaMedico;
import com.example.safedom.VistaPaciente;
import com.example.safedom.clases.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class CustomLoginActivity extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String correo = "";
    private String contraseña = "";
    private ViewGroup contenedor;
    private EditText etCorreo, etContraseña;
    private TextInputLayout tilCorreo, tilContraseña;
    private ProgressDialog dialogo;
    private ProgressDialog dialogoo;

    private String rol = "";
    private String roli = "";
    private String rolm = "Medico";
    private String rolp = "Paciente";
    private String rola = "Admin";
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    User user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        etCorreo = (EditText) findViewById(R.id.correol);
        etContraseña = (EditText) findViewById(R.id.contraseña);
        tilCorreo = (TextInputLayout) findViewById(R.id.til_clave);
        tilContraseña = (TextInputLayout) findViewById(R.id.til_contraseña);
        dialogo = new ProgressDialog(this);
        dialogo.setTitle("Verificando usuario");
        dialogo.setMessage("Por favor espere...");
        dialogoo = new ProgressDialog(this);
        dialogoo.setTitle("Iniciando Sesion");
        dialogoo.setMessage("Por favor espere...");
        if (auth.getCurrentUser() != null) {
            dialogoo.show();
            DocumentReference docRef = db.collection("Users").document(auth.getCurrentUser().getEmail());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    user = documentSnapshot.toObject(User.class);
                    roli = user.getRol().toString();
                    if (Objects.equals(roli, rolp)) {
                        verificaSiUsuarioValidadop();
                    }
                    if (Objects.equals(roli, rolm)) {
                        verificaSiUsuarioValidado();
                    }
                    if (Objects.equals(roli, rola)) {
                        verificaSiUsuarioValidadoa();
                    }
                }
            });
        }


    }
    public void aceptar() {
        Toast t=Toast.makeText(this,"Bienvenido a probar el programa.", Toast.LENGTH_SHORT);
        t.show();
    }
    private void verificaSiUsuarioValidado() {
            if (auth.getCurrentUser() != null) {
                Intent i = new Intent(this, VistaMedico.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
        }
    }
    private void verificaSiUsuarioValidadop() {

        if (auth.getCurrentUser() != null) {
            Intent i = new Intent(this, VistaPaciente.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();

        }
    }
    private void verificaSiUsuarioValidadoa() {

        if (auth.getCurrentUser() != null) {
            Intent i = new Intent(this, VistaAdmin.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
    }



    public void inicioSesiónCorreo(View v) {
            if (verificaCampos()) {
                dialogo.show();
                auth.signInWithEmailAndPassword(correo, contraseña)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.e("Pruebas: ", "onComplete inicioSesiónCorreo");
                                    DocumentReference docRef = db.collection("Users").document(correo);
                                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            user = documentSnapshot.toObject(User.class);
                                            rol=user.getRol().toString();
                                            if(Objects.equals(rol,rolp)) {

                                                verificaSiUsuarioValidadop();
                                            }
                                            if(Objects.equals(rol,rolm)) {

                                                verificaSiUsuarioValidado();
                                            }
                                            if(Objects.equals(rol,rola)) {

                                            verificaSiUsuarioValidadoa();
                                        }
                                    }
                                });
                                } else {
                                    Log.e("Pruebas: ", "FALLO inicioSesiónCorreo");
                                    dialogo.dismiss();
                                    Log.e("Pruebas: ", String.valueOf(task.getException()));
                                    mensaje(task.getException().getLocalizedMessage());
                                }
                            }
                        });
            }
        }



    public void registroCodigo(View v) {
        startActivity(new Intent(this, Clave.class));
    }

    private void mensaje(String mensaje) {
        Snackbar.make(contenedor, mensaje, Snackbar.LENGTH_LONG).show();
    }

    private boolean verificaCampos() {
        boolean s=true;
        correo = etCorreo.getText().toString();
        contraseña = etContraseña.getText().toString();
        tilCorreo.setError("");
        tilContraseña.setError("");
        if (correo.isEmpty()) {
            tilCorreo.setError("Introduce un correo");
            s=false;
        }
        if (contraseña.isEmpty()) {
            tilContraseña.setError("Introduce una contraseña");
            s=false;
        }
        return s;
    }


}


package com.example.safedom.PacienteP;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.safedom.R;
import com.example.safedom.clases.User;
import com.google.firebase.firestore.FirebaseFirestore;


public class InfoPaciente extends AppCompatActivity {
    private ImageView imginfo;
    private TextView nombreinfo, correoinfo, telefonoinfo, generoinfo, dobinfo, alturainfo, pesoinfo;
    private User user;
    Button be;
    String id = "";
    String rol = "";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_infopaciente);
        setTitle(getClass().getSimpleName());
        initViews();
        initValues();


    }

    public void llamarTelefono(View view) {
        Log.e("PRUEBAS: ", "llamarTelefono: OUT");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Log.e("PRUEBAS", "llamarTelefono: PERMISSION ACC");
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telefonoinfo.getText().toString()));
            startActivity(intent);
        } else {
            Log.e("PRUEBAS", "llamarTelefono: PERMISSION DEN " );
            solicitarPermiso(Manifest.permission.CALL_PHONE, "Sin el permiso de llamada no se puede realizar la llamada", 1, this);
        }

    }

    private void solicitarPermiso(String permiso, String justificacion, final int requestCode, final Activity actividad) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(actividad, permiso)) {
            new AlertDialog.Builder(actividad)
                    .setTitle("Solicitud de permiso")
                    .setMessage(justificacion)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ActivityCompat.requestPermissions(actividad, new String[]{permiso}, requestCode);}}).show();
        } else {
            // No se necesita mostrar la explicación al usuario, solicitar el permiso
            ActivityCompat.requestPermissions(actividad, new String[]{permiso}, requestCode);
        }
    }

    private void initViews() {
       /* FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        id = usuario.getUid();
        DocumentReference docRef = db.collection("Users").document(id);
      docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                              @Override
                                              public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                  user = documentSnapshot.toObject(User.class);
                                                  rol = user.getRol().toString();
                                                   if (Objects.equals(rol, "Admin")) {
                                                      be.setVisibility(View.VISIBLE);
                                                  }

                                          });*/

        imginfo = findViewById(R.id.infoimg);
        nombreinfo = findViewById(R.id.infonombre);
        correoinfo = findViewById(R.id.infoacorreo);
        telefonoinfo = findViewById(R.id.infotelefono);
        generoinfo = findViewById(R.id.infogenero);
        dobinfo = findViewById(R.id.infodb);
        alturainfo = findViewById(R.id.infaltura);
        pesoinfo = findViewById(R.id.infopeso);
    }

    private void initValues() {
        user = (User) getIntent().getExtras().getSerializable("pacienteInfo");
        nombreinfo.setText(user.getNombre() + " " + user.getApellido());
        correoinfo.setText(user.getUserEmail());
        telefonoinfo.setText(user.getTelefono());
        generoinfo.setText(user.getGenero());
        dobinfo.setText(user.getDob());
        alturainfo.setText(user.getAltura());
        pesoinfo.setText(user.getPeso());
    }
}

package com.example.safedom.PacienteP;


import static org.example.safedom.bidireccional.Mqtt.TAG;
import static org.example.safedom.bidireccional.Mqtt.broker;
import static org.example.safedom.bidireccional.Mqtt.clientId;
import static org.example.safedom.bidireccional.Mqtt.qos;
import static org.example.safedom.bidireccional.Mqtt.topicRoot;

import android.Manifest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.safedom.InfoActivity;
import com.example.safedom.MainActivity;
import com.example.safedom.R;
import com.example.safedom.clases.Casa;
import com.example.safedom.clases.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.text.DecimalFormat;
import java.util.Objects;



public class VistaPaciente extends AppCompatActivity {
    //region variables
    private static final DecimalFormat df = new DecimalFormat("0.00");
    String id = "", tel = "", mailm = "",Tluz, Comeluz, Cociluz, Dpluz, D1luz, D2luz, Entluz;
    boolean p = true, tluz = true;
    Button bl, bp, blu, btl, bcomel, bcocl, bdpl, bd1l, bd2l, bel;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageRef;
    private TextView puertap, sp, tsp, temp, hum, PP, SP, TSP, TEMP, HUM, m, m2;
    private Button bm, b;
    static MqttClient client;
    public static String Topic1 = "puerta/";
    public static String Topic2 = "luces/";
    private String puerta = "puerta";
    Casa casa;
    Float min;
    User userr;
    private static final int SOLICITUD_PERMISO_CALL_PHONE = 0;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Casas");
    FirebaseUser usuario;
    //endregion
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_paciente);
        conectarMqtt();
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View vieww = inflater.inflate(R.layout.paciente_popup, null);
        btl=(Button)vieww.findViewById(R.id.tluz);
        bcomel=(Button)vieww.findViewById(R.id.comluz);
        bcocl=(Button)vieww.findViewById(R.id.cocluz);
        bdpl=(Button)vieww.findViewById(R.id.dpluz);
        bd1l=(Button)vieww.findViewById(R.id.d1luz);
        bd2l=(Button)vieww.findViewById(R.id.d2luz);
        bel=(Button)vieww.findViewById(R.id.eluz);
        usuario = FirebaseAuth.getInstance().getCurrentUser();
        id = usuario.getUid();
        puertap = findViewById(R.id.PP4);
        sp = findViewById(R.id.SP4);
        tsp = findViewById(R.id.TSP4);
        temp = findViewById(R.id.t3);
        hum = findViewById(R.id.h3);
        PP = findViewById(R.id.textView12);
        SP = findViewById(R.id.textView13);
        TSP = findViewById(R.id.textView14);
        TEMP = findViewById(R.id.textView15);
        HUM = findViewById(R.id.textView16);
        m = findViewById(R.id.Mensaje);
        m2 = findViewById(R.id.Mensaje2);
        bp = (Button) findViewById(R.id.abrirPuertap);
        bl = (Button) findViewById(R.id.llamarM);
        blu = (Button) findViewById(R.id.Luces);


        blu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog popup = new Dialog(VistaPaciente.this);
                popup.setContentView(R.layout.paciente_popup);
                popup.show();
            }
        });




        DocumentReference docRef = db.collection("Users").document(id);
        storageRef = FirebaseStorage.getInstance().getReference();
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User usuario = documentSnapshot.toObject(User.class);
                ImageView imgperfil = findViewById(R.id.imagenp);
                if (!usuario.getFoto().equals("")) {
                    Picasso.get().load(usuario.getFoto()).into(imgperfil);
                }
                TextView nombre = findViewById(R.id.nombrep);
                nombre.setText(usuario.getNombre() + " " + usuario.getApellido());
                TextView edad = findViewById(R.id.dobp);
                edad.setText(usuario.getDob());
                TextView tlf = findViewById(R.id.telefonop);
                tlf.setText(usuario.getTelefono());
                TextView email = findViewById(R.id.mailp);
                email.setText(usuario.getUserEmail());
                if (Objects.equals(usuario.getCasa(), "Si")) {
                    CollectionReference reference = db.collection("Casa");
                    Query query = reference.whereEqualTo("paciente", usuario.getUserEmail());
                    query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                casa = documentSnapshot.toObject(Casa.class);
                            }
                            myRef.child(casa.getDireccion()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String value = String.valueOf(dataSnapshot.getValue());
                                    String[] parts = value.split(",");
                                    String part1 = parts[0].split("=")[1];
                                    String part2 = parts[3].split("=")[1];
                                    String part3 = parts[4].split("=")[1];
                                    String part4 = parts[10].split("=")[1];
                                    String part5 = parts[11].split("=")[1];

                                    String part6 = parts[6].split("=")[1];
                                    String part7 = parts[8].split("=")[1];
                                    String part8 = parts[1].split("=")[1];
                                    String part9 = parts[5].split("=")[1];
                                    String part10 = parts[2].split("=")[1];
                                    String part11 = parts[7].split("=")[1];
                                    String part12 = parts[9].split("=")[1];
                                    Log.d("Pelochas", part5);
                                    String parte5 = "";
                                    String[] lista = part5.split("");
                                    for (int i = 0; i < part5.length() - 1; i++) {
                                        parte5 += lista[i];

                                    }
                                    if (!Objects.equals(parte5, "0")) {
                                        min = Float.valueOf(parte5) / 60;
                                    } else {
                                        min = Float.valueOf(0);
                                    }
                                    puertap.setText(part1);
                                    sp.setText(part4);
                                    tsp.setText(df.format(min) + " minutos");
                                    temp.setText(part3 + " º");
                                    hum.setText(part2 + " %");


                                    puerta = part1;
                                    if (puerta.equals("Abierta")) {
                                        bp.setText("CERRAR PUERTA");
                                    } else if (puerta.equals("Cerrada")) {
                                        bp.setText("ABIRIR PUERTA");
                                    }

                                    Tluz=part6;
                                    Comeluz=part7;
                                    Cociluz=part8;
                                    Dpluz=part9;
                                    D1luz=part10;
                                    D2luz=part11;
                                    Entluz=part12;

                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    // Failed to read value
                                    Log.w("Pelochas", "Failed to read value.", error.toException());
                                }
                            });
                        }
                    });
                } else {
                    puertap.setVisibility(View.GONE);
                    sp.setVisibility(View.GONE);
                    tsp.setVisibility(View.GONE);
                    temp.setVisibility(View.GONE);
                    hum.setVisibility(View.GONE);
                    PP.setVisibility(View.GONE);
                    SP.setVisibility(View.GONE);
                    TSP.setVisibility(View.GONE);
                    HUM.setVisibility(View.GONE);
                    TEMP.setVisibility(View.GONE);
                    m.setVisibility(View.VISIBLE);
                    m2.setVisibility(View.VISIBLE);

                }
            }
        });
        bp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (puerta.equals("Cerrada")) {
                    publicarMqtt(Topic1, "abrirpuerta");
                    Toast.makeText(getApplicationContext(), "Abriendo Puerta",
                            Toast.LENGTH_LONG).show();
                    //bp.setText("CERRAR PUERTA");
                } else if (puerta.equals("Abierta")) {
                    publicarMqtt(Topic1, "cerrarpuerta");
                    Toast.makeText(getApplicationContext(), "Cerrando Puerta",
                            Toast.LENGTH_LONG).show();
                    //bp.setText("ABIRIR PUERTA");
                }
            }
        });

    }

    public void LlamarM(View view) {
        solicitarPermiso(Manifest.permission.CALL_PHONE, "Sin el permiso" +
                        " administrar llamadas no puedo borrar llamadas del registro.",
                SOLICITUD_PERMISO_CALL_PHONE, this);
        llamarTelefono();
    }

    public void llamarTelefono() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            DocumentReference docRef = db.collection("Users").document(id);
            storageRef = FirebaseStorage.getInstance().getReference();
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User usuario = documentSnapshot.toObject(User.class);
                    if (Objects.equals(usuario.getCasa(), "Si")) {
                        CollectionReference reference = db.collection("Casa");
                        Query query = reference.whereEqualTo("paciente", usuario.getUserEmail());
                        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    casa = documentSnapshot.toObject(Casa.class);
                                }
                                mailm = casa.getMedico();
                                Log.d("Pelochas", "Mail: " + mailm);
                                CollectionReference referencee = db.collection("Users");
                                Query queryy = referencee.whereEqualTo("userEmail", mailm);
                                queryy.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                            userr = documentSnapshot.toObject(User.class);
                                            tel = userr.getTelefono();
                                            Intent intent = new Intent(Intent.ACTION_CALL,
                                                    Uri.parse("tel:" + tel));
                                            startActivity(intent);
                                        }
                                    }
                                });
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Aun no se le ha asignado un medico",
                                Toast.LENGTH_LONG).show();
                    }

                }
            });
        } else {
            solicitarPermiso(Manifest.permission.CALL_PHONE, "Sin el permiso" +
                            " administrar llamadas no puedo borrar llamadas del registro.",
                    SOLICITUD_PERMISO_CALL_PHONE, this);
        }
    }


    public static void solicitarPermiso(final String permiso, String justificacion, final int requestCode, final Activity actividad) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(actividad,
                permiso)) {
            new AlertDialog.Builder(actividad)
                    .setTitle("Solicitud de permiso")
                    .setMessage(justificacion)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ActivityCompat.requestPermissions(actividad,
                                    new String[]{permiso}, requestCode);
                        }
                    }).show();
        } else {
            ActivityCompat.requestPermissions(actividad,
                    new String[]{permiso}, requestCode);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_usuario) {
            Intent intent = new Intent(this, UsuarioActivity.class);
            startActivity(intent);

        }
        if (id == R.id.info) {
            Intent intent = new Intent(this, InfoActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    public void Luzt(View view){
        if (Tluz.equals("Apagada")) {

            publicarMqtt(Topic2, "encencdertodasluz");
            Toast.makeText(getApplicationContext(), "Encendiendo Todas Las Luces",
                    Toast.LENGTH_LONG).show();

        } else if(Tluz.equals("Encendida")){
            publicarMqtt(Topic2, "apagartodasluz");
            Toast.makeText(getApplicationContext(), "Apagando Todas Las Luces",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void Luzcom(View view) {
            if (Comeluz.equals("Apagada")) {
                publicarMqtt(Topic2, "encencdercomedorluz");
                Toast.makeText(getApplicationContext(), "Encendiendo Las Luces Del Comedor",
                        Toast.LENGTH_LONG).show();

            } else if(Comeluz.equals("Encendida")){
                publicarMqtt(Topic2, "apagarcomedorluz");
                Toast.makeText(getApplicationContext(), "Apagando Las Luces Del Comedor",
                        Toast.LENGTH_LONG).show();
            }
    }

    public void LuzCoc(View view){
        if (Cociluz.equals("Apagada")) {
            publicarMqtt(Topic2, "encencdercocinaluz");
            Toast.makeText(getApplicationContext(), "Encendiendo Las Luces De La Cocina",
                    Toast.LENGTH_LONG).show();

        } else if(Cociluz.equals("Encendida")){
            publicarMqtt(Topic2, "apagarcocinaluz");
            Toast.makeText(getApplicationContext(), "Apagando Las Luces De La Cocina",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void LuzDP(View view){
        if (Dpluz.equals("Apagada")) {
            publicarMqtt(Topic2, "encencderdormitoriopluz");
            Toast.makeText(getApplicationContext(), "Encendiendo Las Luces Del Dormitorio Principal",
                    Toast.LENGTH_LONG).show();

        } else if(Dpluz.equals("Encendida")){
            publicarMqtt(Topic2, "apagardormitoriopluz");
            Toast.makeText(getApplicationContext(), "Apagando Las Luces Del Dormitorio Principal",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void LuzD1(View view){
        if (D1luz.equals("Apagada")) {
            publicarMqtt(Topic2, "encencderdormitorio1luz");
            Toast.makeText(getApplicationContext(), "Encendiendo Las Luces Del Dormitorio1",
                    Toast.LENGTH_LONG).show();

        } else if(D1luz.equals("Encendida")){
            publicarMqtt(Topic2, "apagardormitorio1luz");
            Toast.makeText(getApplicationContext(), "Apagando Las Luces Del Dormitorio1",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void LuzD2(View view){
        if (D2luz.equals("Apagada")) {
            publicarMqtt(Topic2, "encencderdormitorio2luz");
            Toast.makeText(getApplicationContext(), "Encendiendo Las Luces Del Dormitorio2",
                    Toast.LENGTH_LONG).show();

        } else if(D2luz.equals("Encendida")){
            publicarMqtt(Topic2, "apagardormitorio2luz");
            Toast.makeText(getApplicationContext(), "Apagando Las Luces Del Dormitorio2",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void Luze(View view){
        if (Entluz.equals("Apagada")) {
            publicarMqtt(Topic2, "encencderentradaluz");
            Toast.makeText(getApplicationContext(), "Encendiendo Las Luces De La Entrada",
                    Toast.LENGTH_LONG).show();

        } else if(Entluz.equals("Encendida")){
            publicarMqtt(Topic2, "apagarentradaluz");
            Toast.makeText(getApplicationContext(), "Apagando Las Luces De La Entrada",
                    Toast.LENGTH_LONG).show();
        }
    }



    public static void conectarMqtt() {
        try {
            Log.i(TAG, "Conectando al broker " + broker);
            client = new MqttClient(broker, clientId, new MemoryPersistence());
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setKeepAliveInterval(60);
            connOpts.setWill(topicRoot + "WillTopic", "App desconectada".getBytes(),
                    qos, false);
            client.connect(connOpts);

        } catch (MqttException e) {
            Log.e(TAG, "Error al conectar.", e);
        }
    }

    public static void publicarMqtt(String topic, String mensageStr) {
        try {
            MqttMessage message = new MqttMessage(mensageStr.getBytes());
            message.setQos(qos);
            message.setRetained(false);
            client.publish(topicRoot + topic, message);
            Log.i(TAG, "Publicando mensaje: " + topic + "->" + mensageStr);
        } catch (MqttException e) {
            Log.e(TAG, "Error al publicar." + e);
        }
    }

}



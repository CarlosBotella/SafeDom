/*package com.example.safedom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.safedom.LoginActivity;
import com.example.safedom.LoginSignup.UserHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupProfileActivity extends AppCompatActivity {

    String fullName, fullemail, address, password, gender, date;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri selectedImage;
    ProgressDialog dialog;
    CircleImageView imageView;
    Button continueBtn;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_profile);

        imageView = findViewById(R.id.ImageViewId);
        continueBtn = findViewById(R.id.continue_btn);
        textView = findViewById(R.id.TextViewId);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Updating profile...");
        dialog.setCancelable(false);

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();

        fullName = getIntent().getStringExtra("fullname");
        fullemail = getIntent().getStringExtra("email");
        address = getIntent().getStringExtra("address");
        password = getIntent().getStringExtra("password");
        gender = getIntent().getStringExtra("gender");
        date = getIntent().getStringExtra("date");

        textView.setText(fullName);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 45);
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();
                if (selectedImage != null) {
                    StorageReference reference = storage.getReference().child("Profiles").child(auth.getCurrentUser().getPhoneNumber());
                    reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        String imageUrl = uri.toString();
                                        String phone = auth.getCurrentUser().getPhoneNumber();

                                        UserHelperClass addNewUser = new UserHelperClass(fullName, fullemail, address, password, gender, date, phone, imageUrl);

                                        database.getReference()
                                                .child("Users")
                                                .child(phone)
                                                .setValue(addNewUser)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        dialog.dismiss();
                                                        Intent intent = new Intent(SetupProfileActivity.this, LoginActivity.class);
                                                        Toast.makeText(SetupProfileActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });

                                    }
                                });
                            }
                        }
                    });
                } else {

                    String phone = auth.getCurrentUser().getPhoneNumber();

                    UserHelperClass addNewUser = new UserHelperClass(fullName, fullemail, address, password, gender, date, phone, "No Image");

                    database.getReference()
                            .child("Users")
                            .child(phone)
                            .setValue(addNewUser)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(SetupProfileActivity.this, LoginActivity.class);
                                    Toast.makeText(SetupProfileActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    finish();
                                }
                            });
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (data.getData() != null) {
                Uri uri = data.getData(); // filepath
                FirebaseStorage storage = FirebaseStorage.getInstance();
                long time = new Date().getTime();
                StorageReference reference = storage.getReference().child("Profiles").child(time + "");
                reference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String filePath = uri.toString();
                                    HashMap<String, Object> obj = new HashMap<>();
                                    obj.put("image", filePath);
                                    database.getReference().child("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                                            .updateChildren(obj).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    });
                                }
                            });
                        }
                    }
                });
                imageView.setImageURI(data.getData());
                selectedImage = data.getData();
            }
        }
    }
}*/
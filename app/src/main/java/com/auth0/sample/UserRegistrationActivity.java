package com.auth0.sample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

public class UserRegistrationActivity extends AppCompatActivity {
    private StorageReference photoRef;
    ProgressDialog pbDiaglog;
    private DatabaseReference databaseReference;
    private Uri photoUri;
    private StorageReference storageReference;
    private String imageUrl,full_name,address,pincode,phone_number;
    private ImageView iv_image;
    private EditText et_full_name,et_address,et_pincode,et_phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        imageUrl="";
        iv_image=findViewById(R.id.user_img);
        et_full_name=findViewById(R.id.et_full_name);
        et_pincode=findViewById(R.id.et_pincode);
        et_phone_number=findViewById(R.id.et_phone_number);
        et_address=findViewById(R.id.et_address);
        storageReference = FirebaseStorage.getInstance().getReference().child("User_Photos");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
    }

    public void change_dp(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }
    void UploadImage(){
        photoRef.putFile(photoUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imageUrl=uri.toString();
                        pbDiaglog.cancel();
                    }
                });
            }
        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(getApplicationContext(), (CharSequence) e, Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            pbDiaglog = new ProgressDialog(UserRegistrationActivity.this);
            pbDiaglog.setMessage("Uploading Image");
            pbDiaglog.show();
            photoUri = data.getData();
            iv_image.setImageURI(photoUri);
            photoRef = storageReference.child(photoUri.getLastPathSegment());
            UploadImage();
        }
    }

    public void set_details(View view) {
        full_name=et_full_name.getText().toString();
        address=et_address.getText().toString();
        pincode=et_pincode.getText().toString();
        phone_number=et_phone_number.getText().toString();
        User newUser=new User(full_name,phone_number,address,pincode,imageUrl);
        databaseReference.

    }
}
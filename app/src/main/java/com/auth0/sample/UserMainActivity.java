package com.auth0.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserMainActivity extends AppCompatActivity {
    private String address,pincode,latlong,phoneNumber,landmark,weight,title,payment,time,email,city;
    private EditText et_title,et_address,et_landmark,et_weight,et_payment,et_time,et_phone_number,et_pincode,et_city;
    private DatabaseReference databaseReference,dat2;
    private ScrollView scrollView;
    private LinearLayout ll_job,ll_destination;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        et_title=findViewById(R.id.et_job_description);
        ll_job=findViewById(R.id.ll_post);
        ll_destination=findViewById(R.id.ll_address_details);
        scrollView=findViewById(R.id.scroll_view);
        et_city=findViewById(R.id.et_city);
        et_address=findViewById(R.id.et_address);
        et_landmark=findViewById(R.id.et_landmark);
        et_time=findViewById(R.id.et_last_date);
        et_phone_number=findViewById(R.id.et_phone_number);
        et_pincode=findViewById(R.id.et_pincode);
        et_weight=findViewById(R.id.et_weight);
        et_payment=findViewById(R.id.et_price);
        SharedPreferences sh = getSharedPreferences("EmailVar", MODE_PRIVATE);
        email=sh.getString("Email", "someone@gmail.com");
//      dat2 = FirebaseDatabase.getInstance().getReference().child("User");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Jobs");

    }

    public void btn_discard(View view) {
        reset();
    }

    public void btn_post(View view) {
        payment=et_payment.getText().toString();
        time=et_time.getText().toString();
        title=et_title.getText().toString();
        address=et_address.getText().toString();
        landmark=et_landmark.getText().toString();
        pincode=et_pincode.getText().toString();
        city=et_city.getText().toString();
        weight=et_weight.getText().toString();
        phoneNumber=et_phone_number.getText().toString();
        Job jb=new Job(email,email,phoneNumber,address,landmark,weight,payment,time,title,latlong,pincode,city);
        jb.setCity("lmp");
        databaseReference.push().setValue(jb);
        Toast.makeText(this, "Posted Successfully", Toast.LENGTH_SHORT).show();
        reset();
    }

    public void map_destination(View view) {
        Intent i=new Intent(this,MapActivity.class);
        i.putExtra("Caller","User");
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sh = getSharedPreferences("Location", MODE_PRIVATE);
        latlong=sh.getString("Destination", "null");
        if(!(latlong.equalsIgnoreCase("null")))
            Toast.makeText(this, "Captured Coordinates "+latlong, Toast.LENGTH_SHORT).show();
    }
    void reset(){
        et_phone_number.setText("");
        et_address.setText("");
        et_payment.setText("");
        et_weight.setText("");
        et_pincode.setText("");
        et_landmark.setText("");
        et_title.setText("");
        et_time.setText("");
        et_city.setText("");
    }

    public void set_details(View view) {
        focusOnView(1);
    }

    public void set_destination(View view) {
        focusOnView(2);
    }
    private void focusOnView(int val){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if(val==1){
                    scrollView.scrollTo(0, ll_destination.getTop());
                }
                else if(val==2){
                    scrollView.scrollTo(0, ll_job.getTop());
                }
                else{
                    scrollView.scrollTo(0, ll_job.getTop());
                }
            }
        });
    }
}
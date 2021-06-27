package com.auth0.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.auth0.sample.classes.Job;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class SingleViewActivity extends AppCompatActivity {
    private String address,pincode,latlong,phoneNumber,landmark,weight,title,payment,time,email,city,key,full_name;
    private TextView et_title,et_address,et_landmark,et_weight,et_payment,et_time,et_phone_number,et_pincode,et_city,et_full_name;
    private DatabaseReference databaseReference,dat2;
    private String srt;
    private ScrollView scrollView;
    private LinearLayout ll_job,ll_destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_view);
        et_title=findViewById(R.id.et_job_description);
        ll_job=findViewById(R.id.ll_post);
        ll_destination=findViewById(R.id.ll_address_details);
        scrollView=findViewById(R.id.scroll_view);
        et_full_name=findViewById(R.id.et_full_name);
        et_city=findViewById(R.id.et_city);
        et_address=findViewById(R.id.et_address);
        et_landmark=findViewById(R.id.et_landmark);
        et_time=findViewById(R.id.et_last_date);
        et_phone_number=findViewById(R.id.et_phone_number);
        et_pincode=findViewById(R.id.et_pincode);
        et_weight=findViewById(R.id.et_weight);
        et_payment=findViewById(R.id.et_price);
        Intent i=getIntent();
        srt=i.getStringExtra("Caller");
        if(srt.equalsIgnoreCase("User"))
            ll_job.setBackgroundResource(R.drawable.iagree_to_terms_box);
        else
            ll_job.setBackgroundResource(R.drawable.iagree_to_terms_box);
        Job jb=(Job)i.getSerializableExtra("Object");
//        SharedPreferences sh = getSharedPreferences("EmailVar", MODE_PRIVATE);
//        email=sh.getString("Email", "someone@gmail.com");
        UpdateDetails(jb);
        findTheComplainChild(jb);
        //dat2 = FirebaseDatabase.getInstance().getReference().child("User");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Jobs");
    }

    public void see_address_details(View view) {
        focusOnView(1);
    }

    public void map_destination(View view) {
        Intent i=new Intent(this,MapActivity.class);
        i.putExtra("Caller","Truker");
        i.putExtra("Latlong",latlong);
        startActivity(i);
    }

    public void btn_close(View view) {
        finish();
    }

    public void btn_bid(View view) {

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
    private void findTheComplainChild(Job compaint){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Jobs");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        key=collectMapData((Map<String,Object>) dataSnapshot.getValue(),compaint);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }

    private String collectMapData(Map<String, Object> value,Job complaint) {
        String Email,PhoneNubaer;
        String Title;
        for (Map.Entry<String, Object> entry : value.entrySet()){
            String key=entry.getKey().toString();
            Map singleUser = (Map) entry.getValue();
            Email= (String) singleUser.get("email");
            PhoneNubaer=(String) singleUser.get("phone_number");
            Title=(String) singleUser.get("job_title");
            if((Email.equalsIgnoreCase(complaint.getEmail()))
                    &&(PhoneNubaer.equalsIgnoreCase(complaint.getPhone_number()))&&(Title.equalsIgnoreCase(complaint.getJob_title()))){
                return key;
            }
        }
        return "dinNotFound";
    }
    void UpdateDetails(Job jb){
        address=jb.getAddress();
        email=jb.getEmail();
        phoneNumber=jb.getPhone_number();
        full_name=jb.getName();
        title=jb.getJob_title();
        time=jb.getTime();
        pincode=jb.getPincode();
        payment=jb.getPayment();
        weight=jb.getWeight();
        latlong=jb.getLatLong();
        landmark=jb.getLandmark();
        city=jb.getCity();
    }
    void pushDetails(){
        et_full_name.setText(full_name);
        et_address.setText(address);
        et_landmark.setText(landmark);
        et_city.setText(city);
        et_weight.setText(weight);
        et_payment.setText(payment);
        et_title.setText(title);
        et_phone_number.setText(phoneNumber);

    }

}
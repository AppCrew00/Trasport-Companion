package com.auth0.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.auth0.sample.classes.Bid;
import com.auth0.sample.classes.Job;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SingleViewActivity extends AppCompatActivity {
    private String address,pincode,latlong,phoneNumber,landmark,weight,title,payment,time,email,city,key,full_name;
    private TextView et_title,et_address,et_landmark,et_weight,et_payment,et_time,et_phone_number,et_pincode,et_city,et_full_name,et_email;
    private DatabaseReference databaseReference,dat2;
    private String srt;
    private Job jbr;
    private ScrollView scrollView;
    private ProgressDialog pb;
    private List<Bid> lst = new ArrayList<>();
    private LinearLayout ll_job,ll_destination;
    private String trucker_email,trucker_phone_number,proposed_time,proposed_money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_view);
        et_title=findViewById(R.id.et_job_description);
        ll_job=findViewById(R.id.ll_post);
        ll_destination=findViewById(R.id.ll_address_details);
        scrollView=findViewById(R.id.scroll_view);
        et_full_name=findViewById(R.id.et_full_name);
        et_email=findViewById(R.id.et_email);
        et_city=findViewById(R.id.et_city);
        et_address=findViewById(R.id.et_address);
        et_landmark=findViewById(R.id.et_landmark);
        et_time=findViewById(R.id.et_last_date);
        et_phone_number=findViewById(R.id.et_phone_number);
        et_pincode=findViewById(R.id.et_pincode);
        et_weight=findViewById(R.id.et_weight);
        et_payment=findViewById(R.id.et_price);
        pb=new ProgressDialog(this);
        pb.setMessage("Please Wait.......");
        pb.show();
        Intent i=getIntent();
        srt=getIntent().getStringExtra("Caller");
        Job jb=(Job)i.getSerializableExtra("Object");
        jbr=jb;
        if(srt.equalsIgnoreCase("User"))
            ll_job.setBackgroundResource(R.drawable.close_see_bids);
        else {
            ll_job.setBackgroundResource(R.drawable.place_seebids);
            SharedPreferences sh = getSharedPreferences("EmailVar", MODE_PRIVATE);
            trucker_email=sh.getString("name","someone@gmail.com");
            SharedPreferences shr = getSharedPreferences("EmailVar", MODE_PRIVATE);
            trucker_phone_number=shr.getString("phone" ,"9140266326");
            lst=jb.getLst();
        }
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
        if(srt.equalsIgnoreCase("User")){
            finish();
        }
        else{
            LayoutInflater factory = LayoutInflater.from(this);
            final View deleteDialogView = factory.inflate(R.layout.diaglog_box, null);
            final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
            deleteDialog.setView(deleteDialogView);
            EditText et3=(EditText) deleteDialogView.findViewById(R.id.et_time);
            EditText et4=(EditText) deleteDialogView.findViewById(R.id.et_cost);
            deleteDialogView.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    proposed_money=et3.getText().toString();
                    proposed_time=et4.getText().toString();
                    Bid bd=new Bid(trucker_email,trucker_phone_number,proposed_money,proposed_time);
                    //lst=jbr.getLst();
                   // lst.add(bd);
                    //jbr.setLst(lst);
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Jobs").child(key).child("list");
                    databaseReference.push().setValue(bd);
                    Toast.makeText(SingleViewActivity.this, "Bidden Successfully", Toast.LENGTH_SHORT).show();
                    deleteDialog.dismiss();
                }
            });
            EditText et1=(EditText) deleteDialogView.findViewById(R.id.et_name);
            et1.setText(full_name);
            EditText et2=(EditText) deleteDialogView.findViewById(R.id.et_phone);
            et2.setText(phoneNumber);
            deleteDialog.show();
        }
    }

    public void btn_bid(View view) {

        Intent intent = new Intent( this , BidRecyclerAdapter.class );
        intent.putExtra("bid",key);
        startActivity(intent);
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
                pb.cancel();
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
        pushDetails();
    }
    void pushDetails(){
        et_full_name.setText(full_name);
        et_address.setText(address);
        et_landmark.setText(landmark);
        et_city.setText(city);
        et_weight.setText(weight);
        et_payment.setText(payment);
        et_pincode.setText(pincode);
        et_title.setText(title);
        et_phone_number.setText(phoneNumber);
        et_time.setText(time);
        et_email.setText(email);
    }
}
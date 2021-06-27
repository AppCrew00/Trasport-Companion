package com.auth0.sample.seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.auth0.sample.R;
import com.auth0.sample.classes.SellerInfo;
import com.auth0.sample.databinding.ActivitySellerMainBinding;

public class SellerMainActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivitySellerMainBinding binding;

    private Location location_App;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySellerMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.done.setOnClickListener(this::onClick);

        sharedPreferences = getSharedPreferences("EmailVar", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

                Log.d("ttttttttttt" , location.toString());
                location_App = location;
            }
        };

        if (ContextCompat.checkSelfPermission( this , Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0 ,0 ,locationListener);
            //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0 ,0 ,locationListener);
        }
    }

    @Override
    public void onClick(View v) {

        switch ( v.getId() )
        {
            case R.id.done:

                SellerInfo sellerInfo = new SellerInfo();
                sellerInfo.setName(binding.name.getText().toString());
                sellerInfo.setVehicle_no(binding.vehicleNumber.getText().toString());
                sellerInfo.setPhoneNo(binding.phonoNo.getText().toString());
                sellerInfo.setZipcode(binding.zipcode.getText().toString());
                sellerInfo.setLatitude(location_App.getLatitude());
                sellerInfo.setLongitude(location_App.getLongitude());
                sellerInfo.setEmail(sharedPreferences.getString("Email",""));

                editor.putString("pincode",binding.zipcode.getText().toString());
                editor.putString("phone",binding.phonoNo.getText().toString());
                editor.putString("name",binding.name.getText().toString());
                editor.commit();

                Intent intent1 = new Intent(this,SellerItemSelector.class);
                intent1.putExtra("info",sellerInfo);
                startActivity(intent1);
                break;

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            if (ContextCompat.checkSelfPermission( this , Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0 ,0 ,locationListener);
                //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0 ,0 ,locationListener);
            }
        }
    }
}
package com.example.emp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.SmsManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "hello";
    public static String saveloc;
    //=============================
    int c=0,wait=0;
    Button button;
    Button button2;
    TextView textView, textView1;

    String number,message,msg1,message1;

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    final int REQUEST_CHECK_CODE =8989;
    //================================


    FusedLocationProviderClient floc;
    private LocationSettingsRequest.Builder builder;


    //==================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button2 = findViewById(R.id.button2);

        floc = LocationServices.getFusedLocationProviderClient(this);


        //+_________________=-=-=_+_----------------------=-=-=-=_=-=-=-----------------------------

        number = "9921692237";
        message = "Location is : http://www.google.com/maps/place/";

        
        


        reqper();





        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {

                    try {
                        getLocation();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });


        SharedPreferences getshared = getSharedPreferences("demo", MODE_PRIVATE);
        String v = getshared.getString("str", "NA");

    }



    private void reqper() {

        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE

                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                //if (mu)

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();

            }
        }).onSameThread().check();


    }



    private void getLocation() throws InterruptedException {
        LocationRequest lr = new LocationRequest();
        lr.setInterval(1000);
        lr.setFastestInterval(3000);
        lr.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        //---------------------------------------------------------------------->>>>>>>>>>>>>>>

        builder = new LocationSettingsRequest.Builder().addLocationRequest(lr);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(this).checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    task.getResult(ApiException.class);
                } catch (ApiException e) {
                    switch (e.getStatusCode())
                    {
                        case LocationSettingsStatusCodes
                            .RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException =(ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(MainActivity.this,REQUEST_CHECK_CODE);
                            } catch (IntentSender.SendIntentException ex){
                                ex.printStackTrace();
                            }catch (ClassCastException ex){

                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        {
                            break;
                        }

                    }

                }
            }
        });

        //---------------------------------------------------------------------->>>>>>>>>>>>>>>>>>>

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        LocationServices.getFusedLocationProviderClient(MainActivity.this)
                .requestLocationUpdates(lr, new LocationCallback() {

                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(MainActivity.this)
                                .removeLocationUpdates(this);
                        if(locationResult !=null && locationResult.getLocations().size()>0){
                            int latestLocationIndex = locationResult.getLocations().size() - 1;
                            double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();



                            try {
                                TimeUnit.SECONDS.sleep(0);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }


                            message=message+String.format(
                                    "%s,%s",
                                    latitude,
                                    longitude
                            );

                            saveloc=String.format(
                                    "%s,%s",
                                    latitude,
                                    longitude
                            );;

                            message1=String.format(
                                    "%s,%s",
                                    latitude,
                                    longitude
                            );

                            if(chechPermission(Manifest.permission.SEND_SMS)){
                                //button2.setEnabled(true);
                                try {
                                    onSend();
                                    latitude= Double.NaN;
                                    longitude= Double.NaN;
                                    message = "Location is : http://www.google.com/maps/place/";
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            else{
                               c=1;
                            }
                        }
                    }
                }, Looper.getMainLooper());

            if(c==1) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
                if(chechPermission(Manifest.permission.SEND_SMS)){
                    //button2.setEnabled(true);
                    try {
                        onSend();
                        message = "Location is : http://www.google.com/maps/place/";
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }




    }

//===============================================================================================================================================


public void onSend() throws InterruptedException {

        String phone = number;
        String msg = message ;




            if (chechPermission(Manifest.permission.SEND_SMS)) {
                SmsManager smsManager = SmsManager.getDefault();
               // smsManager.sendTextMessage(phone, null, msg, null, null);
                Toast.makeText(this, "msg sent", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onSend: sent");

                //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                // for TCP communication

                MessageSender messageSender = new MessageSender();
                messageSender.execute(message1);
                openactivitywait();


                //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.

            } else {
                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
            }


}

    private void openactivitywait() {

        Intent intent= new Intent(this, waiting.class);
        startActivity(intent);
    }

    public boolean chechPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this,permission);
        return (check == PackageManager.PERMISSION_GRANTED);

    }

}




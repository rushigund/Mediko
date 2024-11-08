package com.example.center;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
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

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "hello";
    TextView textView,textView1,textView2,textView3,textView4;
    Button button;
    String message="hello";

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    final int REQUEST_CHECK_CODE =8989;
    //================================


    FusedLocationProviderClient floc;
    private LocationSettingsRequest.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
        boolean first = pref.getBoolean("first",true);
        if (first){

            textView = findViewById(R.id.editTextTextPersonName);
        textView1 = findViewById(R.id.editTextTextPersonName2);
        textView2 = findViewById(R.id.editTextTextPersonName3);
        textView3= findViewById(R.id.editTextTextPersonName4);
            textView4= findViewById(R.id.editTextTextPersonName5);

        button = findViewById(R.id.button1);

        reqper();


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String msg = textView.getText().toString();
                String msg2 = textView1.getText().toString();
                String msg3 = textView2.getText().toString();
                String msg4 = textView3.getText().toString();
                String msg5 = textView4.getText().toString();

                if (msg.equalsIgnoreCase("")) {
                    textView.setError("please enter Organization name");//it gives user to info message //use any one //
                } else if (msg2.equalsIgnoreCase("")) {
                    textView1.setError("please enter Organization Conatact No");//it gives user to info message //use any one //
                } else if (msg3.equalsIgnoreCase("")) {
                    textView2.setError("please enter Organization Addresss");//it gives user to info message //use any one //
                }else if (msg4.equalsIgnoreCase("")) {
                    textView3.setError("please enter Organization Email");//it gives user to info message //use any one //
                }else if (msg5.equalsIgnoreCase("")) {
                    textView4.setError("please enter Password");//it gives user to info message //use any one //
                } else {

                    String Fmsg = String.format(
                            "%s/%s/%s/%s/%s/",
                            msg,
                            msg2,
                            msg4,
                            msg5,
                            msg3
                    );


                    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED) {

                        try {
                            getLocation(Fmsg);


                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                    }

                    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


                }


            }
        });

    }
        else{
            openActivity1();
        }
    }

    private void getLocation(final String Fmsg) throws InterruptedException {


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


                             message =String.format(
                                    "%s/%s/%s",
                                    Fmsg,
                                    latitude,
                                    longitude
                            );

                            MessageSender messageSender = new MessageSender();
                            messageSender.execute(message);
                           // Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

                          //  openActivity1();

                            String init= messageSender.init;
                            String Id;
                            while(init=="-10") {
                                Id = messageSender.Id;
                                Log.d(TAG, "Id is:"+ Id);

                                init= messageSender.init;
                                Id = messageSender.Id;
                                if (!Id.equals("-1")) {
                                    Log.d(TAG, "Id taken is:"+ Id);
                                    SharedPreferences shrd = getSharedPreferences("demo", MODE_PRIVATE);
                                    SharedPreferences.Editor ed = shrd.edit();

                                    ed.putString("str", Id);
                                    ed.apply();

                                    SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putBoolean("first",false);
                                    editor.apply();

                                    openActivity1();
                                    break;
                                }
                                else
                                if(init.equals("1") && Id.equals("-1")){
                                    Log.d(TAG, "onLocationResult: 5 Already registered" );
                                    Toast.makeText(MainActivity.this,"Email Aleady Registered",Toast.LENGTH_LONG).show();
                                    SharedPreferences shrd = getSharedPreferences("demo", MODE_PRIVATE);
                                    SharedPreferences.Editor ed = shrd.edit();

                                    ed.putString("str", "-1");
                                    ed.apply();
                                   // openActivity1();

                                }



                            }




                        }
                    }
                }, Looper.getMainLooper());







    }


    private void openActivity1() {

        Log.d(TAG, "openning activity 1" );
        Intent intent= new Intent(this, Activity2.class);
        startActivity(intent);
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


}
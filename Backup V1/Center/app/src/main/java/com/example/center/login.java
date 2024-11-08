package com.example.center;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {

    Button login,sign;
    TextView email,pass;

    private static final String TAG = "Hello";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ExampleThread thread = new ExampleThread();
        thread.start();


    }

    class ExampleThread extends Thread {

        public void run() {

            SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
            boolean first = pref.getBoolean("first", true);
            if (first) {

                login = findViewById(R.id.button);
                sign = findViewById(R.id.button3);


                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        email = findViewById(R.id.editTextTextPersonName);
                        pass = findViewById(R.id.editTextTextPassword);

                        SharedPreferences semail = getSharedPreferences("email", MODE_PRIVATE);
                        SharedPreferences.Editor ed = semail.edit();
                        ed.putString("email", String.valueOf(email));
                        ed.apply();

                        SharedPreferences spass = getSharedPreferences("pass", MODE_PRIVATE);
                        SharedPreferences.Editor ed1 = spass.edit();
                        ed1.putString("pass", String.valueOf(pass));
                        ed1.apply();

                        String ms= email.getText().toString();
                        String ms1= pass.getText().toString();

                        String message = String.format(
                                "%s/%s",
                                ms,
                                ms1
                        );

                        int cout=0;

                        while (true) {
                            cout=cout+1;
                            if(cout<10) {
                                loginmessage loginmessage = new loginmessage();
                                loginmessage.execute(message);

                                String res = "";
                                res = loginmessage.logId;

                                if (res != "-1") {
                                    if (res.equals("Y")) {


                                        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putBoolean("first", false);
                                        editor.apply();

                                        openactivitystart();
                                        break;

                                    } else if (res.equals("N")) {
                                        Toast.makeText(com.example.center.login.this, "Invalid Email or Password", Toast.LENGTH_LONG).show();
                                        openeactivitylogin();
                                        break;
                                    }
                                }
                            }

                        }
                    }
                });


                sign.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openactivitysign();
                    }
                });
            } else {
                openactivitystart();
            }

        }
    }

    private void openactivitysign() {

        Log.d(TAG, "openning sign activity " );
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void openeactivitylogin() {
        Log.d(TAG, "openning login activity " );
        Intent intent= new Intent(this, com.example.center.login.class);
        startActivity(intent);
    }

    private void openactivitystart() {

        Log.d(TAG, "openning start activity " );
        Intent intent= new Intent(this, Activity2.class);
        startActivity(intent);
    }
}
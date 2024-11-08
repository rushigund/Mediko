package com.example.emp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class signup extends AppCompatActivity {

    private static final String TAG = "Hello";
    TextView name, contact, econtact, email, pass;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ExampleThread thread =new ExampleThread();
        thread.start();


    }

    class ExampleThread extends Thread {

        public void run() {


            register = findViewById(R.id.button4);
            name = findViewById(R.id.editTextTextPersonName3);
            contact = findViewById(R.id.editTextPhone);
            econtact = findViewById(R.id.editTextPhone2);
            email = findViewById(R.id.editTextTextEmailAddress);
            pass = findViewById(R.id.editTextTextPassword2);

            
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    while (true) {



                        String ms=  name.getText().toString();
                        String ms1= contact.getText().toString();
                        String ms2= econtact.getText().toString();
                        String ms3= email.getText().toString();
                        String ms4= pass.getText().toString();


                        final String message = String.format(
                                "%s/%s/%s/%s/%s",
                                ms,
                                ms1,
                                ms2,
                                ms3,
                                ms4




                        );


                        signmessage Sign = new signmessage();
                        Sign.execute(message);

                        String res = "";
                        res = signmessage.logId;

                        if (res != "-1") {
                            if (res.equals("Y")) {
                                Toast.makeText(signup.this, "Registered Successfully, Please Login", Toast.LENGTH_LONG).show();
                                openavtivitylogin();
                                break;
                            } else if (res.equals("N")) {
                                Toast.makeText(signup.this, "Contact or Email is already Registered", Toast.LENGTH_LONG).show();
                                openactivitysign();
                                break;
                            }
                        }
                    }
                }
            });

        }
    }



    private void openactivitysign() {

        Log.d(TAG, "openning sign activity " );
        Intent intent= new Intent(this, signup.class);
        startActivity(intent);
    }


    private void openavtivitylogin() {
        Log.d(TAG, "openning login activity " );
        Intent intent= new Intent(this, login.class);
        startActivity(intent);
    }
}
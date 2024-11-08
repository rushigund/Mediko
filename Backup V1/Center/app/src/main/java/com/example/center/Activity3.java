package com.example.center;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class Activity3 extends AppCompatActivity {

    private static final String TAG ="hello" ;
    TextView textView;
    Button acc;
    Button rej;

    public String setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        ExampleThread thread=new ExampleThread();
        thread.start();

       /* textView = findViewById(R.id.textView1);
        acc = findViewById(R.id.button);
        rej = findViewById(R.id.button1);


        SharedPreferences shrd = getSharedPreferences("demo", MODE_PRIVATE);
        setting = shrd.getString("str", "defaultValue");


        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = String.format(
                        "%s/%s",
                        setting,
                        "Y"
                );
                MessageSender3 messageSender3 = new MessageSender3();
                messageSender3.execute(message);

                Uri uri = Uri.parse("http://www.google.com"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        rej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = String.format(
                        "%s/%s",
                        setting,
                        "N"
                );
                MessageSender3 messageSender3 = new MessageSender3();
                messageSender3.execute(message);
            }
        });*/
    }

    class ExampleThread extends Thread{

        public void run()
        {
            textView = findViewById(R.id.textView1);
            acc = findViewById(R.id.button);
            rej = findViewById(R.id.button2);

            final String link=MessageSender2.loc;
            Log.d(TAG, link);


            SharedPreferences shrd = getSharedPreferences("demo", MODE_PRIVATE);
            setting = shrd.getString("str", "defaultValue");


            acc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    while (true) {

                        String message = String.format(
                                "%s/%s/%s",
                                setting,
                                "Y",
                                link
                        );
                        MessageSender3 messageSender3 = new MessageSender3();
                        messageSender3.execute(message);

                        //      /*
                        String res = "";
                        res = MessageSender3.res;
                        Log.d(TAG, "res is: " + res);
                        if (res != null) {
                            if (res.equals("Y")) {
                                openActivity1();
                                Uri uri = Uri.parse("http://www.google.com/maps/place/" + link); // missing 'http://' will cause crashed
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                                break;

                            } else {
                                MessageSender2.loc="-1";
                                openActivity1();
                                Toast.makeText(Activity3.this, "Someone has Already Accepted Request, Sorry for inconvenience", Toast.LENGTH_SHORT).show();
                                break;

                            }

                            //      */


                   /*
                    openActivity1();
                    Uri uri = Uri.parse("http://www.google.com/maps/place/"+link); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);


                   */
                        }
                    }
                } });

            rej.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String message = String.format(
                            "%s/%s/%s",
                            setting,
                            "N",
                            link
                    );
                    MessageSender3 messageSender3 = new MessageSender3();
                    messageSender3.execute(message);
                    MessageSender2.loc="-1";
                    openActivity1();
                }
            });
        }

    }

    private void openActivity1() {

        Log.d(TAG, "openning activity 1" );
        Intent intent= new Intent(this, Activity2.class);
        startActivity(intent);
    }

}
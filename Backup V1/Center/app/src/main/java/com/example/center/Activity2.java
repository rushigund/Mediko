package com.example.center;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import static com.example.center.Notification.CHANNEL_1_ID;

public class Activity2 extends AppCompatActivity {
    private static final String TAG ="hello" ;
    private NotificationManagerCompat notificationManager;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);


        notificationManager = NotificationManagerCompat.from(this);
        ExampleThread thread=new ExampleThread();
        thread.start();


      /*  while(true){
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            SharedPreferences shrd = getSharedPreferences("demo", MODE_PRIVATE);
            String setting = shrd.getString("str", "defaultValue");
            Toast.makeText(Activity2.this, setting,Toast.LENGTH_SHORT).show();

            MessageSender2 messageSender2 = new MessageSender2();
            messageSender2.execute(setting);

            String ms=messageSender2.getloc();
            Log.d(TAG, ms);
            if(ms.equals("-1"))
            {

            }
            else{




                sendnote();
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                openactivity3();
                break;

            }

        }*/

    }

    public void startThread(View view){
        ExampleThread thread=new ExampleThread();
        thread.start();

    }

    public void stopThread(View view){

    }

    private void openactivity3() {

        Intent myintent= new Intent(this, Activity3.class);
        startActivity(myintent);




    }

    private void sendnote() {
        Intent resultIntent = new Intent(this,Activity3.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,1,resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        android.app.Notification notification = new NotificationCompat.Builder(Activity2.this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.help_foreground)
                .setContentTitle("Emergency")
                .setContentText("Some incident has happend, please check App for More info")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .build();
        notificationManager.notify(1, notification);
    }

    class ExampleThread extends Thread{

        public void run()
        {
            while(true){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                SharedPreferences shrd = getSharedPreferences("demo", MODE_PRIVATE);
                String setting = shrd.getString("str", "defaultValue");
//                Toast.makeText(Activity2.this, setting,Toast.LENGTH_SHORT).show();

                MessageSender2 messageSender2 = new MessageSender2();
                messageSender2.execute(setting);

                String ms=messageSender2.getloc();
                Log.d(TAG, ms);
                if(ms.equals("-1"))
                {

                }
                else{



                    openactivity3();
                    sendnote();
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    break;

                }

            }
        }
    }


}
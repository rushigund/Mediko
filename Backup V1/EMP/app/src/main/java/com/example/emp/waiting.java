package com.example.emp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.concurrent.TimeUnit;

public class waiting extends AppCompatActivity {

    String message,check;
    private static final String TAG = "hello";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        message=MainActivity.saveloc;

        ExampleThread thread=new ExampleThread();
        thread.start();

        
    }


    class ExampleThread extends Thread{

        public void run()
        {
            while(true) {
                messageSender1 messageSender1 = new messageSender1();
                messageSender1.execute(message);

                check = messageSender1.Id;
                Log.d(TAG, "run: "+check);
                if (check.equals("Y")) {
                    openActivity2();
                    break;

                }

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    private void openActivity2() {
        Intent intent= new Intent(this, gotHelp.class);
        startActivity(intent);
    }
}
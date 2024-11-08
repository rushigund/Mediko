package com.example.emp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class StartActivity extends AppCompatActivity {

    private ImageView img1;
    private ImageView img2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        img1=(ImageView) findViewById(R.id.ser);
        img2=(ImageView) findViewById(R.id.res);
        img1.setClipToOutline(true);
        img2.setClipToOutline(true);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });


        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openactivitymain();

            }
        });
    }

    private void openactivitymain() {

        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
package com.example.center;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.util.TimeUtils;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.CharBuffer;
import java.util.concurrent.TimeUnit;

public class MessageSender2 extends AsyncTask<String,Void,Void> {


    private static final String TAG ="hello" ;

    private static Context context;
    Socket s;
    DataOutputStream dout;
    PrintWriter pw;
    BufferedReader br;
    DataInputStream din;
    static String loc ="-1";

    private InputStreamReader isr;

    @Override
    protected Void doInBackground(String... voids)
    {

        String message=voids[0];
        String message1;

        while(true) {

            try {
                Log.d(TAG, "doInBackground:1 in 2 ");
                s=new Socket("192.168.0.100",7900);
                //s = new Socket("104.198.168.13", 8000);
                dout = new DataOutputStream(s.getOutputStream());
                //pw= new PrintWriter(s.getOutputStream());
                din = new DataInputStream(s.getInputStream());


                // pw.write(message);
                dout.writeUTF(message);
                dout.flush();

                Log.d(TAG, "doInBackground: 2 in 2");

                String str = din.readUTF();//in.readLine();
                Log.d(TAG, "doInBackground:3  in 2 receivd is" + str);
                if (!str.equals("-1")) {

                    loc=str;
                    break;

                }
                    //code for accepting


                Log.d(TAG, "doInBackground:3  in 2" + str);


                din.close();


                s.close();


            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private Context getContext() {

        return MessageSender.context;
    }

    public String getloc() {
        Log.d(TAG, "getloc: is"+ this.loc);
        return this.loc;
    }
}

package com.example.center;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
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

public class MessageSender extends AsyncTask<String,Void,Void> {


    public static Context context;
    public String init ="-10";
    public String Id ="-1";


    private static final String TAG ="hello" ;
    Socket s;
    DataOutputStream dout;
    PrintWriter pw;
    BufferedReader br;
    DataInputStream din;

    private InputStreamReader isr;

    @Override
    protected Void doInBackground(String... voids)
    {

        String message=voids[0];
        String message1;


        try{
            Log.d(TAG, "doInBackground:1 ");
            s=new Socket("192.168.0.100",7900);
            //s=new Socket("104.198.168.13",7900);
            dout=new DataOutputStream(s.getOutputStream());
            //pw= new PrintWriter(s.getOutputStream());
            din=new DataInputStream(s.getInputStream());


           // pw.write(message);
            dout.writeUTF(message);
            dout.flush();

            Log.d(TAG, "doInBackground: 2");

           // isr =new InputStreamReader(s.getInputStream());
           // br = new BufferedReader(isr);
           // message1 = br.readLine();

            String str = din.readUTF();//in.readLine();
            if(str!="-1"){
                Id=str;
            }


         //   System.out.println("Message"+str);

           // Toast.makeText (getContext(), "hello1", Toast.LENGTH_SHORT).show();

            Log.d(TAG, "doInBackground:3 "+ str);


            din.close();
           // pw.flush();
           // pw.close();
           // System.out.println(message1);



           s.close();




        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Context getContext() {

        return MessageSender.context;
    }
}

package com.example.emp;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MessageSender extends AsyncTask<String,Void,Void> {


    Socket s;
    DataOutputStream dos;
    PrintWriter pw;


    @Override
    protected Void doInBackground(String... voids)
    {

        String message=voids[0];


        try{
            s=new Socket("192.168.0.100",7800);
            //s=new Socket("104.198.168.13",7800);
            pw= new PrintWriter(s.getOutputStream());
            pw.write(message);
            pw.flush();
            pw.close();
            s.close();




        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

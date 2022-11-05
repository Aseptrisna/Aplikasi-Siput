package com.pptik.siput.ui;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.pptik.siput.R;
import com.pptik.siput.service.My_Rmq;
import com.pptik.siput.service.Rmq_Service;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class Dashboard_Screen extends AppCompatActivity implements My_Rmq {
    Rmq_Service rmq_service;
    TextView Status,Time;
    String queue="mqtt-subscription-esp8266-client-50:02:91:E0:3A:E9qos0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_screen);
        rmq_service=new Rmq_Service(this);
        Status=(TextView) findViewById(R.id.status);
        Time=(TextView) findViewById(R.id.time);
        getStatus();
        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                Log.i("tag", "A Kiss every 5 seconds");
                display_time();
            }
        },0,1000);
    }

    private void getStatus() {
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String status = sh.getString("status", "");
        Status.setText(status);
    }

    private void display_time() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String strDate = sdf.format(c.getTime());
        Log.d("Date","DATE : " + strDate);
        Time.setText(strDate);
    }

    @Override
    public void Berhasil(String message) {
        Toast.makeText(this, "Succes Send Data", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void Gagal() {
        Toast.makeText(this, "Gagal send data", Toast.LENGTH_SHORT).show();


    }

    public void Up(View view) {
        Status.setText("UP");
        Save_Status("UP");
        try {
            rmq_service.setupConnectionFactory();
            rmq_service.publish("0",queue);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

    }

    private void Save_Status(String status) {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("status",status);
        myEdit.apply();
    }

    public void Down(View view) {
        Status.setText("DOWN");
        Save_Status("DOWN");
        try {
            rmq_service.setupConnectionFactory();
            rmq_service.publish("1",queue);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public void OFF(View view) {
        Status.setText("OFF");
        Save_Status("OFF");
        try {
            rmq_service.setupConnectionFactory();
            rmq_service.publish("2",queue);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public void RIGHT(View view) {
        Status.setText("RIGHT");
        Save_Status("RIGHT");
        try {
            rmq_service.setupConnectionFactory();
            rmq_service.publish("0",queue);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public void LEFT(View view) {
        Status.setText("LEFT");
        Save_Status("LEFT");
        try {
            rmq_service.setupConnectionFactory();
            rmq_service.publish("1",queue);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
}
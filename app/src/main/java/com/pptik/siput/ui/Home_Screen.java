package com.pptik.siput.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.pptik.siput.R;
import com.pptik.siput.service.My_Rmq;
import com.pptik.siput.service.Rmq_Service;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class Home_Screen extends AppCompatActivity implements My_Rmq {
    Switch lampu1,lampu2,lampu3;
    TextView s_lampu1,s_lampu2,s_lampu3;
    Rmq_Service rmq_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        rmq_service=new Rmq_Service(this);
        lampu1=(Switch) findViewById(R.id.lampu1);
        lampu2=(Switch) findViewById(R.id.lampu2);
        lampu3=(Switch) findViewById(R.id.lampu3);
        s_lampu1=(TextView) findViewById(R.id.statuslampu1);
        s_lampu2=(TextView) findViewById(R.id.statuslampu2);
        s_lampu3=(TextView) findViewById(R.id.statuslampu3);
        lampu1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean on){
                if(on) {
                    s_lampu1.setText("ON");
                   control("Lampu1","0");

                } else {
                    //Do something when Switch is off/unchecked
                    s_lampu1.setText("OFF");
                    control("Lampu1","1");
                }
            }
        });
        lampu2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean on){
                if(on) {
                    //Do something when Switch button is on/checked
                    s_lampu2.setText("ON");
                    control("Lampu2","0");
                }
                else {
                    //Do something when Switch is off/unchecked
                    s_lampu2.setText("OFF");
                    control("Lampu2","1");
                }
            }
        });
        lampu3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean on){
                if(on)
                {
                    //Do something when Switch button is on/checked
                    s_lampu3.setText("ON");
                    control("Lampu3","0");
                }
                else
                {
                    //Do something when Switch is off/unchecked
                    s_lampu3.setText("OFF");
                    control("Lampu3","1");
                }
            }
        });
    }

    @Override
    public void Berhasil(String message) {

    }

    @Override
    public void Gagal() {

    }
    private void control(String queue,String pesan){
        try {
            rmq_service.setupConnectionFactory();
            rmq_service.publish(pesan,queue);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
}
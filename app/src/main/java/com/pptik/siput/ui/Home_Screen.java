package com.pptik.siput.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.pptik.siput.R;
import com.pptik.siput.service.My_Rmq;
import com.pptik.siput.service.Rmq_Service;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class Home_Screen extends AppCompatActivity implements My_Rmq {
    Switch lampu1,lampu2,lampu3,Tv,Ac;
    TextView s_lampu1,s_lampu2,s_lampu3,s_tv,s_ac;
    Rmq_Service rmq_service;
    EditText Suhu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        rmq_service=new Rmq_Service(this);
        lampu1=(Switch) findViewById(R.id.lampu1);
        lampu2=(Switch) findViewById(R.id.lampu2);
        lampu3=(Switch) findViewById(R.id.lampu3);
        Tv=(Switch) findViewById(R.id.televisi);
        Ac=(Switch) findViewById(R.id.ac);
        s_lampu1=(TextView) findViewById(R.id.statuslampu1);
        s_lampu2=(TextView) findViewById(R.id.statuslampu2);
        s_lampu3=(TextView) findViewById(R.id.statuslampu3);
        s_ac=(TextView) findViewById(R.id.txtsuhu);
        s_tv=(TextView) findViewById(R.id.txttv);
        Suhu=(EditText)findViewById(R.id.suhu);
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
        Tv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean on){
                if(on)
                {
                    //Do something when Switch button is on/checked
                    s_tv.setText("ON");
                    control("Tv","0");
                }
                else
                {
                    //Do something when Switch is off/unchecked
                    s_tv.setText("OFF");
                    control("Tv","1");
                }
            }
        });
        Ac.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean on){
                if(on)
                {
                    //Do something when Switch button is on/checked
                    if (Suhu.getText().toString().isEmpty()){
                        Toast.makeText(Home_Screen.this, "Masukan Derajat Celcius", Toast.LENGTH_SHORT).show();
                    }else {
                        s_ac.setText("ON");
                        control("Ac", "Ac On Suhu:" + Suhu.getText().toString()+"°C");
                    }
                }
                else
                {
                    //Do something when Switch is off/unchecked
                    s_ac.setText("OFF");
                    control("Ac","Ac Off");
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

    public void goto_jadwal(View view) {
        Intent intent=new Intent(Home_Screen.this,SetJadwal_Screen.class);
        intent.putExtra("DATA", "Lampu1");
        startActivity(intent);
        finish();
    }

    public void goto_jadwal2(View view) {
        Intent intent=new Intent(Home_Screen.this,SetJadwal_Screen.class);
        intent.putExtra("DATA", "Lampu2");
        startActivity(intent);
        finish();
    }

    public void goto_jadwal3(View view) {
        Intent intent=new Intent(Home_Screen.this,SetJadwal_Screen.class);
        intent.putExtra("DATA", "Lampu3");
        startActivity(intent);
        finish();
    }

    public void goto_jadwaltv(View view) {
        Intent intent=new Intent(Home_Screen.this,SetJadwal_Screen.class);
        intent.putExtra("DATA", "Tv");
        startActivity(intent);
        finish();
    }

    public void goto_jadwalac(View view) {
        Intent intent=new Intent(Home_Screen.this,SetJadwal_Screen.class);
        intent.putExtra("DATA", "Ac");
        startActivity(intent);
        finish();
    }
}
package com.pptik.siput.ui;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import com.pptik.siput.R;
import com.pptik.siput.service.My_Rmq;
import com.pptik.siput.service.Rmq_Service;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class SetJadwal_Screen extends AppCompatActivity implements My_Rmq{
    EditText on,off;
    private TimePickerDialog timePickerDialog;
    Rmq_Service rmq_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_jadwal_screen);
        rmq_service=new Rmq_Service(this);
        on=(EditText) findViewById(R.id.jamon);
        off=(EditText) findViewById(R.id.jamoff);
        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            showTimestart();
            }
        });
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimestop();
            }
        });
    }

    public void Simpanjadwal(View view) {
        kirimon();
        kirimoff();

    }

    public void kirimon(){
        String queue="Jadwal";
        String channel=getIntent().getStringExtra("DATA");
        JSONObject obj=new JSONObject();
        try {
            obj.put("mac", "1609090209950001");
            obj.put("jam",on.getText().toString());
            obj.put("message", "0");
            obj.put("channel", channel);
            String message=obj.toString();
            rmq_service.setupConnectionFactory();
            rmq_service.publish(message,queue);
            Log.d("datapublish",message);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    public void kirimoff(){
        String queue="Jadwal";
        String channel=getIntent().getStringExtra("DATA");
        JSONObject obj=new JSONObject();
        try {
            obj.put("mac", "1609090209950001");
            obj.put("jam",off.getText().toString());
            obj.put("message", "1");
            obj.put("channel", channel);
            String message=obj.toString();
            rmq_service.setupConnectionFactory();
            rmq_service.publish(message,queue);
            Log.d("datapublish",message);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    private void showTimestart() {
        Calendar calendar = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            calendar = Calendar.getInstance();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            timePickerDialog = new TimePickerDialog(SetJadwal_Screen.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    on.setText(hourOfDay+"."+minute);
                }
            },
                    calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                    DateFormat.is24HourFormat(SetJadwal_Screen.this));
        }
        timePickerDialog.show();
    }
    private void showTimestop() {
        Calendar calendar = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            calendar = Calendar.getInstance();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            timePickerDialog = new TimePickerDialog(SetJadwal_Screen.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    off.setText(hourOfDay+"."+minute);
                }
            },
                    calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                    DateFormat.is24HourFormat(SetJadwal_Screen.this));
        }
        timePickerDialog.show();
    }

    @Override
    public void Berhasil(String message) {
        Intent intent=new Intent(SetJadwal_Screen.this,Home_Screen.class);
//        intent.putExtra("DATA", "Lampu2");
        startActivity(intent);
        finish();

    }

    @Override
    public void Gagal() {

    }
}
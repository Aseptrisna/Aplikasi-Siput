package com.pptik.siput.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.pptik.siput.R;

public class StartUp_Screen extends AppCompatActivity {
    ImageView IconApp;
    TextView AppName;
    Animation uptodown, downtoup,Fadein,FadeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up_screen);
        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        Fadein = AnimationUtils.loadAnimation(this, R.anim.to_left);
        FadeOut= AnimationUtils.loadAnimation(this, R.anim.to_right);
        IconApp=(ImageView)findViewById(R.id.iconapp);
        AppName=(TextView)findViewById(R.id.nameapp);
        IconApp.setAnimation(uptodown);
        AppName.setAnimation(downtoup);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goto_home();
            }
        }, 3000);
    }

    private void goto_home() {
        Intent intent=new Intent(StartUp_Screen.this,Dashboard_Screen.class);
        startActivity(intent);
        finish();
    }

}
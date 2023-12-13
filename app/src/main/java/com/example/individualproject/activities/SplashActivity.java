package com.example.individualproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.example.individualproject.R;

public class SplashActivity extends AppCompatActivity {

//    Handler handler;
//    Runnable runnable;
    ImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        img = findViewById(R.id.img_splash);
        // img.animate().alpha(0).setDuration(0);

//        handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(SplashActivity.this, RegisterActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, 3000);
    }
    public void letsGo(View view){
        startActivity(new Intent(SplashActivity.this, RegisterActivity.class));
    }
}
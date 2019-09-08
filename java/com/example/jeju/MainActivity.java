package com.example.jeju;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_page);

        iv = (ImageView) findViewById(R.id.iv);
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.transition);
        iv.startAnimation(anim);

        final Intent i = new Intent(this,MainActivity_2.class);
        Thread timer = new Thread(){
            public void run () {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();

                }
            }
        };
        timer.start();
    }
}


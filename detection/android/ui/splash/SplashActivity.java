package org.tensorflow.demo.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import org.tensorflow.demo.R;
import org.tensorflow.demo.ui.mainmenu.MainMenuActivity;

public class SplashActivity extends AppCompatActivity {

    private ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imgLogo = findViewById(R.id.img_logo);

        Animation anim = AnimationUtils.loadAnimation(this,R.anim.logo_transition);
        imgLogo.startAnimation(anim);
        int splashInterval = 3500;
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainMenuActivity.class));
            finish();
        }, splashInterval);
    }
}

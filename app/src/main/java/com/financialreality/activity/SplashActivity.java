package com.financialreality.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;
import com.financialreality.R;

public class SplashActivity extends AppCompatActivity {
    
    private static final int SPLASH_DELAY = 3000;
    private LottieAnimationView animationView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        
        animationView = findViewById(R.id.animationView);
        
        // Note: You must add your Lottie JSON file to app/src/main/res/raw/financial_animation.json
        // animationView.setAnimation(R.raw.financial_animation);
        // animationView.playAnimation();
        
        new Handler().postDelayed(() -> {
            // Navigate directly to MainActivity, bypassing login
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, SPLASH_DELAY);
    }
}

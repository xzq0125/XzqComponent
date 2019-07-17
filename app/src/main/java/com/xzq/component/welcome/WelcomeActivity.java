package com.xzq.component.welcome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xzq.component.MainActivity;


public class WelcomeActivity extends AppCompatActivity implements Runnable {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().postDelayed(this, 2000);
    }

    @Override
    public void run() {
        if (isFinishing()) {
            finish();
            return;
        }
        MainActivity.start(this);
        finish();
    }
}

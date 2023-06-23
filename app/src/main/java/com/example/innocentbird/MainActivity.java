package com.example.innocentbird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppHolder.assign(this.getApplicationContext());

    }
    public void startGame(View view)
    {
        Intent intent=new Intent(this,GameActivity.class);
        startActivity(intent);
        finish();
    }
}
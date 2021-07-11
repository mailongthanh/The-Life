package com.ntsua.thelife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class game2048_batdau extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2048_batdau);
    }
    public void btn_batdau2048(View view) {
        Intent i = new Intent(this,game2048.class);
        startActivity(i);
    }

    public void btn_quitgame2048(View view) {
        Intent i = new Intent(this,Playground.class);
        startActivity(i);
    }
}

package com.ntsua.thelife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AiLaTrieuPhu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ailatrieuphu);
    }

    public void btn_batdau(View view) {
        Intent i = new Intent(this,AiLaTrieuPhu_ing.class);
        startActivity(i);
    }

    public void btn_quitgame(View view) {
        //Intent i = new Intent(this,Playground.class);
       // startActivity(i);
        onBackPressed();
    }
}
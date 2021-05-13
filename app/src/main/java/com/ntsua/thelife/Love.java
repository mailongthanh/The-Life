package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Love extends AppCompatActivity {

    ListView lvLove;
    FoodAdapter adapter;
    ArrayList<Food> arrLove;
    TextView txtName, txtJob, txtMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love);

        txtName = findViewById(R.id.textviewName);
        txtJob = findViewById(R.id.textviewJob);
        txtMoney = findViewById(R.id.textviewMoney);
        loadGame();

        lvLove = (ListView) findViewById((R.id.listviewLove));
        arrLove = new ArrayList<>();
        arrLove.add(new Food("Mai mối", "", R.drawable.maimoi, 0));
        adapter = new FoodAdapter(this, R.layout.food_line, arrLove);
        lvLove.setAdapter(adapter);
    }

    private void loadGame() {
        txtName.setText(MainActivity.saveGame.getName());
        txtMoney.setText(MainActivity.saveGame.getMoney() + "VND");
        txtJob.setText(MainActivity.saveGame.getJob());
    }

    public void gotoMainMenu(View view)
    {
        startActivity(new Intent(Love.this, HoatDong.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }
}
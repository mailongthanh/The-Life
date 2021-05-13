package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Playground extends AppCompatActivity {

    ListView lvPlayground;
    FoodAdapter adapter;
    ArrayList<Food> arrPlayground;
    TextView txtName, txtJob, txtMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground);

        txtName = findViewById(R.id.textviewName);
        txtJob = findViewById(R.id.textviewJob);
        txtMoney = findViewById(R.id.textviewMoney);
        loadGame();

        lvPlayground = (ListView) findViewById((R.id.listviewPlayground));
        arrPlayground = new ArrayList<>();
        arrPlayground.add(new Food("Bầu cua", "", R.drawable.baucua, 0));
        arrPlayground.add(new Food("Ai là triệu phú", "", R.drawable.ailatrieuphu, 0));
        arrPlayground.add(new Food("Casino", "", R.drawable.casino, 0));
        adapter = new FoodAdapter(this, R.layout.food_line, arrPlayground);
        lvPlayground.setAdapter(adapter);
    }

    private void loadGame() {
        txtName.setText(MainActivity.saveGame.getName());
        txtMoney.setText(MainActivity.saveGame.getMoney() + "VND");
        txtJob.setText(MainActivity.saveGame.getJob());
    }

    public void gotoMainMenu(View view)
    {
        startActivity(new Intent(Playground.this, HoatDong.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }
}
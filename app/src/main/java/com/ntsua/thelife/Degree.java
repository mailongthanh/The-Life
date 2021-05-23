package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Degree extends AppCompatActivity {

    ListView lvDegree;
    FoodAdapter adapter;
    ArrayList<Food> arrDegree;
    TextView txtName, txtJob, txtMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_degree);

        txtName = findViewById(R.id.textviewName);
        txtJob = findViewById(R.id.textviewJob);
        txtMoney = findViewById(R.id.textviewMoney);
        loadGame();

        lvDegree = (ListView) findViewById((R.id.listviewDegree));
        arrDegree = new ArrayList<>();
        arrDegree.add(new Food("Bằng tiếng anh", "5 triệu", R.drawable.book, 0));
        arrDegree.add(new Food("Bằng lái xe", "600 nghìn", R.drawable.motorcycle, 0));
        adapter = new FoodAdapter(this, R.layout.food_line, arrDegree);
        lvDegree.setAdapter(adapter);
    }

    private void loadGame() {
        txtName.setText(MainActivity.saveGame.getName());
        txtMoney.setText(MainActivity.saveGame.getMoney() + "VND");
        txtJob.setText(MainActivity.saveGame.getJob());
    }

    public void gotoMainMenu(View view)
    {
        //startActivity(new Intent(Degree.this, HoatDong.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
        onBackPressed();
    }
}
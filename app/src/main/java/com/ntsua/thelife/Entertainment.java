package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.EmptyStackException;

public class Entertainment extends AppCompatActivity {

    ListView lvEntertainment;
    FoodAdapter adapter;
    ArrayList<Food> arrEntertainment;
    TextView txtName, txtJob, txtMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);

        txtName = findViewById(R.id.textviewName);
        txtJob = findViewById(R.id.textviewJob);
        txtMoney = findViewById(R.id.textviewMoney);
        loadGame();

        lvEntertainment = (ListView) findViewById((R.id.listviewEntertainment));
        arrEntertainment = new ArrayList<>();
        arrEntertainment.add(new Food("Bar", "1 triệu", R.drawable.bar, 1000));
        arrEntertainment.add(new Food("Đi xem phim", "80 nghìn", R.drawable.movie, 80));
        arrEntertainment.add(new Food("Thư viện", "Free", R.drawable.library, 0));
        adapter = new FoodAdapter(this, R.layout.food_line, arrEntertainment);
        lvEntertainment.setAdapter(adapter);
    }

    private void loadGame() {
        txtName.setText(MainActivity.saveGame.getName());
        txtMoney.setText(MainActivity.saveGame.getMoney() + "VND");
        txtJob.setText(MainActivity.saveGame.getJob());
    }

    public void gotoMainMenu(View view)
    {
        startActivity(new Intent(Entertainment.this, HoatDong.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }
}
package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class Playground extends AppCompatActivity {

    ListView lvPlayground;
    FoodAdapter adapter;
    ArrayList<Food> arrPlayground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground);
        lvPlayground = (ListView) findViewById((R.id.listviewPlayground));
        arrPlayground = new ArrayList<>();
        arrPlayground.add(new Food("Bầu cua", "", R.drawable.baucua, 0));
        arrPlayground.add(new Food("Ai là triệu phú", "", R.drawable.ailatrieuphu, 0));
        arrPlayground.add(new Food("Casino", "", R.drawable.casino, 0));
        adapter = new FoodAdapter(this, R.layout.food_line, arrPlayground);
        lvPlayground.setAdapter(adapter);
    }

    public void gotoMainMenu(View view)
    {
        startActivity(new Intent(Playground.this, HoatDong.class));
    }
}
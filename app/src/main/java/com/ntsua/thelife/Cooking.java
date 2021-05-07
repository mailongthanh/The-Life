package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class Cooking extends AppCompatActivity {

    ListView lvCooking;
    FoodAdapter adapter;
    ArrayList<Food> arrCooking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooking);
        lvCooking = (ListView) findViewById((R.id.listviewCooking));
        arrCooking = new ArrayList<>();
        arrCooking.add(new Food("Sushi", "", R.drawable.sushi, 0));
        arrCooking.add(new Food("Bún bò", "", R.drawable.bunbo, 0));
        arrCooking.add(new Food("Phở", "", R.drawable.pho, 0));
        arrCooking.add(new Food("Pizza", "", R.drawable.pizza, 0));
        arrCooking.add(new Food("Bánh xèo", "", R.drawable.banhxeo, 0));
        adapter = new FoodAdapter(this, R.layout.food_line, arrCooking);
        lvCooking.setAdapter(adapter);
    }

    public void gotoMainMenu(View view)
    {
        startActivity(new Intent(Cooking.this, HoatDong.class));
    }
}
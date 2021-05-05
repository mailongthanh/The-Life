package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class Degree extends AppCompatActivity {

    ListView lvDegree;
    FoodAdapter adapter;
    ArrayList<Food> arrDegree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_degree);
        lvDegree = (ListView) findViewById((R.id.listviewDegree));
        arrDegree = new ArrayList<>();
        arrDegree.add(new Food("Bằng tiếng anh", "5 triệu", R.drawable.book));
        arrDegree.add(new Food("Bằng lái xe", "600 nghìn", R.drawable.motorcycle));
        adapter = new FoodAdapter(this, R.layout.food_line, arrDegree);
        lvDegree.setAdapter(adapter);
    }

    public void gotoMainMenu(View view)
    {
        startActivity(new Intent(Degree.this, HoatDong.class));
    }
}
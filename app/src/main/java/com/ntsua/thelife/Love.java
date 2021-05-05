package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class Love extends AppCompatActivity {

    ListView lvLove;
    FoodAdapter adapter;
    ArrayList<Food> arrLove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love);
        lvLove = (ListView) findViewById((R.id.listviewLove));
        arrLove = new ArrayList<>();
        arrLove.add(new Food("Mai mối", "", R.drawable.maimoi));
        adapter = new FoodAdapter(this, R.layout.food_line, arrLove);
        lvLove.setAdapter(adapter);
    }

    public void gotoMainMenu(View view)
    {
        startActivity(new Intent(Love.this, HoatDong.class));
    }
}
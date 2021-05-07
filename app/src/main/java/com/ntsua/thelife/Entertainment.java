package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.EmptyStackException;

public class Entertainment extends AppCompatActivity {

    ListView lvEntertainment;
    FoodAdapter adapter;
    ArrayList<Food> arrEntertainment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);
        lvEntertainment = (ListView) findViewById((R.id.listviewEntertainment));
        arrEntertainment = new ArrayList<>();
        arrEntertainment.add(new Food("Bar", "1 triệu", R.drawable.bar, 1000));
        arrEntertainment.add(new Food("Đi xem phim", "80 nghìn", R.drawable.movie, 80));
        arrEntertainment.add(new Food("Thư viện", "Free", R.drawable.library, 0));
        adapter = new FoodAdapter(this, R.layout.food_line, arrEntertainment);
        lvEntertainment.setAdapter(adapter);
    }

    public void gotoMainMenu(View view)
    {
        startActivity(new Intent(Entertainment.this, HoatDong.class));
    }
}
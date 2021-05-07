package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class Travel extends AppCompatActivity {
    ListView lvTravel;
    FoodAdapter adapter;
    ArrayList<Food> arrTravel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
        lvTravel = (ListView) findViewById((R.id.listviewTravel));
        arrTravel = new ArrayList<>();
        arrTravel.add(new Food("Hong Kong", "", R.drawable.hongkong, 0));
        arrTravel.add(new Food("America", "", R.drawable.america, 0));
        arrTravel.add(new Food("Korea", "", R.drawable.korea, 0));
        arrTravel.add(new Food("ThaiLand", "", R.drawable.thailand, 0));
        arrTravel.add(new Food("Vũng Tàu", "", R.drawable.vungtau, 0));
        arrTravel.add(new Food("Phú Quốc", "", R.drawable.phuquoc, 0));
        arrTravel.add(new Food("Đà Nẵng", "", R.drawable.danang, 0));
        adapter = new FoodAdapter(this, R.layout.food_line, arrTravel);
        lvTravel.setAdapter(adapter);
    }

    public void gotoMainMenu(View view)
    {
        startActivity(new Intent(Travel.this, HoatDong.class));
    }
}
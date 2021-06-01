package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class SongKhac extends AppCompatActivity {

    ListView lvSongKhac;
    FoodAdapter adapter;
    ArrayList<Food> arrSongKhac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_khac);

        lvSongKhac = (ListView) findViewById((R.id.listviewSongKhac));
        arrSongKhac = new ArrayList<>();
        arrSongKhac.add(new Food("Cướp giật", "", R.drawable.knife, 0));
        arrSongKhac.add(new Food("Trộm chó", "", R.drawable.dog, 0));
        adapter = new FoodAdapter(this, R.layout.food_line, arrSongKhac);
        lvSongKhac.setAdapter(adapter);
    }

    public void gotoMainMenu(View view)
    {
        startActivity(new Intent(SongKhac.this, HoatDong.class));
    }
}
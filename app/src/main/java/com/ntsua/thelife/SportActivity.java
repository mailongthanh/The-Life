package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class SportActivity extends AppCompatActivity {

    ListView lvSport;
    FoodAdapter adapter;
    ArrayList<Food> arrSport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);

        lvSport = (ListView) findViewById((R.id.listviewSports));

        arrSport = new ArrayList<>();
        arrSport.add(new Food("Chạy bộ", "", R.drawable.jogging, 0));
        arrSport.add(new Food("Bài tập thể dục", "", R.drawable.triangle, 0));
        arrSport.add(new Food("Bóng đá", "", R.drawable.football, 0));
        arrSport.add(new Food("Bóng bàn", "", R.drawable.ping_pong, 0));
        arrSport.add(new Food("Bóng rỗ", "", R.drawable.basketball, 0));
        arrSport.add(new Food("Bóng chày", "", R.drawable.baseball, 0));
        arrSport.add(new Food("Quần vợt", "", R.drawable.tennis_racket, 0));
        arrSport.add(new Food("Cầu lông", "", R.drawable.badminton, 0));
        arrSport.add(new Food("Chạy xe đạp", "", R.drawable.racing, 0));
        arrSport.add(new Food("Leo núi", "", R.drawable.hiking, 0));
        arrSport.add(new Food("Cử tạ", "", R.drawable.fitness, 0));
        arrSport.add(new Food("Võ Vovinam", "", R.drawable.vovinam, 0));
        arrSport.add(new Food("Chèo thuyền", "", R.drawable.rowing, 0));
        arrSport.add(new Food("Lướt sóng", "", R.drawable.surfing, 0));
        arrSport.add(new Food("Lặn", "", R.drawable.snorkle, 0));
        arrSport.add(new Food("Yoga", "", R.drawable.yoga, 0));

        adapter = new FoodAdapter(this, R.layout.food_line, arrSport);
        lvSport.setAdapter(adapter);
    }

    public void gotoMainMenu(View view)
    {
        startActivity(new Intent(SportActivity.this, HoatDong.class));
    }
}
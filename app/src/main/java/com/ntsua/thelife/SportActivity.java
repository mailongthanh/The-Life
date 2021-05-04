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
        arrSport.add(new Food("Chạy bộ", "", R.drawable.jogging));
        arrSport.add(new Food("Bài tập thể dục", "", R.drawable.triangle));
        arrSport.add(new Food("Bóng đá", "", R.drawable.football));
        arrSport.add(new Food("Bóng bàn", "", R.drawable.ping_pong));
        arrSport.add(new Food("Bóng rỗ", "", R.drawable.basketball));
        arrSport.add(new Food("Bóng chày", "", R.drawable.baseball));
        arrSport.add(new Food("Quần vợt", "", R.drawable.tennis_racket));
        arrSport.add(new Food("Cầu lông", "", R.drawable.badminton));
        arrSport.add(new Food("Chạy xe đạp", "", R.drawable.racing));
        arrSport.add(new Food("Leo núi", "", R.drawable.hiking));
        arrSport.add(new Food("Cử tạ", "", R.drawable.fitness));
        arrSport.add(new Food("Võ Vovinam", "", R.drawable.vovinam));
        arrSport.add(new Food("Chèo thuyền", "", R.drawable.rowing));
        arrSport.add(new Food("Lướt sóng", "", R.drawable.surfing));
        arrSport.add(new Food("Lặn", "", R.drawable.snorkle));
        arrSport.add(new Food("Yoga", "", R.drawable.yoga));

        adapter = new FoodAdapter(this, R.layout.food_line, arrSport);
        lvSport.setAdapter(adapter);
    }

    public void gotoMainMenu(View view)
    {
        startActivity(new Intent(SportActivity.this, MainActivity.class));
    }
}
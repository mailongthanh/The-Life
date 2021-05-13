package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MakeUp extends AppCompatActivity {
    ListView lvMakeUp;
    FoodAdapter adapter;
    ArrayList<Food> arrMakeUp;
    TextView txtName, txtJob, txtMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_up);

        txtName = findViewById(R.id.textviewName);
        txtJob = findViewById(R.id.textviewJob);
        txtMoney = findViewById(R.id.textviewMoney);
        loadGame();

        lvMakeUp = (ListView) findViewById((R.id.listviewMakeUp));
        arrMakeUp = new ArrayList<>();
        arrMakeUp.add(new Food("Xăm mình", "Xăm đi ngại chi", R.drawable.tattoo, 1000));
        arrMakeUp.add(new Food("Trị mụn", "1 triệu 500 nghìn - Cho làn da trắng mịn", R.drawable.acnet, 1500));
        arrMakeUp.add(new Food("Skin care", "500 nghìn - Làm sạch các vết nhăn", R.drawable.skincare, 500));
        arrMakeUp.add(new Food("Massage", "800 nghìn - Massage cao cấp", R.drawable.massage, 800));
        arrMakeUp.add(new Food("Xông hơi", "600 nghìn - Xông hơi thư giãn", R.drawable.steam, 600));
        adapter = new FoodAdapter(this, R.layout.food_line, arrMakeUp);
        lvMakeUp.setAdapter(adapter);
    }

    private void loadGame() {
        txtName.setText(MainActivity.saveGame.getName());
        txtMoney.setText(MainActivity.saveGame.getMoney() + "VND");
        txtJob.setText(MainActivity.saveGame.getJob());
    }

    public void gotoMainMenu(View view)
    {
        startActivity(new Intent(MakeUp.this, HoatDong.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }
}
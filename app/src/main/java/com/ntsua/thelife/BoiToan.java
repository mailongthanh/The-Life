package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class BoiToan extends AppCompatActivity {
    ListView lvBoi;
    FoodAdapter adapter;
    ArrayList<Food> arrBoi;
    TextView txtName, txtJob, txtMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boi_toan);

        txtName = findViewById(R.id.textviewName);
        txtJob = findViewById(R.id.textviewJob);
        txtMoney = findViewById(R.id.textviewMoney);
        loadGame();

        lvBoi = (ListView) findViewById((R.id.listviewBoi));
        arrBoi = new ArrayList<>();
        arrBoi.add(new Food("Bói tử vi", "500 nghìn - Cuộc sống nở hoa hay bế tắc", R.drawable.tuvi, 500));
        arrBoi.add(new Food("Bói công danh sự nghiệp", "1 triệu - Sự nghiệp lên như diều gặp gió hay lận đận?", R.drawable.career, 1000));
        arrBoi.add(new Food("Bói tình duyên", "500 nghìn - Theo tình, tình chạy, đuổi tình, tình theo", R.drawable.handlove, 500));
        adapter = new FoodAdapter(this, R.layout.food_line, arrBoi);
        lvBoi.setAdapter(adapter);
    }

    private void loadGame() {
        txtName.setText(MainActivity.saveGame.getName());
        txtMoney.setText(MainActivity.saveGame.getMoney() + "VND");
        txtJob.setText(MainActivity.saveGame.getJob());
    }

    public void gotoMainMenu(View view)
    {
        startActivity(new Intent(BoiToan.this, HoatDong.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }
}
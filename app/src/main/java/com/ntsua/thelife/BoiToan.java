package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class BoiToan extends AppCompatActivity {
    ListView lvBoi;
    FoodAdapter adapter;
    ArrayList<Food> arrBoi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boi_toan);
        lvBoi = (ListView) findViewById((R.id.listviewBoi));
        arrBoi = new ArrayList<>();
        arrBoi.add(new Food("Bói tử vi", "500 nghìn - Cuộc sống nở hoa hay bế tắc", R.drawable.tuvi));
        arrBoi.add(new Food("Bói công danh sự nghiệp", "1 triệu - Sự nghiệp lên như diều gặp gió hay lận đận?", R.drawable.career));
        arrBoi.add(new Food("Bói tình duyên", "500 nghìn - Theo tình, tình chạy, đuổi tình, tình theo", R.drawable.handlove));
        adapter = new FoodAdapter(this, R.layout.food_line, arrBoi);
        lvBoi.setAdapter(adapter);
    }

    public void gotoMainMenu(View view)
    {
        startActivity(new Intent(BoiToan.this, HoatDong.class));
    }
}
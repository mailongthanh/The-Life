package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class Startup extends AppCompatActivity {
    ListView lvStartUp;
    FoodAdapter adapter;
    ArrayList<Food> arrStartUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        lvStartUp = (ListView) findViewById((R.id.listviewStartUp));
        arrStartUp = new ArrayList<>();
        arrStartUp.add(new Food("Quân tử lập nghiệp", "Now or never", R.drawable.hero, 0));
        arrStartUp.add(new Food("Bán máu", "Hãy cho đi mà không cần nhận lại", R.drawable.blooddonate, 0));
        arrStartUp.add(new Food("Vé số", "Xổ số kiến thiết mang niềm vui đến mọi nhà", R.drawable.lottery, 0));
        arrStartUp.add(new Food("Đánh đề", "Sau 5h chiều không biết ai giàu hơn ai", R.drawable.dice, 0));
        adapter = new FoodAdapter(this, R.layout.food_line, arrStartUp);
        lvStartUp.setAdapter(adapter);
    }

    public void gotoMainMenu(View view)
    {
        startActivity(new Intent(Startup.this, HoatDong.class));
    }
}
package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class HoatDong extends AppCompatActivity {

    ListView lvHoatDong;
    FoodAdapter adapter;
    ArrayList<Food> arrHoatDong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoat_dong);
        lvHoatDong = (ListView) findViewById((R.id.listviewHoatDong));

        arrHoatDong = new ArrayList<>();
        arrHoatDong.add(new Food("Khu vui chơi", "", R.drawable.playground));
        arrHoatDong.add(new Food("Tình yêu", "", R.drawable.love));
        arrHoatDong.add(new Food("Bệnh viện", "", R.drawable.hospital));
        arrHoatDong.add(new Food("Khởi nghiệp", "", R.drawable.startup));
        arrHoatDong.add(new Food("Nấu ăn", "", R.drawable.cook));
        arrHoatDong.add(new Food("Sống khác", "", R.drawable.devil));
        arrHoatDong.add(new Food("Làm đẹp", "", R.drawable.makeup));
        arrHoatDong.add(new Food("Bằng cấp", "", R.drawable.degree));
        arrHoatDong.add(new Food("Ăn uống", "", R.drawable.cutlery));
        arrHoatDong.add(new Food("Du lịch", "", R.drawable.travel));
        arrHoatDong.add(new Food("Xem bói", "", R.drawable.fortunetelling));
        arrHoatDong.add(new Food("Giải trí", "", R.drawable.entertainment));
        arrHoatDong.add(new Food("Thể thao", "", R.drawable.sport));
        adapter = new FoodAdapter(this, R.layout.food_line, arrHoatDong);
        lvHoatDong.setAdapter(adapter);
    }

    public void gotoMainMenu(View view)
    {
        startActivity(new Intent(HoatDong.this, MainActivity.class));
    }
}

package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
        lvHoatDong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent myintent = new Intent(view.getContext(), Playground.class);
                    startActivityForResult(myintent, 0);
                }
                if (position == 1) {
                    Intent myintent = new Intent(view.getContext(), Love.class);
                    startActivityForResult(myintent, 1);
                }
                if (position == 2) {
                    Intent myintent = new Intent(view.getContext(), Hospital.class);
                    startActivityForResult(myintent, 2);
                }
                if (position == 3) {
                    Intent myintent = new Intent(view.getContext(), Startup.class);
                    startActivityForResult(myintent, 3);
                }
                if (position == 4) {
                    Intent myintent = new Intent(view.getContext(), Cooking.class);
                    startActivityForResult(myintent, 4);
                }
                if (position == 5) {
                    Intent myintent = new Intent(view.getContext(), SongKhac.class);
                    startActivityForResult(myintent, 5);
                }
                if (position == 6) {
                    Intent myintent = new Intent(view.getContext(), MakeUp.class);
                    startActivityForResult(myintent, 6);
                }
                if (position == 7) {
                    Intent myintent = new Intent(view.getContext(), Degree.class);
                    startActivityForResult(myintent, 7);
                }
                if (position == 8) {
                    Intent myintent = new Intent(view.getContext(), FoodActivity.class);
                    startActivityForResult(myintent, 8);
                }
                if (position == 9) {
                    Intent myintent = new Intent(view.getContext(), Travel.class);
                    startActivityForResult(myintent, 9);
                }
                if (position == 10) {
                    Intent myintent = new Intent(view.getContext(), BoiToan.class);
                    startActivityForResult(myintent, 10);
                }
                if (position == 11) {
                    Intent myintent = new Intent(view.getContext(), Entertainment.class);
                    startActivityForResult(myintent, 11);
                }
                if (position == 12) {
                    Intent myintent = new Intent(view.getContext(), SportActivity.class);
                    startActivityForResult(myintent, 12);
                }
            }
        });
        }
    public void gotoMainMenu(View view) {
        startActivity(new Intent(HoatDong.this, MainActivity.class));
    }
    }


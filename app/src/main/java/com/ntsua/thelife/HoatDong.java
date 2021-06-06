package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HoatDong extends AppCompatActivity {

    ListView lvHoatDong;
    FoodAdapter adapter;
    ArrayList<Food> arrHoatDong;
    TextView txtName, txtJob, txtMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoat_dong);


        txtName = findViewById(R.id.textviewName);
        txtJob = findViewById(R.id.textviewJob);
        txtMoney = findViewById(R.id.textviewMoney);
        lvHoatDong = (ListView) findViewById((R.id.listviewHoatDong));

        loadGame();
        arrHoatDong = new ArrayList<>();
        arrHoatDong.add(new Food("Khu vui chơi", "", R.drawable.playground, 0));
        arrHoatDong.add(new Food("Tình yêu", "", R.drawable.love, 0));
        arrHoatDong.add(new Food("Bệnh viện", "", R.drawable.hospital, 0));
        arrHoatDong.add(new Food("Khởi nghiệp", "", R.drawable.startup, 0));
        arrHoatDong.add(new Food("Nấu ăn", "", R.drawable.cook, 0));
        arrHoatDong.add(new Food("Sống khác", "", R.drawable.devil, 0));
        arrHoatDong.add(new Food("Làm đẹp", "", R.drawable.makeup, 0));
        arrHoatDong.add(new Food("Bằng cấp", "", R.drawable.degree, 0));
        arrHoatDong.add(new Food("Ăn uống", "", R.drawable.cutlery, 0));
        arrHoatDong.add(new Food("Du lịch", "", R.drawable.travel, 0));
        arrHoatDong.add(new Food("Xem bói", "", R.drawable.clairvoyance, 0));
        arrHoatDong.add(new Food("Giải trí", "", R.drawable.entertainment, 0));
        arrHoatDong.add(new Food("Thể thao", "", R.drawable.sport, 0));
        arrHoatDong.add(new Food("Mua sắm","",R.drawable.shopping,0));
        arrHoatDong.add(new Food("Xin việc","",R.drawable.jobsearch,0));
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
                    Intent myintent = new Intent(view.getContext(), CrimeActivity.class);
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
                if(position == 13){
                    Intent myintent = new Intent(view.getContext(), Shopping.class);
                    startActivityForResult(myintent,13);
                }
                if(position == 14){
                    Intent myintent = new Intent(view.getContext(), ChuyenViec.class);
                    startActivityForResult(myintent,14);
                }
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        }
    private void loadGame() {
        txtName.setText(MainActivity.saveGame.getName());
        txtMoney.setText(MainActivity.saveGame.getMoney() + " VND");
        txtJob.setText(MainActivity.saveGame.getJob());
    }
    public void gotoMainMenu(View view) {
        startActivity(new Intent(HoatDong.this, MainActivity.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
        onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadGame();
    }
}


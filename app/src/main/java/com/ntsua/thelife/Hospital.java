package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Hospital extends AppCompatActivity {
    ListView lvHospital;
    FoodAdapter adapter;
    ArrayList<Food> arrHospital;
    TextView txtName, txtJob, txtMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        txtName = findViewById(R.id.textviewName);
        txtJob = findViewById(R.id.textviewJob);
        txtMoney = findViewById(R.id.textviewMoney);
        loadGame();

        lvHospital = (ListView) findViewById((R.id.listviewHospital));
        arrHospital = new ArrayList<>();
        arrHospital.add(new Food("Nha sĩ", "5 triệu", R.drawable.dentist, 5000));
        arrHospital.add(new Food("Bác sĩ mắt", "2 triệu - 15 triệu", R.drawable.eyesdoctor, 2));
        arrHospital.add(new Food("Bác sĩ tai mũi họng", "2 triệu - 5 triệu", R.drawable.doctor, 2));
        arrHospital.add(new Food("Bác sĩ da liễu", "500 nghìn - 2 triệu", R.drawable.dalieu, 2));
        arrHospital.add(new Food("Bác sĩ tâm lý", "12 triệu", R.drawable.psychiatrist, 12));
        adapter = new FoodAdapter(this, R.layout.food_line, arrHospital);
        lvHospital.setAdapter(adapter);
    }

    private void loadGame() {
        txtName.setText(MainActivity.saveGame.getName());
        txtMoney.setText(MainActivity.saveGame.getMoney() + "VND");
        txtJob.setText(MainActivity.saveGame.getJob());
    }

    public void gotoMainMenu(View view)
    {
        startActivity(new Intent(Hospital.this, HoatDong.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }
}
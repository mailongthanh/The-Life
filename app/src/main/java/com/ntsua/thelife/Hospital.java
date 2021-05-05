package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class Hospital extends AppCompatActivity {
    ListView lvHospital;
    FoodAdapter adapter;
    ArrayList<Food> arrHospital;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);
        lvHospital = (ListView) findViewById((R.id.listviewHospital));
        arrHospital = new ArrayList<>();
        arrHospital.add(new Food("Nha sĩ", "5 triệu", R.drawable.dentist));
        arrHospital.add(new Food("Bác sĩ mắt", "2 triệu - 15 triệu", R.drawable.eyesdoctor));
        arrHospital.add(new Food("Bác sĩ tai mũi họng", "2 triệu - 5 triệu", R.drawable.doctor));
        arrHospital.add(new Food("Bác sĩ da liễu", "500 nghìn - 2 triệu", R.drawable.dalieu));
        arrHospital.add(new Food("Bác sĩ tâm lý", "12 triệu", R.drawable.psychiatrist));
        adapter = new FoodAdapter(this, R.layout.food_line, arrHospital);
        lvHospital.setAdapter(adapter);
    }

    public void gotoMainMenu(View view)
    {
        startActivity(new Intent(Hospital.this, HoatDong.class));
    }
}
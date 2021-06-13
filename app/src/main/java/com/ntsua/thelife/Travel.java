package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Travel extends AppCompatActivity {
    ListView lvTravel;
    FoodAdapter adapter;
    ArrayList<Food> arrTravel;
    TextView txtName, txtJob, txtMoney;
    ImageView imgAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);

        txtName = findViewById(R.id.textviewName);
        txtJob = findViewById(R.id.textviewJob);
        txtMoney = findViewById(R.id.textviewMoney);
        imgAvatar = findViewById(R.id.imageAvatar);
        loadGame();

        lvTravel = (ListView) findViewById((R.id.listviewTravel));
        arrTravel = new ArrayList<>();
        arrTravel.add(new Food("Hong Kong", "", R.drawable.hongkong, 0));
        arrTravel.add(new Food("America", "", R.drawable.america, 0));
        arrTravel.add(new Food("Korea", "", R.drawable.korea, 0));
        arrTravel.add(new Food("ThaiLand", "", R.drawable.thailand, 0));
        arrTravel.add(new Food("Vũng Tàu", "", R.drawable.vungtau, 0));
        arrTravel.add(new Food("Phú Quốc", "", R.drawable.phuquoc, 0));
        arrTravel.add(new Food("Đà Nẵng", "", R.drawable.danang, 0));
        adapter = new FoodAdapter(this, R.layout.food_line, arrTravel);
        lvTravel.setAdapter(adapter);
    }

    private void loadGame() {
        txtName.setText(MainActivity.saveGame.getName());
        txtMoney.setText(MainActivity.saveGame.getMoney() + "VND");
        txtJob.setText(MainActivity.saveGame.getJob());
        imgAvatar = findViewById(R.id.imageAvatar);
    }

    public void gotoMainMenu(View view)
    {
        //startActivity(new Intent(Travel.this, HoatDong.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
        onBackPressed();
    }
}
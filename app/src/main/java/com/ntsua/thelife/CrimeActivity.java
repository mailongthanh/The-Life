package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CrimeActivity extends AppCompatActivity {

    ListView lvCrime;
    CrimeAdapter adapter;
    ArrayList<Food> arrCrime;
    TextView txtName, txtJob, txtMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

        txtName = findViewById(R.id.textviewName);
        txtJob = findViewById(R.id.textviewJob);
        txtMoney = findViewById(R.id.textviewMoney);
        loadGame();

        lvCrime = findViewById(R.id.listviewCrime);
        arrCrime = new ArrayList<>();

        arrCrime.add(new Food("Móc túi", "đi Loot", R.drawable.pickpocket, 0));
        arrCrime.add(new Food("Cướp ngân hàng", "$$$$$$$$", R.drawable.robber, 0));
        arrCrime.add(new Food("Buôn rau", "kinh doanh rau muống đột biến", R.drawable.cannabis, 0));
        arrCrime.add(new Food("Cướp xe", "Grand Theft Auto", R.drawable.carrobber, 0));
        arrCrime.add(new Food("Hack", "Wảning", R.drawable.hacker, 0));
        arrCrime.add(new Food("Trộm chó", "Cẩu tặc", R.drawable.dogrobber, 0));

        adapter = new CrimeAdapter(this, R.layout.crime, arrCrime);
        lvCrime.setAdapter(adapter);

        lvCrime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void loadGame() {
        txtName.setText(MainActivity.saveGame.getName());
        txtMoney.setText(MainActivity.saveGame.getMoney() + "VND");
        txtJob.setText(MainActivity.saveGame.getJob());
    }

    public void gotoMainMenu(View view)
    {
        //startActivity(new Intent(CrimeActivity.this, HoatDong.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
        onBackPressed();
    }
}
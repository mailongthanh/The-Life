package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Asset extends AppCompatActivity {

    ListView lvAsset;
    ArrayList<Food> arrAsset;
    productAdapter adapter;
    TextView txtMoney, txtName, txtJob;
    ImageView imgAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset);

        txtMoney = findViewById(R.id.textviewMoney);
        txtJob = findViewById(R.id.textviewJob);
        txtName = findViewById(R.id.textviewName);
        imgAvatar = findViewById(R.id.imageAvatar);
        loadGame();

        TextView tvThongbao = findViewById(R.id.tvthongbao);
        lvAsset = findViewById(R.id.listviewAsset);

        arrAsset = new ArrayList<>();
        arrAsset = MainActivity.saveGame.getAsset();
        if(arrAsset != null)
        {
            tvThongbao.setText("");
            adapter = new productAdapter(this,R.layout.productline, arrAsset);
            lvAsset.setAdapter(adapter);
        }
  }

    private void loadGame() {
        txtName.setText(MainActivity.saveGame.getName());
        txtMoney.setText(MainActivity.saveGame.getMoney() + "VND");
        txtJob.setText(MainActivity.saveGame.getJob());
        imgAvatar.setImageResource(MainActivity.saveGame.getAvatar());
    }

    public void gotoMainMenu(View view)
    {
        startActivity(new Intent(Asset.this, MainActivity.class));
    }
}
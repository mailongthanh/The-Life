package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
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
        lvAsset.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CreateDialog();
            }
        });
  }

    private void CreateDialog()
    {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_death);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogResultAnimation;
        dialog.show();
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
package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class ChuyenViec extends AppCompatActivity {
    ListView lvViec;
    FoodAdapter adapter;
    ArrayList<Food> arrViec;
    TextView txtName, txtJob, txtMoney;
    ImageView imgAvatar;
    String tienAn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuyen_viec);
        txtName = findViewById(R.id.textviewName);
        txtJob = findViewById(R.id.textviewJob);
        txtMoney = findViewById(R.id.textviewMoney);
        imgAvatar = findViewById(R.id.imageAvatar);

        loadGame();

        lvViec = (ListView) findViewById((R.id.listviewViec));
        arrViec = new ArrayList<>();
        arrViec.add(new Food("Ca sĩ", "", R.drawable.singer, 0));
        arrViec.add(new Food("Cầu thủ", "", R.drawable.footballplayer, 0));
        arrViec.add(new Food("Tài xế", "", R.drawable.car, 0));
        arrViec.add(new Food("Báo chí", "", R.drawable.newspaper, 0));
        arrViec.add(new Food("Quân đội", "", R.drawable.army, 0));
        arrViec.add(new Food("Kinh doanh", "", R.drawable.business, 0));
        arrViec.add(new Food("Ẩm thực", "", R.drawable.chef, 0));
        arrViec.add(new Food("Dịch vụ", "", R.drawable.service, 0));
        arrViec.add(new Food("Công nghệ", "", R.drawable.it, 0));
        arrViec.add(new Food("Tài chính", "", R.drawable.finance, 0));
        arrViec.add(new Food("Diễn viên", "", R.drawable.actor, 0));
        arrViec.add(new Food("Y tế", "", R.drawable.medical, 0));
        arrViec.add(new Food("Giáo dục", "", R.drawable.education, 0));
        adapter = new FoodAdapter(this, R.layout.food_line, arrViec);
        lvViec.setAdapter(adapter);
        tienAn = MainActivity.saveGame.getTienAn();
        Toast.makeText(this, tienAn, Toast.LENGTH_SHORT).show();

        lvViec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent myintent = new Intent(view.getContext(), JobCaSi.class);
                    startActivityForResult(myintent, 0);
                }
                if (position == 1) {
                    Intent myintent = new Intent(view.getContext(), JobCauThu.class);
                    startActivityForResult(myintent, 1);
                }
                if (position == 2) {
                    Intent myintent = new Intent(view.getContext(), JobTaiXe.class);
                    startActivityForResult(myintent, 2);
                }
                if (position == 3) {
                    Intent myintent = new Intent(view.getContext(), JobBaoChi.class);
                    startActivityForResult(myintent, 3);
                }
                if (position == 4 && tienAn == "") {
                    Intent myintent = new Intent(view.getContext(), JobQuanDoi.class);
                    startActivityForResult(myintent, 4);
                } else if( position == 4)
                {
                    MainActivity.createNotification(R.drawable.cancel,
                            "Do bạn có tiền án "+ tienAn +" nên bạn không thể gia nhập môi trường quân đội",
                            ChuyenViec.this);}
                if (position == 5) {
                    Intent myintent = new Intent(view.getContext(), JobKinhDoanh.class);
                    startActivityForResult(myintent, 5);
                }
                if (position == 6) {
                    Intent myintent = new Intent(view.getContext(), JobAmThuc.class);
                    startActivityForResult(myintent, 6);
                }
                if (position == 7) {
                    Intent myintent = new Intent(view.getContext(), JobDichVu.class);
                    startActivityForResult(myintent, 7);
                }
                if (position == 8) {
                    Intent myintent = new Intent(view.getContext(), JobCongNghe.class);
                    startActivityForResult(myintent, 8);
                }
                if (position == 9) {
                    Intent myintent = new Intent(view.getContext(), JobTaiChinh.class);
                    startActivityForResult(myintent, 9);
                }
                if (position == 10) {
                    Intent myintent = new Intent(view.getContext(),JobDienVien.class);
                    startActivityForResult(myintent, 10);
                }
                if (position == 11) {
                    Intent myintent = new Intent(view.getContext(), JobYTe.class);
                    startActivityForResult(myintent, 11);
                }
                if (position == 12) {
                    Intent myintent = new Intent(view.getContext(), JobGiaoDuc.class);
                    startActivityForResult(myintent, 12);
                }
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
    }
    private void loadGame() {
        txtName.setText(MainActivity.saveGame.getName());
        txtMoney.setText(MainActivity.saveGame.getMoney() + "K VND");
        txtJob.setText(MainActivity.saveGame.getJob());
        imgAvatar.setImageResource(MainActivity.saveGame.getAvatar());
    }
    public void gotoMainMenu(View view)
    {
        //startActivity(new Intent(Degree.this, HoatDong.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
        onBackPressed();
    }
}
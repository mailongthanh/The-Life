package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Tuong_tacActivity extends AppCompatActivity {

    ListView lvTuongTac;
    ArrayList<HoatDongQH> MangTuongTac;
    TextView hoten, tuoi, quanhe;
    ImageView hinhanh;
    ProgressBar pbqhanhe;
    int dothanmat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuong_tac);

        hoten = findViewById(R.id.textviewNameA);
        tuoi = findViewById(R.id.tvTuoi);
        quanhe = findViewById(R.id.TvQuanHe);
        hinhanh = findViewById(R.id.imageAvatarA);
        pbqhanhe = findViewById(R.id.pbThanhQuanHeA);

        //Nhận dữ liệu
        Intent intent=getIntent();
        Bundle bundle = intent.getBundleExtra("BUN");

        //Ánh xạ quan hệ
        hoten.setText(bundle.getString("hoten"));
        tuoi.setText("( " + bundle.getString("tuoi") + " Tuổi )");
        quanhe.setText(bundle.getString("quanhe").toUpperCase());
        hinhanh.setImageResource(bundle.getInt("hinhanh"));
        pbqhanhe.setProgress(bundle.getInt("dothanmat"));

        //Ánh xạ listview
        lvTuongTac = (ListView) findViewById(R.id.ListViewTuongTac);
        MangTuongTac = new ArrayList<HoatDongQH>();

        MangTuongTac.add(new HoatDongQH(R.drawable.money, "Xin tiền", "Không làm mà vẫn có ăn"));
        MangTuongTac.add(new HoatDongQH(R.drawable.assualt, "Hành hung", "Một chuỳ vào đầu"));
        MangTuongTac.add(new HoatDongQH(R.drawable.compliment, "Khen ngợi", "Thảo mai khen ngợi"));
        MangTuongTac.add(new HoatDongQH(R.drawable.communication, "Đàm đạo", "Đàm đạo chuyện thế gian"));
        MangTuongTac.add(new HoatDongQH(R.drawable.insult, "Xúc phạm", "Giết người bằng lời nói"));
        MangTuongTac.add(new HoatDongQH(R.drawable.film, "Rủ xem phim", "Phimcuzzzzz.net"));

        HoatDongQHAdapter adapter = new HoatDongQHAdapter(
                Tuong_tacActivity.this,
                R.layout.tuong_tac,
                MangTuongTac
        );

        lvTuongTac.setAdapter(adapter);
    }


        public void gotoRelationship(View view)
        {
            startActivity(new Intent(Tuong_tacActivity.this, RelationShip.class));
        };

}

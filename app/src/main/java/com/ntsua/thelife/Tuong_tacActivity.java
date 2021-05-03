package com.ntsua.thelife;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class Tuong_tacActivity extends AppCompatActivity {

    ListView lvTuongTac;
    ArrayList<HoatDongQH> MangTuongTac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuong_tac);

        lvTuongTac = (ListView) findViewById(R.id.ListViewTuongTac);
        MangTuongTac = new ArrayList<HoatDongQH>();

        MangTuongTac.add(new HoatDongQH(R.drawable.money,"Xin Tiền","Không làm mà vẫn có ăn"));
        MangTuongTac.add(new HoatDongQH(R.drawable.assualt,"Hành hung","Một chuỳ vào đầu"));
        MangTuongTac.add(new HoatDongQH(R.drawable.compliment,"Khen ngợi","Thảo mai khen ngợi"));
        MangTuongTac.add(new HoatDongQH(R.drawable.communication,"Đàm đạo","Đàm đạo chuyện thế gian"));
        MangTuongTac.add(new HoatDongQH(R.drawable.insult,"Xúc phạm","Giết người bằng lời nói"));
        MangTuongTac.add(new HoatDongQH(R.drawable.film,"Rủ xem phim","Phimcuzzzzz.net"));

        HoatDongQHAdater adapter = new HoatDongQHAdater(
                Tuong_tacActivity.this,
                R.layout.tuong_tac,
                MangTuongTac
        );

        lvTuongTac.setAdapter(adapter);

    }
}
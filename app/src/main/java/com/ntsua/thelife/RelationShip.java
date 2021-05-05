package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RelationShip extends AppCompatActivity {

    ListView lvQuanHe;
    ArrayList<QuanHe> MangQuanHe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relationship);

        lvQuanHe = (ListView) findViewById(R.id.ListViewQuanHe);
        MangQuanHe = new ArrayList<QuanHe>();

        MangQuanHe.add(new QuanHe("Trần Thanh Vũ", 19, 50, "Bạn bè"));
        MangQuanHe.add(new QuanHe("Nguyễn Thiện Sua", 19, 2, "Bạn bè"));
        MangQuanHe.add(new QuanHe("Nguyễn Hiếu Nghĩa", 19, 50, "Bạn bè"));
        MangQuanHe.add(new QuanHe("Mai Long Thành", 19, 80, "Bạn bè"));
        MangQuanHe.add(new QuanHe("Võ Thành Phát", 19, 2, "Bạn bè"));
        MangQuanHe.add(new QuanHe("Hoàng Nhật Tiến", 19, 0, "Bạn bè"));

        QuanHeAdapter adapter = new QuanHeAdapter(
                RelationShip.this,
                R.layout.quan_he,
                MangQuanHe
        );

        lvQuanHe.setAdapter(adapter);

        lvQuanHe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(RelationShip.this, Tuong_tacActivity.class));
            }
        });

    }

    public void gotoMainMenu(View view) {
        startActivity(new Intent(RelationShip.this, MainActivity.class));
    }
}

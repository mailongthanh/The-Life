package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class RelationShip extends AppCompatActivity {

    ListView lvQuanHe;
    ArrayList<QuanHe> MangQuanHe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvQuanHe = (ListView) findViewById(R.id.ListViewQuanHe);
        MangQuanHe = new ArrayList<QuanHe>();

        MangQuanHe.add(new QuanHe("Trần Thanh Vũ",19,50,"Bạn bè"));
        MangQuanHe.add(new QuanHe("Nguyễn Thiện Sua",19,2,"Bạn bè"));
        MangQuanHe.add(new QuanHe("Nguyễn Hiếu Nghĩa",19,50,"Bạn bè"));

        QuanHeAdapter adapter = new QuanHeAdapter(
                RelationShip.this,
                R.layout.quan_he,
                MangQuanHe
        );

        lvQuanHe.setAdapter(adapter);

    }
}

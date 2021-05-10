package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RelationShip extends AppCompatActivity {

    ListView lvQuanHe;
    ArrayList<QuanHe> MangQuanHe;
    TextView txtName, txtJob, txtMoney;

    SaveGame saveGame;
    SharedPreferences preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relationship);

        preference = getSharedPreferences("data", MODE_PRIVATE);
        saveGame = new SaveGame(preference);
        txtName = findViewById(R.id.textviewName);
        txtJob = findViewById(R.id.textviewJob);
        txtMoney = findViewById(R.id.textviewMoney);
        lvQuanHe = (ListView) findViewById(R.id.ListViewQuanHe);
        MangQuanHe = new ArrayList<QuanHe>();


        loadGame();
//        MangQuanHe.add(new QuanHe("Trần Thanh Vũ", 19, 50, "Bạn bè",R.drawable.boy));
//        MangQuanHe.add(new QuanHe("Nguyễn Thiện Sua", 19, 2, "Bạn bè",R.drawable.boy));
//        MangQuanHe.add(new QuanHe("Nguyễn Hiếu Nghĩa", 19, 50, "Bạn bè",R.drawable.boy));
//        MangQuanHe.add(new QuanHe("Mai Long Thành", 19, 80, "Bạn bè",R.drawable.boy));
//        MangQuanHe.add(new QuanHe("Võ Thành Phát", 19, 2, "Bạn bè",R.drawable.boy));
//        MangQuanHe.add(new QuanHe("Hoàng Nhật Tiến", 19, 0, "Bạn bè",R.drawable.boy));

        //saveGame.saveRelationship(MangQuanHe);

        QuanHeAdapter adapter = new QuanHeAdapter(
                RelationShip.this,
                R.layout.quan_he,
                MangQuanHe
        );

        lvQuanHe.setAdapter(adapter);

        lvQuanHe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gotoTuongTac(position);
            }
        });

    }

    private void loadGame() {
        MangQuanHe = saveGame.getRelationship();
        txtName.setText(saveGame.getName());
        txtMoney.setText("$" + saveGame.getMoney());
        txtJob.setText(saveGame.getJob());
    }

    public void gotoMainMenu(View view) {
        startActivity(new Intent(RelationShip.this, MainActivity.class));
    }

    public void gotoTuongTac(int i){
        Intent intent = new Intent(RelationShip.this, Tuong_tacActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("hoten",MangQuanHe.get(i).getHoten());
        bundle.putInt("tuoi", MangQuanHe.get(i).getTuoi());
        bundle.putString("quanhe",MangQuanHe.get(i).getQuanHe());
        bundle.putInt("dothanmat",MangQuanHe.get(i).getDoThanMat());
        bundle.putInt("hinhanh",MangQuanHe.get(i).getHinhAnh());
        bundle.putInt("position", i);

        intent.putExtra("BUN",bundle);

        startActivity(intent);
    }

}


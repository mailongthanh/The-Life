package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RelationShip extends AppCompatActivity {

    ListView lvQuanHe;
    ArrayList<QuanHe> MangQuanHe;
    TextView txtName, txtJob, txtMoney;
    ImageView imgAvatar;
    QuanHeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relationship);

        txtName = findViewById(R.id.textviewName);
        txtJob = findViewById(R.id.textviewJob);
        txtMoney = findViewById(R.id.textviewMoney);
        imgAvatar = findViewById(R.id.imageAvatar);
        lvQuanHe = (ListView) findViewById(R.id.ListViewQuanHe);
        MangQuanHe = new ArrayList<>();
//        MangQuanHe.add(new QuanHe("Trần Thanh Vũ", 19, 50, "Bạn bè",R.drawable.boy));
//        MangQuanHe.add(new QuanHe("Nguyễn Thiện Sua", 19, 2, "Bạn bè",R.drawable.boy));
//        MangQuanHe.add(new QuanHe("Nguyễn Hiếu Nghĩa", 19, 50, "Bạn bè",R.drawable.boy));
//        MangQuanHe.add(new QuanHe("Mai Long Thành", 19, 80, "Bạn bè",R.drawable.boy));
//        MangQuanHe.add(new QuanHe("Võ Thành Phát", 19, 2, "Bạn bè",R.drawable.boy));
//        MangQuanHe.add(new QuanHe("Hoàng Nhật Tiến", 19, 0, "Bạn bè",R.drawable.boy));

        //saveGame.saveRelationship(MangQuanHe);

        lvQuanHe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(MangQuanHe.get(position).getHinhAnh()!= R.drawable.death && MainActivity.saveGame.getAge() > 1) {
                    gotoTuongTac(position);
                }
            }
        });
        loadGame();
    }

    @Override
    protected void onResume() {
        loadGame();
        super.onResume();
    }

    private void loadGame() {

        MangQuanHe = MainActivity.saveGame.getRelationship();
        adapter = new QuanHeAdapter(
                RelationShip.this,
                R.layout.quan_he,
                MangQuanHe
        );

        lvQuanHe.setAdapter(adapter);
        txtName.setText(MainActivity.saveGame.getName());
        txtMoney.setText(MainActivity.saveGame.getMoney() + "K VND");
        txtJob.setText(MainActivity.saveGame.getJob());
        imgAvatar.setImageResource(MainActivity.saveGame.getAvatar());
    }

    public void gotoMainMenu(View view) {
        //startActivity(new Intent(RelationShip.this, MainActivity.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
        onBackPressed();
    }

    public void gotoTuongTac(int i){
        Intent intent = new Intent(RelationShip.this, Tuong_tacActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("hoten",MangQuanHe.get(i).getHoten());
        bundle.putInt("tuoi", MangQuanHe.get(i).getTuoi());
        bundle.putSerializable("quanhe",MangQuanHe.get(i).getQuanHe());
        bundle.putInt("dothanmat",MangQuanHe.get(i).getDoThanMat());
        bundle.putInt("hinhanh",MangQuanHe.get(i).getHinhAnh());
        bundle.putInt("position", i);
        //Toast.makeText(this, "" + i, Toast.LENGTH_SHORT).show();

        intent.putExtra("BUN",bundle);

        startActivity(intent);
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }



}

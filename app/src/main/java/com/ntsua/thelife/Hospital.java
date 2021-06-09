package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Hospital extends AppCompatActivity {
    ListView lvHospital;
    FoodAdapter adapter;
    ArrayList<Food> arrHospital;
    TextView txtName, txtJob, txtMoney;
    Sick[] arrSick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        txtName = findViewById(R.id.textviewName);
        txtJob = findViewById(R.id.textviewJob);
        txtMoney = findViewById(R.id.textviewMoney);
        loadGame();

        lvHospital = (ListView) findViewById((R.id.listviewHospital));
        arrHospital = new ArrayList<>();
        arrHospital.add(new Food("Nha sĩ", "5 triệu", R.drawable.dentist, 5000));
        arrHospital.add(new Food("Bác sĩ mắt", "2 triệu", R.drawable.eyesdoctor, 2000));
        arrHospital.add(new Food("Bác sĩ tai mũi họng", "2 triệu", R.drawable.doctor, 2000));
        arrHospital.add(new Food("Bác sĩ da liễu", "500 nghìn", R.drawable.dalieu, 500));
        arrHospital.add(new Food("Bác sĩ tâm lý", "3 triệu", R.drawable.psychiatrist, 3000));
        adapter = new FoodAdapter(this, R.layout.food_line, arrHospital);
        lvHospital.setAdapter(adapter);

        lvHospital.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 4)
                {
                    if (MainActivity.saveGame.getHappy() > 30)
                        MainActivity.createNotification(R.drawable.cancel,
                                "Bạn không có vấn đề gì về tâm lý",
                                Hospital.this);
                    return;
                }
                else if (!arrSick[position].isSick())
                {
                    MainActivity.createNotification(R.drawable.cancel,
                            "Bạn không bị " + arrSick[position].getSickName() + ", bạn không cần đi khám đâu",
                            Hospital.this);
                    return;
                }
                else if (MainActivity.saveGame.getMoney() < arrHospital.get(position).getPrice()){
                    MainActivity.createNotification(R.drawable.money,
                            "Chả đủ tiền, bạn bị viêm màng túi rồi",
                            Hospital.this);
                    return;
                }

//                switch (arrHospital.get(position).getFoodName())
//                {
//                    case "":
//                        break;
//                    case "":
//                        break;
//                    case "":
//                        break;
//                    case "":
//                        break;
//                    case "":
//                        break;
//                }
            }
        });
    }

    private void loadGame() {
        txtName.setText(MainActivity.saveGame.getName());
        txtMoney.setText(MainActivity.saveGame.getMoney() + "VND");
        txtJob.setText(MainActivity.saveGame.getJob());
        arrSick = MainActivity.saveGame.getSick();
    }

    public void gotoMainMenu(View view)
    {
        //startActivity(new Intent(Hospital.this, HoatDong.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
        onBackPressed();
    }
}
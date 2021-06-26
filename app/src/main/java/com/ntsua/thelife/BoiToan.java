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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class BoiToan extends AppCompatActivity {
    ListView lvBoi;
    FoodAdapter adapter;
    ArrayList<Food> arrBoi;
    TextView txtName, txtJob, txtMoney;
    ImageView imgAvatar;
    JSONObject BTjs;
    Random random;
    ActivitiesEvent Activity;
    String jsonEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boi_toan);

        txtName = findViewById(R.id.textviewName);
        txtJob = findViewById(R.id.textviewJob);
        txtMoney = findViewById(R.id.textviewMoney);
        imgAvatar = findViewById(R.id.imageAvatar);
        random = new Random();
        arrBoi = new ArrayList<Food>();
        lvBoi = (ListView) findViewById((R.id.listviewBoi));
        loadGame();
        arrBoi.add(new Food("Bói tử vi", "500 nghìn - Cuộc sống nở hoa hay bế tắc", R.drawable.tuvi, 500));
        arrBoi.add(new Food("Bói công danh sự nghiệp", "1 triệu - Sự nghiệp lên như diều gặp gió hay lận đận?", R.drawable.career, 1000));
        arrBoi.add(new Food("Bói tình duyên", "500 nghìn - Theo tình, tình chạy, đuổi tình, tình theo", R.drawable.handlove, 500));
        adapter = new FoodAdapter(this, R.layout.food_line, arrBoi);
        lvBoi.setAdapter(adapter);
        jsonEvent = readEvent();
        Activity = new ActivitiesEvent(jsonEvent, BoiToan.this);
        lvBoi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (arrBoi.get(position).getFoodName() == "Bói tử vi" && MainActivity.saveGame.getMoney() >= arrBoi.get(0).getPrice()) {
                    if (MainActivity.saveGame.getTuVi() < 2) {
                        try {
                            Activity.CreateDialog("Bói tử vi", "Bói toán");
                            MainActivity.saveGame.saveTuVi(MainActivity.saveGame.getTuVi() + 1);
                            loadGame();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        MainActivity.createNotification(R.drawable.tuvi, "Sự kiện chỉ được làm 2 lần ở mỗi tuổi", BoiToan.this);
                    }
                }else if(position ==0)
                {
                    createDialogOFM();
                }
                if (arrBoi.get(position).getFoodName() == "Bói công danh sự nghiệp" && MainActivity.saveGame.getMoney() >= arrBoi.get(1).getPrice()) {
                    if (MainActivity.saveGame.getBoiSN() < 2) {
                        try {
                            Activity.CreateDialog("Bói công danh sự nghiệp", "Bói toán");
                            MainActivity.saveGame.saveBoiSN(MainActivity.saveGame.getBoiSN() + 1);
                            loadGame();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        MainActivity.createNotification(R.drawable.career, "Sự kiện chỉ được làm 2 lần ở mỗi tuổi", BoiToan.this);
                    }
                }else if(position ==1)
                {
                    createDialogOFM();
                }
                if (arrBoi.get(position).getFoodName() == "Bói tình duyên" && MainActivity.saveGame.getMoney() >= arrBoi.get(2).getPrice()) {
                    if (MainActivity.saveGame.getBoiTinh() < 2) {
                        try {
                            Activity.CreateDialog("Bói tình duyên", "Bói toán");
                            MainActivity.saveGame.saveBoiTinh(MainActivity.saveGame.getBoiTinh() + 1);
                            loadGame();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        MainActivity.createNotification(R.drawable.handlove, "Sự kiện chỉ được làm 2 lần ở mỗi tuổi", BoiToan.this);
                    }
                }else if(position ==2)
                {
                    createDialogOFM();
                }
            }
        });
    }
    String readEvent()
    {
        jsonEvent = null;
        try {
            InputStream inputStream = getAssets().open("xemboi_event.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonEvent = new String(buffer, "UTF-8");
            BTjs = new JSONObject(jsonEvent);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return jsonEvent;
    }
    void createDialogOFM() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_out_of_money);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LinearLayout dialogResult = dialog.findViewById(R.id.dialog_event_result);
        Button btnOke = dialog.findViewById(R.id.buttonDialogEventOke);
        btnOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

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
        //startActivity(new Intent(BoiToan.this, HoatDong.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
        onBackPressed();
    }
}
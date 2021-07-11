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

public class Travel extends AppCompatActivity {
    ListView lvTravel;
    FoodAdapter adapter;
    ArrayList<Food> arrTravel;
    TextView txtName, txtJob, txtMoney;
    ImageView imgAvatar;
    ActivitiesEvent Activity;
    JSONObject Traveljs;
    Random random;
    String jsonEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);

        txtName = findViewById(R.id.textviewName);
        txtJob = findViewById(R.id.textviewJob);
        txtMoney = findViewById(R.id.textviewMoney);
        imgAvatar = findViewById(R.id.imageAvatar);
        loadGame();

        lvTravel = (ListView) findViewById((R.id.listviewTravel));
        arrTravel = new ArrayList<>();
        arrTravel.add(new Food("Hong Kong", "10 triệu - 5 ngày 4 đêm", R.drawable.hongkong, 10000));
        arrTravel.add(new Food("America", "70 triệu - 8 ngày 7 đêm", R.drawable.america, 70000));
        arrTravel.add(new Food("Korea", "12 triệu - 5 ngày 4 đêm", R.drawable.korea, 12000));
        arrTravel.add(new Food("ThaiLand", "6 triệu - 5 ngày 4 đêm", R.drawable.thailand, 6000));
        arrTravel.add(new Food("Vũng Tàu", "500k - 2 ngày 1 đêm", R.drawable.vungtau, 500));
        arrTravel.add(new Food("Phú Quốc", "1 triệu - 3 ngày 2 đêm", R.drawable.phuquoc, 1000));
        arrTravel.add(new Food("Đà Nẵng", "2 triệu - 4 ngày 3 đêm", R.drawable.danang, 2000));
        adapter = new FoodAdapter(this, R.layout.food_line, arrTravel);
        lvTravel.setAdapter(adapter);
        jsonEvent = readEvent();
        Activity = new ActivitiesEvent(jsonEvent ,Travel.this);
        lvTravel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (arrTravel.get(position).getFoodName() == "Hong Kong"
                        && MainActivity.saveGame.getMoney() >= arrTravel.get(0).getPrice()
                        && MainActivity.saveGame.getHongKong() <3) {
                    try {
                        Activity.CreateDialog("Hong Kong","Du lịch");
                        MainActivity.saveGame.saveHongKong(MainActivity.saveGame.getHongKong() + 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position ==0 && MainActivity.saveGame.getHongKong() <3&& MainActivity.saveGame.getMoney() <= arrTravel.get(0).getPrice()){
                    createDialogOFM();
                }
                else if(position ==0 && MainActivity.saveGame.getHongKong() >=3)
                {
                    MainActivity.createNotification(R.drawable.hongkong, "Một năm bạn chỉ được đi du lịch 3 lần", Travel.this);
                }
                if (arrTravel.get(position).getFoodName() == "America"
                        && MainActivity.saveGame.getMoney() >= arrTravel.get(1).getPrice()
                        && MainActivity.saveGame.getAmerica() <3) {
                    try {
                        Activity.CreateDialog("America","Du lịch");
                        MainActivity.saveGame.saveAmerica(MainActivity.saveGame.getAmerica() + 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position ==1 && MainActivity.saveGame.getAmerica() <3&& MainActivity.saveGame.getMoney() <= arrTravel.get(1).getPrice()){
                    createDialogOFM();
                }
                else if(position ==1 && MainActivity.saveGame.getAmerica() >=3)
                {
                    MainActivity.createNotification(R.drawable.america, "Một năm bạn chỉ được đi du lịch nước này 3 lần", Travel.this);
                }
                if (arrTravel.get(position).getFoodName() == "Korea"
                        && MainActivity.saveGame.getMoney() >= arrTravel.get(2).getPrice()
                        && MainActivity.saveGame.getKorea() <3) {
                    try {
                        Activity.CreateDialog("Korea","Du lịch");
                        MainActivity.saveGame.saveKorea(MainActivity.saveGame.getKorea() + 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position ==2 && MainActivity.saveGame.getKorea() <3&& MainActivity.saveGame.getMoney() <= arrTravel.get(2).getPrice()){
                    createDialogOFM();
                }
                else if(position ==2 && MainActivity.saveGame.getKorea() >=3)
                {
                    MainActivity.createNotification(R.drawable.korea, "Một năm bạn chỉ được đi du lịch nước này 3 lần", Travel.this);
                }
                if (arrTravel.get(position).getFoodName() == "ThaiLand"
                        && MainActivity.saveGame.getMoney() >= arrTravel.get(3).getPrice()
                        && MainActivity.saveGame.getThaiLand() <3) {
                    try {
                        Activity.CreateDialog("ThaiLand","Du lịch");
                        MainActivity.saveGame.saveThaiLand(MainActivity.saveGame.getThaiLand() + 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position ==3 && MainActivity.saveGame.getThaiLand() <3&& MainActivity.saveGame.getMoney() <= arrTravel.get(3).getPrice()){
                    createDialogOFM();
                }
                else if(position ==3 && MainActivity.saveGame.getThaiLand() >=3)
                {
                    MainActivity.createNotification(R.drawable.thailand, "Một năm bạn chỉ được đi du lịch nước này 3 lần", Travel.this);
                }
                if (arrTravel.get(position).getFoodName() == "Vũng Tàu"
                        && MainActivity.saveGame.getMoney() >= arrTravel.get(4).getPrice()
                        && MainActivity.saveGame.getVungTau() <3) {
                    try {
                        Activity.CreateDialog("Vũng Tàu","Du lịch");
                        MainActivity.saveGame.saveVungTau(MainActivity.saveGame.getVungTau() + 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position ==4 && MainActivity.saveGame.getVungTau() <3&& MainActivity.saveGame.getMoney() <= arrTravel.get(4).getPrice()){
                    createDialogOFM();
                }
                else if(position ==4 && MainActivity.saveGame.getVungTau() >=3)
                {
                    MainActivity.createNotification(R.drawable.vungtau, "Một năm bạn chỉ được đi du lịch nơi này 3 lần", Travel.this);
                }
                if (arrTravel.get(position).getFoodName() == "Phú Quốc"
                        && MainActivity.saveGame.getMoney() >= arrTravel.get(5).getPrice()
                        && MainActivity.saveGame.getPhuQuoc() <3) {
                    try {
                        Activity.CreateDialog("Phú Quốc","Du lịch");
                        MainActivity.saveGame.savePhuQuoc(MainActivity.saveGame.getPhuQuoc() + 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position ==5 && MainActivity.saveGame.getPhuQuoc() <3&& MainActivity.saveGame.getMoney() <= arrTravel.get(5).getPrice()){
                    createDialogOFM();
                }
                else if(position ==5 && MainActivity.saveGame.getPhuQuoc() >=3)
                {
                    MainActivity.createNotification(R.drawable.phuquoc, "Một năm bạn chỉ được đi du lịch nơi này 3 lần", Travel.this);
                }
                if (arrTravel.get(position).getFoodName() == "Đà Nẵng"
                        && MainActivity.saveGame.getMoney() >= arrTravel.get(6).getPrice()
                        && MainActivity.saveGame.getDaNang() <3) {
                    try {
                        Activity.CreateDialog("Đà Nẵng","Du lịch");
                        MainActivity.saveGame.saveDaNang(MainActivity.saveGame.getDaNang() + 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position ==6 && MainActivity.saveGame.getDaNang() <3&& MainActivity.saveGame.getMoney() <= arrTravel.get(6).getPrice()){
                    createDialogOFM();
                }
                else if(position ==6 && MainActivity.saveGame.getDaNang() >=3)
                {
                    MainActivity.createNotification(R.drawable.danang, "Một năm bạn chỉ được đi du lịch nơi này 3 lần", Travel.this);
                }
            }
        });
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
    String readEvent()
    {
        jsonEvent = null;
        try {
            InputStream inputStream = getAssets().open("travel.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonEvent = new String(buffer, "UTF-8");
            Traveljs = new JSONObject(jsonEvent);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return jsonEvent;
    }
    private void loadGame() {
        txtName.setText(MainActivity.saveGame.getName());
        txtMoney.setText(MainActivity.saveGame.getMoney() + "K VND");
        txtJob.setText(MainActivity.saveGame.getJob());
        imgAvatar = findViewById(R.id.imageAvatar);
    }

    public void gotoMainMenu(View view)
    {
        //startActivity(new Intent(Travel.this, HoatDong.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
        onBackPressed();
    }
}
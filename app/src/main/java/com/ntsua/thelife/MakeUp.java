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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MakeUp extends AppCompatActivity {
    ListView lvMakeUp;
    FoodAdapter adapter;
    ArrayList<Food> arrMakeUp;
    TextView txtName, txtJob, txtMoney;
    Random random;
    String jsonEvent;
    ActivitiesEvent Activity;
    JSONObject Makeupjs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_up);
        txtName = findViewById(R.id.textviewName);
        txtJob = findViewById(R.id.textviewJob);
        txtMoney = findViewById(R.id.textviewMoney);
        random = new Random();
        arrMakeUp = new ArrayList<Food>();
        lvMakeUp = (ListView) findViewById((R.id.listviewMakeUp));
        loadGame();
        arrMakeUp = new ArrayList<>();
        arrMakeUp.add(new Food("Xăm hình", "Xăm đi ngại chi", R.drawable.tattoo, 0));
        arrMakeUp.add(new Food("Triệt lông", "300 nghìn - Không còn vi-ô-lông, không còn đau rát", R.drawable.wax, 300));
        arrMakeUp.add(new Food("Trị mụn", "1 triệu 500 nghìn - Cho làn da trắng mịn", R.drawable.acnet, 1500));
        arrMakeUp.add(new Food("Skin care", "800 nghìn - Làm sạch các vết nhăn", R.drawable.skincare, 800));
        arrMakeUp.add(new Food("Massage", "800 nghìn - Massage cao cấp", R.drawable.massage, 800));
        arrMakeUp.add(new Food("Xông hơi", "600 nghìn - Xông hơi thư giãn", R.drawable.steam, 600));
        arrMakeUp.add(new Food("Ngâm chân thảo dược", "400 nghìn - Cho bàn chân thư giãn sau ngày dài hoạt động", R.drawable.foot, 400));
        adapter = new FoodAdapter(this, R.layout.food_line, arrMakeUp);
        lvMakeUp.setAdapter(adapter);
        jsonEvent = readEvent();
        Activity = new ActivitiesEvent(jsonEvent,MainActivity.saveGame,MakeUp.this);

        lvMakeUp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (arrMakeUp.get(position).getFoodName() == "Xăm hình" )
                {
                    createDialogchoosetattoo();
                } else if(position == 0) {
                    createDialogOFM();
                }

                if (arrMakeUp.get(position).getFoodName() == "Triệt lông"
                        && MainActivity.saveGame.getMoney() >= arrMakeUp.get(1).getPrice())
                {
                    try {
                        Activity.CreateDialog("Triệt lông","Làm đẹp");
                        loadGame();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position == 1) {
                    createDialogOFM();
                }

                if (arrMakeUp.get(position).getFoodName() == "Trị mụn"
                        && MainActivity.saveGame.getMoney() >= arrMakeUp.get(2).getPrice())
                {
                    try {
                        Activity.CreateDialog("Trị mụn","Làm đẹp");
                        loadGame();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position == 2){
                    createDialogOFM();
                }

                if (arrMakeUp.get(position).getFoodName() == "Skin care"
                        && MainActivity.saveGame.getMoney() >= arrMakeUp.get(3).getPrice())
                {
                    try {
                        Activity.CreateDialog("Skin care","Làm đẹp");
                        loadGame();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position ==3 ){
                    createDialogOFM();
                }

                if (arrMakeUp.get(position).getFoodName() == "Massage"
                        && MainActivity.saveGame.getMoney() >= arrMakeUp.get(4).getPrice())
                {
                    try {
                        Activity.CreateDialog("Massage","Làm đẹp");
                        loadGame();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position==4){
                    createDialogOFM();
                }

                if (arrMakeUp.get(position).getFoodName() == "Xông hơi"
                        && MainActivity.saveGame.getMoney() >= arrMakeUp.get(5).getPrice()) {
                    try {
                        Activity.CreateDialog("Xông hơi","Làm đẹp");
                        loadGame();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position == 5){
                    createDialogOFM();
                }

                if (arrMakeUp.get(position).getFoodName() == "Ngâm chân thảo dược"
                        && MainActivity.saveGame.getMoney() >= arrMakeUp.get(6).getPrice()) {
                    try {
                        Activity.CreateDialog("Ngâm chân thảo dược","Làm đẹp");
                        loadGame();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position ==6){
                    createDialogOFM();
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
    void createDialogchoosetattoo() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_tattoo);
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
    public void loadGame() {
        txtName.setText(MainActivity.saveGame.getName());
        txtMoney.setText(MainActivity.saveGame.getMoney() + "VND");
        txtJob.setText(MainActivity.saveGame.getJob());
    }

    String readEvent()
    {
        jsonEvent = null;
        try {
            InputStream inputStream = getAssets().open("lamdep_event.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonEvent = new String(buffer, "UTF-8");
            Makeupjs = new JSONObject(jsonEvent);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return jsonEvent;
    }

    public void gotoMainMenu(View view)
    {
        //startActivity(new Intent(SportActivity.this, HoatDong.class));
        finish();
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadGame();
    }
}
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

public class Startup extends AppCompatActivity {
    ListView lvStartUp;
    FoodAdapter adapter;
    ArrayList<Food> arrStartUp;
    TextView txtName, txtJob, txtMoney;
    ImageView imgAvatar;
    String jsonEvent;
    ActivitiesEvent Activity;
    JSONObject jsStartup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        txtName = findViewById(R.id.textviewName);
        txtJob = findViewById(R.id.textviewJob);
        txtMoney = findViewById(R.id.textviewMoney);
        imgAvatar = findViewById(R.id.imageAvatar);
        loadGame();

        lvStartUp = (ListView) findViewById((R.id.listviewStartUp));
        arrStartUp = new ArrayList<>();
        arrStartUp.add(new Food("Gọi vốn các 'Shark'", "Bây giờ hoặc không bao giờ. Chi phí đi lại: 1 triệu", R.drawable.sharktank, 1000));
        arrStartUp.add(new Food("Vé số", "Xổ số kiến thiết mang niềm vui đến mọi nhà. Vé số 10k chiều xổ", R.drawable.lottery, 10));
        arrStartUp.add(new Food("Kinh doanh", "kinh doanh tư nhân", R.drawable.shops, 0));
        adapter = new FoodAdapter(this, R.layout.food_line, arrStartUp);
        lvStartUp.setAdapter(adapter);
        jsonEvent = readEvent();
        Activity = new ActivitiesEvent(jsonEvent, Startup.this);
        lvStartUp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(arrStartUp.get(position).getFoodName()=="Gọi vốn các 'Shark'" && MainActivity.saveGame.getMoney() > arrStartUp.get(0).getPrice() )
                {
                    try {
                        Activity.CreateDialog("SharkTank","Startup");
                        loadGame();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (position == 0) createDialogOFM();

                if(arrStartUp.get(position).getFoodName()=="Vé số" && MainActivity.saveGame.getMoney() > arrStartUp.get(1).getPrice())
                {
                    try {
                        Activity.CreateDialog("Lottery","Startup");
                        loadGame();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (position ==1) createDialogOFM();

                if(arrStartUp.get(position).getFoodName() == "Kinh doanh")
                {
                    startActivity(new Intent(Startup.this, Shopping.class));
                }
            }
        });

    }

    String readEvent()
    {
        jsonEvent = null;
        try {
            InputStream inputStream = getAssets().open("startup.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonEvent = new String(buffer, "UTF-8");
            jsStartup = new JSONObject(jsonEvent);
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
        //startActivity(new Intent(Startup.this, HoatDong.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
        onBackPressed();
    }
}
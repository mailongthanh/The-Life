package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class Hospital extends AppCompatActivity {
    ListView lvHospital;
    FoodAdapter adapter;
    ArrayList<Food> arrHospital;
    TextView txtName, txtJob, txtMoney;
    ImageView imgAvatar;
    ArrayList<Sick> arrSick;
    JSONObject jsonSick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        txtName = findViewById(R.id.textviewName);
        txtJob = findViewById(R.id.textviewJob);
        txtMoney = findViewById(R.id.textviewMoney);
        imgAvatar = findViewById(R.id.imageAvatar);
        loadGame();

        lvHospital = (ListView) findViewById((R.id.listviewHospital));
        arrHospital = new ArrayList<>();
        arrHospital.add(new Food("Bác sĩ tâm lý", "3 triệu", R.drawable.psychiatrist, 3000));
        arrHospital.add(new Food("Nha sĩ", "5 triệu", R.drawable.dentist, 5000));
        arrHospital.add(new Food("Bác sĩ mắt", "2 triệu", R.drawable.eyesdoctor, 2000));
        arrHospital.add(new Food("Bác sĩ tai mũi họng", "2 triệu", R.drawable.doctor, 2000));
        arrHospital.add(new Food("Bác sĩ da liễu", "500 nghìn", R.drawable.dalieu, 500));
        arrHospital.add(new Food("Bác sĩ cảm sốt", "500 nghìn", R.drawable.dalieu, 500));
        arrHospital.add(new Food("Bác sĩ trĩ", "500 nghìn", R.drawable.dalieu, 500));

        adapter = new FoodAdapter(this, R.layout.food_line, arrHospital);
        lvHospital.setAdapter(adapter);

        lvHospital.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (arrHospital.get(position).getFoodName() == "Bác sĩ tâm lý") //Mặc định 0 là bác sĩ tâm lí
                {
                    if (MainActivity.saveGame.getHappy() > 30) {
                        MainActivity.createNotification(R.drawable.cancel,
                                "Bạn không có vấn đề gì về tâm lý",
                                Hospital.this);
                        return;
                    }
                    try {
                        JSONArray arr = jsonSick.getJSONArray("tamly");
                        JSONObject object = arr.getJSONObject(new Random().nextInt(arr.length()));
                        dialogResult(object, arrHospital.get(position).getFoodName());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    //Toast.makeText(Hospital.this, arrHospital.get(position).getFoodName(), Toast.LENGTH_SHORT).show();
                    int index = isSick(arrHospital.get(position).getFoodName());
                    if (index == -1) {
                        MainActivity.createNotification(R.drawable.cancel,
                                "Hiện tại bạn không cần đến " + arrHospital.get(position).getFoodName().toLowerCase() + " đâu",
                                Hospital.this);
                        return;
                    }
                    else if (MainActivity.saveGame.getMoney() < arrHospital.get(position).getPrice()){
                        MainActivity.createNotification(R.drawable.money,
                                "Chả đủ tiền, bạn bị viêm màng túi rồi",
                                Hospital.this);
                        return;
                    }
                    else {
                        //Toast.makeText(Hospital.this, arrSick.get(index).getJson(), Toast.LENGTH_SHORT).show();
                        try {

                            JSONArray arr = jsonSick.getJSONArray(arrSick.get(index).getJson());
                            JSONObject object = arr.getJSONObject(new Random().nextInt(arr.length()));
                            arrSick.get(index).setSick(!object.getBoolean("result"));
                            dialogResult(object, arrHospital.get(position).getFoodName());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
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

    int isSick(String doctor)
    {
        for (int i=0; i<arrSick.size(); i++)
        {
            if (arrSick.get(i).getDocter().equals(doctor) && arrSick.get(i).isSick())
                return i;
        }
        return -1;
    }

    void dialogResult(JSONObject object, String titleEvent) throws JSONException {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_event_result);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //Ánh xạ các view
        LinearLayout DialogResult = dialog.findViewById(R.id.dialog_event_result);
        Button btnOke = dialog.findViewById(R.id.buttonDialogEventOke);
        TextView txtTitle = dialog.findViewById(R.id.textviewDialogEventTitle);
        TextView txtContent= dialog.findViewById(R.id.textviewDialogEventContent);
        TextView txtHappy = dialog.findViewById(R.id.textviewResultHappy);
        TextView txtHealth = dialog.findViewById(R.id.textviewResultHealth);
        TextView txtSmart= dialog.findViewById(R.id.textviewResultSmart);
        TextView txtAppearance = dialog.findViewById(R.id.textviewResultAppearance);
        TextView txtAssets = dialog.findViewById(R.id.textviewResultMoney);

        //Gán giá trị
        txtTitle.setText(titleEvent);
        String event = object.getString("event");
        txtContent.setText(event);

        int happy = MainActivity.saveGame.getHappy();
        int health = MainActivity.saveGame.getHealth();
        int smart = MainActivity.saveGame.getSmart();
        int appearance = MainActivity.saveGame.getAppearance();

        int value = 0;
        value = object.getInt("happy");
        if(value == 0)
        {
            DialogResult.removeView(dialog.findViewById(R.id.linearHappy));
        } else {
            toString(value, txtHappy);
            happy += value;
        }

        value = object.getInt("health");
        if(value == 0)
        {
            DialogResult.removeView(dialog.findViewById(R.id.linearHealth));
        } else {
            toString(value, txtHealth);
            health += value;
        }

        value = object.getInt("smart");
        if(value == 0)
        {
            DialogResult.removeView(dialog.findViewById(R.id.linearSmart));
        } else {
            toString(value, txtSmart);
            smart += value;
        }

        value = object.getInt("appearance");
        if(value == 0)
        {
            DialogResult.removeView(dialog.findViewById(R.id.linearAppearance));
        } else {
            toString(value, txtAppearance);
            appearance += value;
        }

        value = object.getInt("assets");
        if (value == 0)
        {
            DialogResult.removeView(dialog.findViewById(R.id.linearMoney));
        } else {
            String money = String.format( "%,d", value*1000);
            if (value > 0)
                money = "+ " + money;
            else {
                money = "- " + money;
                txtAssets.setTextColor(Color.RED);
            }
            txtAssets.setText(money);
            MainActivity.saveGame.saveMoney(MainActivity.saveGame.getMoney() + value);
            txtAssets.setText("$" + value);
        }

        MainActivity.saveGame.savePlayerInfo(happy,health,smart,appearance);
        String contentHTML = MainActivity.saveGame.getDetailActivity();
        contentHTML += event + "<br>";
        MainActivity.saveGame.saveDetailActivity(contentHTML);

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
        arrSick = MainActivity.saveGame.getSick();
        imgAvatar.setImageResource(MainActivity.saveGame.getAvatar());
        try {
            readEvent();
        } catch (JSONException e) {
            //Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void gotoMainMenu(View view)
    {
        //startActivity(new Intent(Hospital.this, HoatDong.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
        onBackPressed();
    }

    void readEvent() throws JSONException {
        String jsonEvent = null;
        try {
            InputStream inputStream = getAssets().open("sick.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonEvent = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        jsonSick = new JSONObject(jsonEvent);
    }

    void toString(int value, TextView txtResult)
    {
        String str = null;
        if (value > 0)
            txtResult.setText("+ " + value);
        else if (value < 0) {
            txtResult.setText("- " + (-1 * value));
            txtResult.setTextColor(Color.RED);
        }
    }
}
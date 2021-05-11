package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Spliterator;

public class SportActivity extends AppCompatActivity {

    ListView lvSport;
    FoodAdapter adapter;
    ArrayList<Food> arrSport;
    JSONObject Sportjs;
    Random random;
    SaveGame saveGame;
    SharedPreferences preference;
    TextView txtName, txtJob, txtMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);

        txtName = findViewById(R.id.textviewName);
        txtJob = findViewById(R.id.textviewJob);
        txtMoney = findViewById(R.id.textviewMoney);
        loadGame();

        lvSport = (ListView) findViewById((R.id.listviewSports));
        preference = getSharedPreferences("data", MODE_PRIVATE);
        saveGame = new SaveGame(preference);
        random = new Random();

        arrSport = new ArrayList<Food>();
        arrSport.add(new Food("Chạy bộ", "FREE", R.drawable.jogging, 0));
        arrSport.add(new Food("Bài tập thể dục", "FREE", R.drawable.triangle, 0));
        arrSport.add(new Food("Bóng đá", "150 nghìn", R.drawable.football, 0));
        arrSport.add(new Food("Bóng bàn", "200 nghìn", R.drawable.ping_pong, 0));
        arrSport.add(new Food("Bóng rỗ", "100 nghìn", R.drawable.basketball, 0));
        arrSport.add(new Food("Bóng chày", "220 nghìn", R.drawable.baseball, 0));
        arrSport.add(new Food("Quần vợt", "1 triệu", R.drawable.tennis_racket, 0));
        arrSport.add(new Food("Cầu lông", "150 nghìn", R.drawable.badminton, 0));
        arrSport.add(new Food("Chạy xe đạp", "150 nghìn", R.drawable.racing, 0));
        arrSport.add(new Food("Leo núi", "300 nghìn", R.drawable.hiking, 0));
        arrSport.add(new Food("Cử tạ", "400 nghìn", R.drawable.fitness, 0));
        arrSport.add(new Food("Võ Vovinam", "200 nghìn", R.drawable.vovinam, 0));
        arrSport.add(new Food("Chèo thuyền", "450 nghìn", R.drawable.rowing, 0));
        arrSport.add(new Food("Lướt sóng", "600 nghìn", R.drawable.surfing, 0));
        arrSport.add(new Food("Lặn", "1 triệu", R.drawable.snorkle, 0));
        arrSport.add(new Food("Yoga", "500 nghìn", R.drawable.yoga, 0));

        adapter = new FoodAdapter(this, R.layout.food_line, arrSport);
        lvSport.setAdapter(adapter);
        readEvent();
        lvSport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    try {
                        JSONArray arrEvent = Sportjs.getJSONArray("jogging");
                        createDialog(arrEvent.getJSONObject(random.nextInt(arrEvent.length())));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (position == 1) {
                    try {
                        JSONArray arrEvent = Sportjs.getJSONArray("exercise");
                        createDialog(arrEvent.getJSONObject(random.nextInt(arrEvent.length())));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (position == 2) {
                    try {
                        JSONArray arrEvent = Sportjs.getJSONArray("football");
                        createDialog(arrEvent.getJSONObject(random.nextInt(arrEvent.length())));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (position == 3) {
                    try {
                        JSONArray arrEvent = Sportjs.getJSONArray("pingpong");
                        createDialog(arrEvent.getJSONObject(random.nextInt(arrEvent.length())));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (position == 4) {
                    try {
                        JSONArray arrEvent = Sportjs.getJSONArray("basketball");
                        createDialog(arrEvent.getJSONObject(random.nextInt(arrEvent.length())));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (position == 5) {
                    try {
                        JSONArray arrEvent = Sportjs.getJSONArray("baseball");
                        createDialog(arrEvent.getJSONObject(random.nextInt(arrEvent.length())));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (position == 6) {
                    try {
                        JSONArray arrEvent = Sportjs.getJSONArray("tennis");
                        createDialog(arrEvent.getJSONObject(random.nextInt(arrEvent.length())));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (position == 7) {
                    try {
                        JSONArray arrEvent = Sportjs.getJSONArray("badminton");
                        createDialog(arrEvent.getJSONObject(random.nextInt(arrEvent.length())));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (position == 8) {
                    try {
                        JSONArray arrEvent = Sportjs.getJSONArray("cycling");
                        createDialog(arrEvent.getJSONObject(random.nextInt(arrEvent.length())));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (position == 9) {
                    try {
                        JSONArray arrEvent = Sportjs.getJSONArray("climb");
                        createDialog(arrEvent.getJSONObject(random.nextInt(arrEvent.length())));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (position == 10) {
                    try {
                        JSONArray arrEvent = Sportjs.getJSONArray("GYM");
                        createDialog(arrEvent.getJSONObject(random.nextInt(arrEvent.length())));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (position == 11) {
                    try {
                        JSONArray arrEvent = Sportjs.getJSONArray("martial");
                        createDialog(arrEvent.getJSONObject(random.nextInt(arrEvent.length())));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (position == 12) {
                    try {
                        JSONArray arrEvent = Sportjs.getJSONArray("rowing");
                        createDialog(arrEvent.getJSONObject(random.nextInt(arrEvent.length())));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (position == 13) {
                    try {
                        JSONArray arrEvent = Sportjs.getJSONArray("surf");
                        createDialog(arrEvent.getJSONObject(random.nextInt(arrEvent.length())));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (position == 14) {
                    try {
                        JSONArray arrEvent = Sportjs.getJSONArray("dive");
                        createDialog(arrEvent.getJSONObject(random.nextInt(arrEvent.length())));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (position == 15) {
                    try {
                        JSONArray arrEvent = Sportjs.getJSONArray("yoga");
                        createDialog(arrEvent.getJSONObject(random.nextInt(arrEvent.length())));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    void createDialog(JSONObject object) throws JSONException {
        //Tao dialog
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_hoatdong);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //Anh xa cac view
        TextView txtContent = dialog.findViewById(R.id.textviewDialogEventContent);
        TextView txtHappy = dialog.findViewById(R.id.textviewResultHappy);
        TextView txtHealth = dialog.findViewById(R.id.textviewResultHealth);
        TextView txtSmart= dialog.findViewById(R.id.textviewResultSmart);
        TextView txtAppearance = dialog.findViewById(R.id.textviewResultAppearance);
        TextView txtAssets = dialog.findViewById(R.id.textviewResultMoney);
        LinearLayout dialogResult = dialog.findViewById(R.id.dialog_event_result);
        Button btnOke = dialog.findViewById(R.id.buttonDialogEventOke);

        txtContent.setText(object.getString("event"));

        int happy = saveGame.getHappy();
        int health = saveGame.getHealth();
        int smart = saveGame.getSmart();
        int appearance = saveGame.getAppearance();
        int value = 0;
        value = object.getInt("happy");
        if (value == 0)
        {
            dialogResult.removeView(dialog.findViewById(R.id.linearHappy));
        } else {
            txtHappy.setText(value + "");
            happy += value;
        }
        value = object.getInt("health");
        if (value == 0)
        {
            dialogResult.removeView(dialog.findViewById(R.id.linearHealth));
        } else {
            txtHealth.setText(value + "");
            health += value;
        }
        value = object.getInt("smart");
        if (value == 0)
        {
            dialogResult.removeView(dialog.findViewById(R.id.linearSmart));
        } else {
            txtSmart.setText(value + "");
            smart += value;
        }
        value = object.getInt("appearance");
        if (value == 0)
        {
            dialogResult.removeView(dialog.findViewById(R.id.linearAppearance));
        } else {
            txtAppearance.setText(value + "");
            appearance += value;
        }
        value = object.getInt("assets");
        if (value == 0)
        {
            dialogResult.removeView(dialog.findViewById(R.id.linearMoney));
        } else {
            txtAssets.setText(String.format( "%,d",value*1000));
            saveGame.saveMoney(saveGame.getMoney() + value);
        }
        saveGame.savePlayerInfo(happy, health, smart, appearance);
        String contentHtml = saveGame.getDetailActivity();
        contentHtml += txtContent.getText().toString() + "<br>";
        saveGame.saveDetailActivity(contentHtml);

        btnOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void loadGame() {
        txtName.setText(saveGame.getName());
        txtMoney.setText("$" + saveGame.getMoney());
        txtJob.setText(saveGame.getJob());
    }

    void readEvent()
    {
        String jsonEvent = null;
        try {
            InputStream inputStream = getAssets().open("sport_event.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonEvent = new String(buffer, "UTF-8");
            Sportjs = new JSONObject(jsonEvent);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
    public void gotoMainMenu(View view)
    {
        //startActivity(new Intent(SportActivity.this, HoatDong.class));
        finish();
    }
}
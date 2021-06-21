package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Random;

public class Entertainment extends AppCompatActivity {

    ListView lvEntertainment;
    FoodAdapter adapter;
    ArrayList<Food> arrEntertainment;
    TextView txtName, txtJob, txtMoney;
    ImageView imgAvatar;
    JSONObject jsonEntertainment;
    JSONObject jsonResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);

        txtName = findViewById(R.id.textviewName);
        txtJob = findViewById(R.id.textviewJob);
        txtMoney = findViewById(R.id.textviewMoney);
        imgAvatar = findViewById(R.id.imageAvatar);
        loadGame();
        readEvent();

        lvEntertainment = (ListView) findViewById((R.id.listviewEntertainment));
        arrEntertainment = new ArrayList<>();
        arrEntertainment.add(new Food("Bar", "1 triệu", R.drawable.bar, 1000));
        arrEntertainment.add(new Food("Đi xem phim", "80 nghìn", R.drawable.movie, 80));
        arrEntertainment.add(new Food("Thư viện", "Free", R.drawable.library, 0));
        adapter = new FoodAdapter(this, R.layout.food_line, arrEntertainment);
        lvEntertainment.setAdapter(adapter);

        lvEntertainment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (arrEntertainment.get(position).getFoodName())
                {
                    case "Bar":
                        try {
                            JSONArray arrobject = jsonEntertainment.getJSONArray("bar");
                            JSONObject[] objects = new JSONObject[]{arrobject.getJSONObject(new Random().nextInt(arrobject.length()))};
                            event(objects);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "Đi xem phim":
                        try {
                            JSONArray arrobject = jsonEntertainment.getJSONArray("film");
                            JSONObject[] objects = new JSONObject[]{arrobject.getJSONObject(new Random().nextInt(arrobject.length()))};
                            event(objects);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "Thư viện":
                        if (MainActivity.saveGame.getLibrary() >= 3) {
                            MainActivity.createNotification(R.drawable.cancel, "Hôm nay bạn đã đi thư viện quá nhiều rồi", Entertainment.this);
                            return;
                        }
                        else {
                            try {
                                JSONArray arrobject = jsonEntertainment.getJSONArray("library");
                                JSONObject[] objects = new JSONObject[]{arrobject.getJSONObject(new Random().nextInt(arrobject.length()))};
                                event(objects);
                                MainActivity.saveGame.saveLibrary(MainActivity.saveGame.getLibrary() + 1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                }
            }
        });
    }

    void event(JSONObject[] object) throws JSONException {
        final String[] event = {object[0].getString("event")};
        String title = object[0].getString("title");

        //Kiem tra su kien co su lua chon hay khong
        final boolean[] isSelection = {object[0].getBoolean("selection")};

        //Tao dialog hien thi su kien
        if (isSelection[0])
            dialogEvent(object, isSelection, title, event);
        else {
            jsonResult = object[0];
            dialogEventResult(title, true);
        }
    }
    void dialogEvent(JSONObject[] object, boolean[] isSelection, String title, String[] event) throws JSONException
    {
        jsonResult = object[0];
        if (!isSelection[0]) {
            JSONArray arr = jsonResult.getJSONArray("event");
            jsonResult = arr.getJSONObject(new Random().nextInt(arr.length()));
            //Tao dialog hien thi ket qua cua event
            dialogEventResult(title, true);
            return;
        }


        //Them thong tin da choi vao textview
        //contentHtml += event[0] + "<br>";

        //Tao dialog va them cac button lua chon vao dialog
        Dialog dialog = createDialog(title, event[0]);
        LinearLayout dialogCustom = dialog.findViewById(R.id.dialog_event);
        JSONArray arrSelect = object[0].getJSONArray("select");
        for (int i = 0; i < arrSelect.length(); i++) {
            JSONObject objectSelect = arrSelect.getJSONObject(i);
            Button btn = addButton(dialogCustom, objectSelect.getString("content"));
            int finalI = i;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        object[0] = arrSelect.getJSONObject(finalI);
                        isSelection[0] = object[0].getBoolean("selection");
                        event[0] = object[0].getString("event");
                        dialog.dismiss();
                        dialogEvent(object, isSelection, title, event);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        dialog.show();
    }

    void dialogEventResult(String title, boolean isAgeEvent) throws JSONException {
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
        txtTitle.setText(title);
        txtContent.setText(jsonResult.getString("event"));

        int happy = MainActivity.saveGame.getHappy();
        int health = MainActivity.saveGame.getHealth();
        int smart = MainActivity.saveGame.getSmart();
        int appearance = MainActivity.saveGame.getAppearance();

        int value = 0;
        value = jsonResult.getInt("happy");
        if(value == 0)
        {
            DialogResult.removeView(dialog.findViewById(R.id.linearHappy));
        } else {
            toString(value, txtHappy);
            happy += value;
        }

        value = jsonResult.getInt("health");
        if(value == 0)
        {
            DialogResult.removeView(dialog.findViewById(R.id.linearHealth));
        } else {
            toString(value, txtHealth);
            health += value;
        }

        value = jsonResult.getInt("smart");
        if(value == 0)
        {
            DialogResult.removeView(dialog.findViewById(R.id.linearSmart));
        } else {
            toString(value, txtSmart);
            smart += value;
        }

        value = jsonResult.getInt("appearance");
        if(value == 0)
        {
            DialogResult.removeView(dialog.findViewById(R.id.linearAppearance));
        } else {
            toString(value, txtAppearance);
            appearance += value;
        }

        value = jsonResult.getInt("assets");
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

            MainActivity.saveGame.saveMoney(MainActivity.saveGame.getMoney() + value);
            txtMoney.setText(MainActivity.saveGame.getMoney() + " VND");
            txtAssets.setText("$" + value);
        }

        MainActivity.saveGame.savePlayerInfo(happy,health,smart,appearance);
        String contentHTML = MainActivity.saveGame.getDetailActivity();
        contentHTML += txtContent.getText().toString() + "<br>";
        MainActivity.saveGame.saveDetailActivity(contentHTML);

        btnOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (MainActivity.saveGame.getHealth() < 0)
                    MainActivity.checkMySelf(Entertainment.this, "\"Qua đời vì sức khỏe yếu kéo dài, không chịu nổi những biến cố trong cuộc sống\"");
            }
        });

        dialog.show();
    }

    @SuppressLint("ResourceAsColor")
    Button addButton(LinearLayout dialogCustom, String text)
    {
        Button btn = new Button(dialogCustom.getContext());
        btn.setText(text);
        btn.setTextSize(15);
        btn.setBackgroundResource(R.drawable.custom_button_menu);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(800, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 10;
        params.topMargin = 10;
        params.gravity = Gravity.CENTER_HORIZONTAL;
        btn.setLayoutParams(params);
        btn.setTextColor( Color.argb(255,16,54,103));
        dialogCustom.addView(btn);

        return btn;
    }

    Dialog createDialog(String title, String event){
        //Dinh dang dialog
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_event);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogEventAnimation;
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK)
                {
                    return true;
                }
                return false;
            }
        });

        //Anh xa cac phan tu trong dialog
        TextView txtTitle = dialog.findViewById(R.id.textviewDialogEventTitle);
        TextView txtContent = dialog.findViewById(R.id.textviewDialogEventContent);

        //Gan gia tri vao view
        txtContent.setText(event);
        txtTitle.setText(title);

        return dialog;
    }

    private void loadGame() {
        txtName.setText(MainActivity.saveGame.getName());
        txtMoney.setText(MainActivity.saveGame.getMoney() + "VND");
        txtJob.setText(MainActivity.saveGame.getJob());
        imgAvatar.setImageResource(MainActivity.saveGame.getAvatar());
    }

    void readEvent()
    {
        String jsonEvent = null;
        try {
            InputStream inputStream = getAssets().open("entertainment.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonEvent = new String(buffer, "UTF-8");
            jsonEntertainment = new JSONObject(jsonEvent);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
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

    public void gotoMainMenu(View view)
    {
        //startActivity(new Intent(Entertainment.this, HoatDong.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
        onBackPressed();
    }
}
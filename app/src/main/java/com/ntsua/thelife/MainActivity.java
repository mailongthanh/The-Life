package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btnActivity, btnRelationship;
    ImageButton ibtnAddAge;
    TextView txtContent;
    SharedPreferences preferences;
    SaveGame saveGame;
    JSONArray arrJsonAge, arrJsonEvent;
    JSONObject jsonResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        saveGame.saveAge(1);
        try {
            readEvent();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Test
        try {
            init("Name", "vn");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //SpannableString s = new SpannableString(txtContent.getText().toString());
        //s.setSpan(new RelativeSizeSpan(2f), 0, 3, 0);
        //txtContent.setText(saveGame.getDetailActivity());
        //saveGame.saveDetailActivity(txtContent.getText().toString());

        ibtnAddAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    addAge();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void addAge() throws JSONException {
        //them tuoi
        int age = saveGame.getAge() + 1;
        //saveGame.saveAge(age);
        //lay su kien tuoi
        JSONArray arrAge = arrJsonAge.getJSONArray(age);
        Random random = new Random();
        final JSONObject[] object = {arrAge.getJSONObject(random.nextInt(arrAge.length()))};

        final String[] event = {object[0].getString("event")};
        String title = object[0].getString("title");



        //Kiem tra su kien co su lua chon hay khong
        final boolean[] isSelection = {object[0].getBoolean("selection")};
//        while (isSelection[0])
//        {
//            Dialog dialog = createDialog(title, event[0]);
//
//            LinearLayout dialogCustom = dialog.findViewById(R.id.dialog_event);
//            JSONArray arrSelect = object[0].getJSONArray("select");
//            for (int i = 0; i < arrSelect.length(); i++) {
//                JSONObject objectSelect = arrSelect.getJSONObject(i);
//                Button btn = addButton(dialogCustom, objectSelect.getString("content"));
//                int finalI = i;
//                btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        try {
//                            object[0] = arrSelect.getJSONObject(finalI);
//                            isSelection[0] = object[0].getBoolean("selection");
//                            event[0] = object[0].getString("event");
//                            dialog.dismiss();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            }
//            countDownTimer.start();
//            dialog.show();
//            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dialog) {
//                    countDownTimer.cancel();
//                }
//            });
//            //isSelection[0] = false;
//        }

        if (isSelection[0])
            dialogEvent(object, isSelection, title, event);
        else {
            jsonResult = object[0];
            dialogEventResult(title);
        }
    }

    void dialogEvent(JSONObject[] object, boolean[] isSelection, String title, String[] event) throws JSONException
    {
        jsonResult = object[0];
        if (!isSelection[0]) {
            //Tao dialog hien thi ket qua cua event
            dialogEventResult(title);
            return;
        }
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
        //isSelection[0] = false;
    }

    void dialogEventResult(String title) throws JSONException {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_event_result);
        dialog.setCanceledOnTouchOutside(false);


        //Lay gia tri gan vao dialog
        LinearLayout dialogResult = dialog.findViewById(R.id.dialog_event_result);
        Button btnOke = dialog.findViewById(R.id.buttonDialogEventOke);
        TextView txtTitle = dialog.findViewById(R.id.textviewDialogEventTitle);
        TextView txtContent= dialog.findViewById(R.id.textviewDialogEventContent);
        TextView txtHappy = dialog.findViewById(R.id.textviewResultHappy);
        TextView txtHealth = dialog.findViewById(R.id.textviewResultHealth);
        TextView txtSmart= dialog.findViewById(R.id.textviewResultSmart);
        TextView txtAppearance = dialog.findViewById(R.id.textviewResultAppearance);
        TextView txtAssets = dialog.findViewById(R.id.textviewResultMoney);

        txtTitle.setText(title);
        txtContent.setText(jsonResult.getString("event"));


        int value = 0;
        value = jsonResult.getInt("happy");
        if (value == 0)
        {
            dialogResult.removeView(dialog.findViewById(R.id.linearHappy));
        } else {
            txtHappy.setText(value + "");
        }
        value = jsonResult.getInt("health");
        if (value == 0)
        {
            dialogResult.removeView(dialog.findViewById(R.id.linearHealth));
        } else {
            txtHealth.setText(value + "");
        }
        value = jsonResult.getInt("smart");
        if (value == 0)
        {
            dialogResult.removeView(dialog.findViewById(R.id.linearSmart));
        } else {
            txtSmart.setText(value + "");
        }
        value = jsonResult.getInt("appearance");
        if (value == 0)
        {
            dialogResult.removeView(dialog.findViewById(R.id.linearAppearance));
        } else {
            txtAppearance.setText(value + "");
        }
        value = jsonResult.getInt("assets");
        if (value == 0)
        {
            dialogResult.removeView(dialog.findViewById(R.id.linearMoney));
        } else {
            txtAssets.setText(value + "");
        }

        btnOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    Button addButton(LinearLayout dialogCustom, String text)
    {
        Button btn = new Button(dialogCustom.getContext());
        btn.setText(text);
        btn.setTextSize(15);
        btn.setBackgroundResource(R.drawable.custom_button_menu);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(400, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 10;
        params.topMargin = 10;
        params.gravity = Gravity.CENTER_HORIZONTAL;
        btn.setLayoutParams(params);
        btn.setTextColor(Color.WHITE);
        dialogCustom.addView(btn);

        return btn;
    }

    Dialog createDialog(String title, String event) throws JSONException {
        //Dinh dang dialog
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_event);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //Anh xa cac phan tu trong dialog
        TextView txtTitle = dialog.findViewById(R.id.textviewDialogEventTitle);
        TextView txtContent = dialog.findViewById(R.id.textviewDialogEventContent);

        //Gan gia tri vao view
        txtContent.setText(event);
        txtTitle.setText(title);

        return dialog;
    }

    void readEvent() throws JSONException {
        String jsonEvent = null;
        try {
            InputStream inputStream = getAssets().open("age_event.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonEvent = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject object = new JSONObject(jsonEvent);
        arrJsonAge = object.getJSONArray("age");
        arrJsonEvent = object.getJSONArray("event");
    }

    private void AnhXa() {
        btnRelationship = (Button) findViewById(R.id.buttonRelationship);
        btnActivity = (Button) findViewById(R.id.buttonActivity);
        txtContent = findViewById(R.id.textViewDetail);
        ibtnAddAge = findViewById(R.id.imagebuttonAddAge);

        preferences = getSharedPreferences("data", MODE_PRIVATE);
        saveGame = new SaveGame(preferences);
    }

    public void gotoActivity(View view)
    {
        startActivity(new Intent(MainActivity.this, HoatDong.class));
    }

    public void gotoRelationship(View view)
    {
        startActivity(new Intent(MainActivity.this, RelationShip.class));
    }

    // init tam thoi
    void init(String name, String country) throws JSONException {
        String json = null;
        try {
            InputStream inputStream = getAssets().open("parent_name.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject object = new JSONObject(json);
        JSONObject vn = object.getJSONObject("vi");
        JSONArray arrFather = vn.getJSONArray("father");
        JSONArray arrMother = vn.getJSONArray("mother");
        Random random = new Random();

        String s = "Tôi tên " + name + "\n" +
                "Bố tôi là " + arrFather.getString(random.nextInt(arrFather.length())) + "\n" +
                "Mẹ tôi là " + arrMother.getString(random.nextInt(arrMother.length()));
        //Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        txtContent.setText(s);
    }
}
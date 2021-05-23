package com.ntsua.thelife;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.Random;

public class ActivitiesEvent {
    JSONObject jsonevent;
    Random random;
    Context context;
    List<QuanHe> arrQuanHe;

    public ActivitiesEvent(String Json, Context context) {
        try {
            jsonevent = new JSONObject(Json);
            this.context = context;
            arrQuanHe = MainActivity.saveGame.getRelationship();
        } catch (JSONException e) {
            e.printStackTrace();
        };

        random = new Random();
    }
    public void CreateDialog(String name,String titleEvent) throws JSONException {
        JSONArray arrEvent = jsonevent.getJSONArray(name);
        AddDialog(arrEvent.getJSONObject(random.nextInt(arrEvent.length())),titleEvent);
    }
    void AddDialog(JSONObject object, String titleEvent) throws JSONException {
        Dialog dialog = new Dialog(context);
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

            String str = toString(value);

            txtHappy.setText(str);
            happy += value;
        }

        value = object.getInt("health");
        if(value == 0)
        {
            DialogResult.removeView(dialog.findViewById(R.id.linearHealth));
        } else {
            String str = toString(value);
            txtHealth.setText(str);
            health += value;
        }

        value = object.getInt("smart");
        if(value == 0)
        {
            DialogResult.removeView(dialog.findViewById(R.id.linearSmart));
        } else {
            String str = toString(value);
            txtSmart.setText(str);
            smart += value;
        }

        value = object.getInt("appearance");
        if(value == 0)
        {
            DialogResult.removeView(dialog.findViewById(R.id.linearAppearance));
        } else {
            String str = toString(value);
            txtAppearance.setText(str);
            appearance += value;
        }

        value = object.getInt("assets");
        if (value == 0)
        {
            DialogResult.removeView(dialog.findViewById(R.id.linearMoney));
        } else {
            String money = String.format( "%,d", value*1000);
            if (value > 0)
                money = "+" + money;
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

    String toString(int value)
    {
        String str = null;
        if (value > 0)
            str = "+ " + value;
        else if (value < 0)
            str = "- " + (-1*value);
        //Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        return str;
    }
}

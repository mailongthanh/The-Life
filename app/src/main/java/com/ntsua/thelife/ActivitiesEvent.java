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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ActivitiesEvent {
    JSONObject jsonevent;
    Random random;
    Context context;
    ArrayList<QuanHe> arrQuanHe;

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
                if (isNewFriend())
                {
                    newFriend();
                }
            }
        });

        dialog.show();
    }

    boolean isNewFriend()
    {
        int friend = new Random().nextInt(10);
        if (friend < 3 &&
                MainActivity.saveGame.getNumberOfFriends() < MainActivity.arrFriend.size() &&
        MainActivity.saveGame.getNewFriendInYear() < 3) // Khả năng có bạn mới là 30% :D
            return true;
        return false;
    }

    void newFriend()
    {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_new_friend);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //Anh xa
        TextView txtName = dialog.findViewById(R.id.textviewFriendName);
        TextView txtAge = dialog.findViewById(R.id.textviewFriendAge);
        ImageView imgAvatar = dialog.findViewById(R.id.imageViewNewFriend);
        Button btnAccept = dialog.findViewById(R.id.buttonDialogFriendAccept);
        Button btnCancel = dialog.findViewById(R.id.buttonDialogFriendCancel);

        QuanHe friend = whoIsNewFriend();

        txtName.setText(friend.getHoten());
        txtAge.setText(friend.getTuoi() +" Tuổi");
        imgAvatar.setImageResource(friend.getHinhAnh());

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrQuanHe.add(friend);
                MainActivity.saveGame.saveRelationship(arrQuanHe);
                MainActivity.createNotification(friend.getHinhAnh(),
                        "Bạn đã đồng ý kết bạn với " + friend.getHoten(),
                        context);
                MainActivity.saveGame.saveNewFriendInYear(MainActivity.saveGame.getNewFriendInYear() + 1);
                MainActivity.saveGame.saveNumberOfFriends(MainActivity.saveGame.getNumberOfFriends() + 1);
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.createNotification(friend.getHinhAnh(),
                        "Bạn đã từ chối kết bạn với " + friend.getHoten(),
                        context);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    QuanHe whoIsNewFriend() // random ra nguoi ban moi (Nguoi ay la ai ?)
    {
        QuanHe friend = null;
        Random random = new Random();

        do {
            int index = random.nextInt(MainActivity.arrFriend.size());
            friend = MainActivity.arrFriend.get(index);
        }
        while (arrQuanHe.indexOf(friend) != -1);

        return friend;
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

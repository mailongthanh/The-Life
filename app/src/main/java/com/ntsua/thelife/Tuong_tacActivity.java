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
import java.util.regex.Pattern;

public class Tuong_tacActivity extends AppCompatActivity {

    ListView lvTuongTac;
    ArrayList<HoatDongQH> MangTuongTac;
    TextView txthoten, txttuoi, txtquanhe, txtName, txtJob, txtMoney;
    ImageView hinhanh;
    ProgressBar pbqhanhe;
    int dothanmat;
    Bundle bundle;
    JSONObject jsonRelationship;
    SaveGame saveGame;
    SharedPreferences preference;
    Random random;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuong_tac);

        txthoten = findViewById(R.id.textviewNameA);
        txttuoi = findViewById(R.id.tvTuoi);
        txtquanhe = findViewById(R.id.TvQuanHe);
        hinhanh = findViewById(R.id.imageAvatarA);
        pbqhanhe = findViewById(R.id.pbThanhQuanHeA);
        txtName = findViewById(R.id.textviewName);
        txtJob = findViewById(R.id.textviewJob);
        txtMoney = findViewById(R.id.textviewMoney);

        //Nhận dữ liệu
        Intent intent=getIntent();
        bundle = intent.getBundleExtra("BUN");

        //Ánh xạ quan hệ
        txthoten.setText(bundle.getString("hoten"));
        txttuoi.setText("( " + bundle.getInt("tuoi") + " Tuổi )");
        txtquanhe.setText(bundle.getString("quanhe").toUpperCase());
        hinhanh.setImageResource(bundle.getInt("hinhanh"));
        pbqhanhe.setProgress(bundle.getInt("dothanmat"));
        dothanmat = bundle.getInt("dothanmat");
        position = bundle.getInt("position");

        //Ánh xạ listview
        lvTuongTac = (ListView) findViewById(R.id.ListViewTuongTac);
        MangTuongTac = new ArrayList<HoatDongQH>();
        preference = getSharedPreferences("data", MODE_PRIVATE);
        saveGame = new SaveGame(preference);
        random = new Random();

        MangTuongTac.add(new HoatDongQH(R.drawable.money, "Xin tiền", "Không làm mà vẫn có ăn"));
        MangTuongTac.add(new HoatDongQH(R.drawable.assualt, "Hành hung", "Một chuỳ vào đầu"));
        MangTuongTac.add(new HoatDongQH(R.drawable.compliment, "Khen ngợi", "Thảo mai khen ngợi"));
        MangTuongTac.add(new HoatDongQH(R.drawable.communication, "Đàm đạo", "Đàm đạo chuyện thế gian"));
        MangTuongTac.add(new HoatDongQH(R.drawable.insult, "Xúc phạm", "Giết người bằng lời nói"));
        MangTuongTac.add(new HoatDongQH(R.drawable.film, "Rủ xem phim", "Phimcuzzzzz.net"));

        HoatDongQHAdapter adapter = new HoatDongQHAdapter(
                Tuong_tacActivity.this,
                R.layout.tuong_tac,
                MangTuongTac
        );

        lvTuongTac.setAdapter(adapter);

        //Read Event
        readEvent();

        //Load game
        loadGame();

        lvTuongTac.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (MangTuongTac.get(position).TenHoatDong)
                {
                    case "Xin tiền":
                        try {
                            JSONArray arrEvent = jsonRelationship.getJSONArray("money");
                            createDialog(arrEvent.getJSONObject(random.nextInt(arrEvent.length())), "Xin tiền");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "Hành hung":
                        break;
                    case "Khen ngợi":
                        break;
                    case "Đàm đạo":
                        break;
                    case "Xúc phạm":
                        break;
                    case "Rủ xem phim":
                        break;
                }
            }
        });
    }

    void createDialog(JSONObject object, String titleEvent) throws JSONException {
        //Tao dialog
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_relationship);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //Anh xa cac view
        TextView txtTitle = dialog.findViewById(R.id.textviewDialogRelationshipTitle);
        TextView txtNameRelationship = dialog.findViewById(R.id.textviewNameRelationship);
        TextView txtNameActivity = dialog.findViewById(R.id.textviewRelationshipTitle);
        TextView txtContent = dialog.findViewById(R.id.textviewDialogRelationshipContent);

        TextView txtHappy = dialog.findViewById(R.id.textviewRelationship_Happy);
        TextView txtHealth = dialog.findViewById(R.id.textviewRelationship_Health);
        TextView txtSmart= dialog.findViewById(R.id.textviewRelationship_Smart);
        TextView txtAppearance = dialog.findViewById(R.id.textviewRelationship_Appearance);
        TextView txtAssets = dialog.findViewById(R.id.textviewRelationship_Money);
        TextView txtRelationship = dialog.findViewById(R.id.textviewRelationship);

        LinearLayout dialogResult = dialog.findViewById(R.id.dialog_Relationship);
        Button btnOke = dialog.findViewById(R.id.buttonDialogRelationship_Oke);

        //Gan gia tri
        txtTitle.setText(txthoten.getText().toString());
        txtNameRelationship.setText(txtquanhe.getText().toString());
        txtNameActivity.setText(titleEvent);
        String event = object.getString("event").replaceAll( "@", bundle.getString("hoten"));
        txtContent.setText(event);


        int happy = saveGame.getHappy();
        int health = saveGame.getHealth();
        int smart = saveGame.getSmart();
        int appearance = saveGame.getAppearance();

        int value = 0;
        value = object.getInt("happy");
        if (value == 0)
        {
            dialogResult.removeView(dialog.findViewById(R.id.linearHappyRelationship));
        } else {
            txtHappy.setText(value + "");
            happy += value;
        }
        value = object.getInt("health"); //Toast.makeText(this, value + "", Toast.LENGTH_SHORT).show();
        if (value == 0)
        {
            dialogResult.removeView(dialog.findViewById(R.id.linearHealthRelationship));
        } else {
            txtHealth.setText(value + "");
            health += value;
        }
        value = object.getInt("smart");
        if (value == 0)
        {
            dialogResult.removeView(dialog.findViewById(R.id.linearSmartRelationship));
        } else {
            txtSmart.setText(value + "");
            smart += value;
        }
        value = object.getInt("appearance");
        if (value == 0)
        {
            dialogResult.removeView(dialog.findViewById(R.id.linearAppearanceRelationship));
        } else {
            txtAppearance.setText(value + "");
            appearance += value;
        }
        value = object.getInt("assets");
        if (value == 0)
        {
            dialogResult.removeView(dialog.findViewById(R.id.linearMoneyRelationship));
        } else {
            txtAssets.setText(String.format( "%,d",value*1000));
            saveGame.saveMoney(saveGame.getMoney() + value);
            txtMoney.setText("$" + value);
        }
        saveGame.savePlayerInfo(happy, health, smart, appearance);
        value = object.getInt("relationship");
        if (value == 0)
        {
            dialogResult.removeView(dialog.findViewById(R.id.linearRelationship));
        } else {
            txtRelationship.setText(value + "");

            dothanmat += value;
            if (dothanmat < 0)
                dothanmat = 0;

            //Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
            ArrayList<QuanHe> arrQuanHe = saveGame.getRelationship();
            pbqhanhe.setProgress(dothanmat);

            arrQuanHe.get(position).setDoThanMat(dothanmat);
            saveGame.saveRelationship(arrQuanHe);
        }

        String contentHtml = saveGame.getDetailActivity();
        contentHtml += event + "<br>";
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
            InputStream inputStream = getAssets().open("relationship_event.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonEvent = new String(buffer, "UTF-8");
            jsonRelationship = new JSONObject(jsonEvent);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    public void gotoRelationship(View view)
    {
        startActivity(new Intent(Tuong_tacActivity.this, RelationShip.class));
    };

}
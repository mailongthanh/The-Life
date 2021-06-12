package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
    ArrayList<QuanHe> arrQuanHe;
    TextView txthoten, txttuoi, txtquanhe, txtName, txtJob, txtMoney;
    ImageView hinhanh;
    ProgressBar pbqhanhe;
    int dothanmat;
    Bundle bundle;
    JSONObject jsonRelationship;
    Random random;
    int position;
    QuanHe quanHe;
    NameOfRelationship nameOfRelationship;
    View view;
    HoatDongQHAdapter adapter;
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

        nameOfRelationship = (NameOfRelationship) bundle.get("quanhe");
        txtquanhe.setText(nameOfRelationship.toString());
        hinhanh.setImageResource(bundle.getInt("hinhanh"));
        pbqhanhe.setProgress(bundle.getInt("dothanmat"));
        dothanmat = bundle.getInt("dothanmat");
        position = bundle.getInt("position");
        quanHe  = MainActivity.saveGame.getRelationship().get(position);

        //Ánh xạ listview
        lvTuongTac = (ListView) findViewById(R.id.ListViewTuongTac);
        MangTuongTac = new ArrayList<HoatDongQH>();
        random = new Random();

        adapter = new HoatDongQHAdapter(
                Tuong_tacActivity.this,
                R.layout.tuong_tac,
                MangTuongTac
        );

        lvTuongTac.setAdapter(adapter);

        recreateListview();
        //Read Event
        readEvent();
        //Load game
        loadGame();



        lvTuongTac.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
                quanHe  = MainActivity.saveGame.getRelationship().get(position);
                switch (MangTuongTac.get(index).TenHoatDong)
                {
                    case "Xin tiền":
                        //Toast.makeText(Tuong_tacActivity.this, quanHe.getDoThanMat() + " - " + quanHe.getQuanHe().toString(), Toast.LENGTH_SHORT).show();
                        if (quanHe.getDoThanMat() < 10 && quanHe.getQuanHe() != NameOfRelationship.Friend)
                        {
                            MainActivity.createNotification(R.drawable.holding_hands, "Hãy làm " + quanHe.getQuanHe() + " vui trước khi nghĩ tới chuyện xin tiền", Tuong_tacActivity.this);
                            return;
                        }
                        try {
                            JSONArray arrEvent = jsonRelationship.getJSONArray("money");
                            createDialog(arrEvent.getJSONObject(random.nextInt(arrEvent.length())), "Xin tiền");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "Hành hung":
                        try {
                            JSONArray arrEvent = jsonRelationship.getJSONArray("assault");
                            createDialog(arrEvent.getJSONObject(random.nextInt(arrEvent.length())), "Hành hung");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "Khen ngợi":
                        try {
                            JSONArray arrEvent = jsonRelationship.getJSONArray("praise");
                            createDialog(arrEvent.getJSONObject(random.nextInt(arrEvent.length())), "Khen ngợi");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "Đàm đạo":
                        try {
                            JSONArray arrEvent = jsonRelationship.getJSONArray("talk");
                            createDialog(arrEvent.getJSONObject(random.nextInt(arrEvent.length())), "Đàm đạo");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "Xúc phạm":
                        try {
                            JSONArray arrEvent = jsonRelationship.getJSONArray("offense");
                            createDialog(arrEvent.getJSONObject(random.nextInt(arrEvent.length())), "Xúc phạm");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "Rủ xem phim":
                        try {
                            JSONArray arrEvent = jsonRelationship.getJSONArray("movie");
                            createDialog(arrEvent.getJSONObject(random.nextInt(arrEvent.length())), "Rủ xem phim");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "Tỏ tình":
                        if (MainActivity.saveGame.getDating())
                        {
                            MainActivity.createNotification(R.drawable.cancel,
                                    "Đừng có mà bắt cá hai tay",
                                    Tuong_tacActivity.this);
                            return;
                        }
                        if (isSuccess()) {
                            createDialogOFM(R.layout.dialog_totinhthanhcong);
                            arrQuanHe.get(position).setQuanHe(NameOfRelationship.GirlFriend);
                            if (!MainActivity.saveGame.getGender())
                                arrQuanHe.get(position).setQuanHe(NameOfRelationship.BoyFriend);
                            MainActivity.saveGame.saveRelationship(arrQuanHe);
                            MainActivity.saveGame.saveDating(true);
                            recreateListview();
                        }
                        else {
                            createDialog();
                        }
                        break;
                    case "Chia tay":
                        try {
                            JSONArray arrObject = jsonRelationship.getJSONArray("breakup");
                            JSONObject object = arrObject.getJSONObject(random.nextInt(arrQuanHe.size()));
                            if(object.getBoolean("result"))
                            {
                                arrQuanHe.get(position).setQuanHe(NameOfRelationship.Friend);
                                MainActivity.saveGame.saveRelationship(arrQuanHe);
                                MainActivity.saveGame.saveDating(false);
                                recreateListview();
                            }
                            createDialog(object, "Chia tay");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "Cầu hôn":
                        if(CauHonSuccess())
                            {
                                createDialogOFM(R.layout.layout_kethonthanhcong);
                                arrQuanHe.get(position).setQuanHe(NameOfRelationship.Wife);
                                if (!MainActivity.saveGame.getGender())
                                    arrQuanHe.get(position).setQuanHe(NameOfRelationship.Husband);
                                MainActivity.saveGame.saveRelationship(arrQuanHe);
                                MainActivity.saveGame.saveDating(true);
                                recreateListview();
                            }
                        else{
                                createDialog();
                        }
                        break;
                }
            }
        });
    }
    void recreateListview()
    {
        MangTuongTac.clear();
        MangTuongTac.add(new HoatDongQH(R.drawable.money, "Xin tiền", "Không làm mà vẫn có ăn"));
        MangTuongTac.add(new HoatDongQH(R.drawable.assualt, "Hành hung", "Một chuỳ vào đầu"));
        MangTuongTac.add(new HoatDongQH(R.drawable.compliment, "Khen ngợi", "Thảo mai khen ngợi"));
        MangTuongTac.add(new HoatDongQH(R.drawable.communication, "Đàm đạo", "Đàm đạo chuyện thế gian"));
        MangTuongTac.add(new HoatDongQH(R.drawable.insult, "Xúc phạm", "Giết người bằng lời nói"));
        MangTuongTac.add(new HoatDongQH(R.drawable.film, "Rủ xem phim", "Phimcuzzzzz.net"));
        quanHe  = MainActivity.saveGame.getRelationship().get(position);
        if (quanHe.getQuanHe() == NameOfRelationship.Friend)
        {
            MangTuongTac.add(new HoatDongQH(R.drawable.love, "Tỏ tình", "Mày yêu tao không để tao còn tán con khác"));
        }
        if (quanHe.getQuanHe() == NameOfRelationship.Wife||quanHe.getQuanHe() == NameOfRelationship.Husband)
        {
            MangTuongTac.add(new HoatDongQH(R.drawable.love, "Chia tay", "Cảm thấy đối phương không còn như xưa"));
        }
        if (quanHe.getQuanHe() == NameOfRelationship.BoyFriend || quanHe.getQuanHe() == NameOfRelationship.GirlFriend) {
            MangTuongTac.add(new HoatDongQH(R.drawable.love, "Chia tay", "Cảm thấy đối phương không còn như xưa"));
            MangTuongTac.add(new HoatDongQH(R.drawable.love, "Cầu hôn", "Sau này ngồi cùng bàn thờ"));
        }
        adapter.notifyDataSetChanged();
        txtquanhe.setText(quanHe.getQuanHe().toString());
    }
    void createDialogOFM(int id) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(id);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnOke = dialog.findViewById(R.id.buttonDialogEventOke);
        btnOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    void createDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.layout_kethonthatbai);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnOke = dialog.findViewById(R.id.buttonDialogEventOke);
        btnOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
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
        String event = "";
        if (nameOfRelationship == NameOfRelationship.Friend)
            event = object.getString("event").replaceAll( "@", bundle.getString("hoten"));
        else
            event = object.getString("event").replaceAll( "@", nameOfRelationship.toString());
        txtContent.setText(event);


        int happy = MainActivity.saveGame.getHappy();
        int health = MainActivity.saveGame.getHealth();
        int smart = MainActivity.saveGame.getSmart();
        int appearance = MainActivity.saveGame.getAppearance();

        int value = 0;
        value = object.getInt("happy");
        if (value == 0)
        {
            dialogResult.removeView(dialog.findViewById(R.id.linearHappyRelationship));
        } else {
            toString(value, txtHappy);
            happy += value;
        }
        value = object.getInt("health"); //Toast.makeText(this, value + "", Toast.LENGTH_SHORT).show();
        if (value == 0)
        {
            dialogResult.removeView(dialog.findViewById(R.id.linearHealthRelationship));
        } else {
            toString(value, txtHealth);
            health += value;
        }
        value = object.getInt("smart");
        if (value == 0)
        {
            dialogResult.removeView(dialog.findViewById(R.id.linearSmartRelationship));
        } else {
            toString(value, txtSmart);
            smart += value;
        }
        value = object.getInt("appearance");
        if (value == 0)
        {
            dialogResult.removeView(dialog.findViewById(R.id.linearAppearanceRelationship));
        } else {
            toString(value, txtAppearance);
            appearance += value;
        }

        value = object.getInt("assets");
        if (value == 0)
        {
            dialogResult.removeView(dialog.findViewById(R.id.linearMoneyRelationship));
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
            txtMoney.setText(value + MainActivity.saveGame.getMoney() + " VND");
        }
        MainActivity.saveGame.savePlayerInfo(happy, health, smart, appearance);
        value = object.getInt("relationship");
        if (value == 0)
        {
            dialogResult.removeView(dialog.findViewById(R.id.linearRelationship));
        } else {
            String thanmat = value + "";
            if (value > 0)
                thanmat = "+ " + thanmat;
            else {
                txtAssets.setTextColor(Color.RED);
            }
            txtRelationship.setText(thanmat);

            dothanmat += value;
            if (dothanmat < 0)
                dothanmat = 0;

            //Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
            pbqhanhe.setProgress(dothanmat);

            arrQuanHe.get(position).setDoThanMat(dothanmat);
            MainActivity.saveGame.saveRelationship(arrQuanHe);
        }

        String contentHtml = MainActivity.saveGame.getDetailActivity();
        contentHtml += event + "<br>";
        MainActivity.saveGame.saveDetailActivity(contentHtml);

        btnOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pbqhanhe.getProgress() <= 0 && nameOfRelationship == NameOfRelationship.Friend
                        || pbqhanhe.getProgress() <= 30 && (nameOfRelationship == NameOfRelationship.BoyFriend || nameOfRelationship == NameOfRelationship.GirlFriend))
                    breakFriend();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    void breakFriend()
    {
        Dialog dialog = MainActivity.createNotification(R.drawable.cancel, "Mối quan hệ giữa bạn và " + txthoten.getText() + " đã đổ vỡ.", this);
        Button btnOke  = dialog.findViewById(R.id.buttonNotificationtOke);
        ArrayList<QuanHe> arrQuanHe = MainActivity.saveGame.getRelationship();
        if (nameOfRelationship == NameOfRelationship.BoyFriend || nameOfRelationship == NameOfRelationship.GirlFriend)
            MainActivity.saveGame.saveDating(false);
        arrQuanHe.remove(position);
        MainActivity.saveGame.saveNumberOfFriends(MainActivity.saveGame.getNumberOfFriends() - 1);
        MainActivity.saveGame.saveRelationship(arrQuanHe);
        btnOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        dialog.show();
    }

    boolean isSuccess()
    {
        int fail = (100 - pbqhanhe.getProgress()) * 3;

        int ability = new Random().nextInt(100) + 1;
        if (ability > 100 - fail) //Nằm trong khoảng lớn fail đến 100
            return false; // :((
        else  return true; //=))
    }
    boolean CauHonSuccess()
    {
        int success = pbqhanhe.getProgress();
        if (success ==100)
            return true;
        else  return false;
    }

    private void loadGame() {
        txtName.setText(MainActivity.saveGame.getName());
        txtMoney.setText(MainActivity.saveGame.getMoney() + " VND");
        txtJob.setText(MainActivity.saveGame.getJob());
        arrQuanHe = MainActivity.saveGame.getRelationship();
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
        //startActivity(new Intent(Tuong_tacActivity.this, RelationShip.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
        onBackPressed();
    };

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
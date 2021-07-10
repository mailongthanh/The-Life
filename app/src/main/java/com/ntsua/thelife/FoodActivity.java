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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class FoodActivity extends AppCompatActivity {

    ListView lvFood;
    FoodAdapter adapter;
    ArrayList<Food> arrFood;
    TextView txtName, txtJob, txtMoney;
    ImageView imgAvatar;
    JSONObject Foodjs;
    Random random;
    String jsonEvent;
    ActivitiesEvent Activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        Anhxa();
        loadGame();
        addFood();

    }

    private void loadGame() {
        txtName.setText(MainActivity.saveGame.getName());
        txtMoney.setText(MainActivity.saveGame.getMoney() + "K VND");
        txtJob.setText(MainActivity.saveGame.getJob());
        imgAvatar.setImageResource(MainActivity.saveGame.getAvatar());
    }

    private void addFood() {
        arrFood = new ArrayList<>();
        arrFood.add(new Food("Nhà hàng", "1 triệu", R.drawable.restaurant, 1000));
        arrFood.add(new Food("Nước ép trái cây", "20 nghìn", R.drawable.drink, 20));
        arrFood.add(new Food("Trà sữa", "50 nghìn", R.drawable.bubble_tea, 50));
        arrFood.add(new Food("Cà phê", "15 nghìn", R.drawable.coffee, 15));
        arrFood.add(new Food("Rượu", "300 nghìn", R.drawable.alcohol, 300));
        arrFood.add(new Food("Bia", "200 nghìn", R.drawable.beer, 200));
        arrFood.add(new Food("Hamburger", "45 nghìn", R.drawable.hamburger, 45));
        arrFood.add(new Food("Bánh mì", "15 nghìn", R.drawable.bread, 15));
        arrFood.add(new Food("Mỳ", "35 nghìn", R.drawable.noodle, 35));
        arrFood.add(new Food("Trái cây", "60 nghìn", R.drawable.fruits, 60));
        arrFood.add(new Food("Pizza", "150 nghìn", R.drawable.pizza, 150));
        arrFood.add(new Food("Lẩu", "250 nghìn", R.drawable.bibimbap, 250));
        arrFood.add(new Food("Cơm", "25 nghìn", R.drawable.rice_bowl, 25));
        arrFood.add(new Food("Hải sản", "500 nghìn", R.drawable.seafood, 500));
        arrFood.add(new Food("Gà", "150 nghìn", R.drawable.thanksgiving, 150));
        arrFood.add(new Food("Rau củ", "30 nghìn", R.drawable.salad, 30));
        arrFood.add(new Food("Kẹo", "5 nghìn", R.drawable.lollipop, 5));
        arrFood.add(new Food("Thức ăn nhanh", "50 nghìn", R.drawable.fast_food, 50));

        adapter = new FoodAdapter(this, R.layout.food_line, arrFood);
        lvFood.setAdapter(adapter);
        jsonEvent = readEvent();
        Activity = new ActivitiesEvent(jsonEvent ,FoodActivity.this);
        lvFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (arrFood.get(position).getFoodName() == "Nhà hàng"
                        && MainActivity.saveGame.getMoney() >= arrFood.get(0).getPrice()
                        && MainActivity.saveGame.getNhaHang() <3) {
                    try {
                        Activity.CreateDialog("Nhà hàng","Ăn uống");
                        MainActivity.saveGame.saveNhaHang(MainActivity.saveGame.getNhaHang() + 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position ==0 && MainActivity.saveGame.getNhaHang() <3&& MainActivity.saveGame.getMoney() <= arrFood.get(0).getPrice()){
                    createDialogOFM();
                }
                else if(position ==0 && MainActivity.saveGame.getNhaHang() >=3)
                {
                    MainActivity.createNotification(R.drawable.restaurant, "Bạn đã ăn món này quá nhiều", FoodActivity.this);
                }
                if (arrFood.get(position).getFoodName() == "Nước ép trái cây"
                        && MainActivity.saveGame.getMoney() >= arrFood.get(1).getPrice()
                        && MainActivity.saveGame.getNuocEp() <3) {
                    try {
                        Activity.CreateDialog("Nước ép trái cây","Món ăn");
                        MainActivity.saveGame.saveNuocEp(MainActivity.saveGame.getNuocEp() + 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position ==1 && MainActivity.saveGame.getNuocEp() <3&& MainActivity.saveGame.getMoney() <= arrFood.get(1).getPrice()){
                    createDialogOFM();
                }
                else if(position ==1 && MainActivity.saveGame.getNuocEp() >=3)
                {
                    MainActivity.createNotification(R.drawable.drink, "Bạn đã uống nước này quá nhiều", FoodActivity.this);
                }
                if (arrFood.get(position).getFoodName() == "Trà sữa"
                        && MainActivity.saveGame.getMoney() >= arrFood.get(2).getPrice()
                        && MainActivity.saveGame.getTraSua() <3) {
                    try {
                        Activity.CreateDialog("Trà sữa","Món ăn");
                        MainActivity.saveGame.saveTraSua(MainActivity.saveGame.getTraSua() + 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position ==2 && MainActivity.saveGame.getTraSua() <3&& MainActivity.saveGame.getMoney() <= arrFood.get(2).getPrice()){
                    createDialogOFM();
                }
                else if(position ==2 && MainActivity.saveGame.getTraSua() >=3)
                {
                    MainActivity.createNotification(R.drawable.bubble_tea, "Bạn đã uống nước này quá nhiều", FoodActivity.this);
                }
                if (arrFood.get(position).getFoodName() == "Cà phê"
                        && MainActivity.saveGame.getMoney() >= arrFood.get(3).getPrice()
                        && MainActivity.saveGame.getCaPhe() <3) {
                    try {
                        Activity.CreateDialog("Cà phê","Món ăn");
                        MainActivity.saveGame.saveCaPhe(MainActivity.saveGame.getCaPhe() + 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position ==3 && MainActivity.saveGame.getCaPhe() <3&& MainActivity.saveGame.getMoney() <= arrFood.get(3).getPrice()){
                    createDialogOFM();
                }
                else if(position ==3 && MainActivity.saveGame.getCaPhe() >=3)
                {
                    MainActivity.createNotification(R.drawable.coffee, "Bạn đã uống nước này quá nhiều", FoodActivity.this);
                }
                if (arrFood.get(position).getFoodName() == "Rượu"
                        && MainActivity.saveGame.getMoney() >= arrFood.get(4).getPrice()
                        && MainActivity.saveGame.getRuou() <3) {
                    try {
                        Activity.CreateDialog("Rượu","Món ăn");
                        MainActivity.saveGame.saveRuou(MainActivity.saveGame.getRuou() + 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position ==4 && MainActivity.saveGame.getRuou() <3&& MainActivity.saveGame.getMoney() <= arrFood.get(4).getPrice()){
                    createDialogOFM();
                }
                else if(position ==4 && MainActivity.saveGame.getRuou() >=3)
                {
                    MainActivity.createNotification(R.drawable.alcohol, "Bạn đã uống nước này quá nhiều", FoodActivity.this);
                }
                if (arrFood.get(position).getFoodName() == "Bia"
                        && MainActivity.saveGame.getMoney() >= arrFood.get(5).getPrice()
                        && MainActivity.saveGame.getBia() <3) {
                    try {
                        Activity.CreateDialog("Bia","Món ăn");
                        MainActivity.saveGame.saveBia(MainActivity.saveGame.getBia() + 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position ==5 && MainActivity.saveGame.getBia() <3&& MainActivity.saveGame.getMoney() <= arrFood.get(5).getPrice()){
                    createDialogOFM();
                }
                else if(position ==5 && MainActivity.saveGame.getBia() >=3)
                {
                    MainActivity.createNotification(R.drawable.beer, "Bạn đã uống nước này quá nhiều", FoodActivity.this);
                }
                if (arrFood.get(position).getFoodName() == "Hamburger"
                        && MainActivity.saveGame.getMoney() >= arrFood.get(6).getPrice()
                        && MainActivity.saveGame.getHamBurGer() <3) {
                    try {
                        Activity.CreateDialog("Hamburger","Món ăn");
                        MainActivity.saveGame.saveHamBurGer(MainActivity.saveGame.getHamBurGer() + 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position ==6 && MainActivity.saveGame.getHamBurGer() <3&& MainActivity.saveGame.getMoney() <= arrFood.get(6).getPrice()){
                    createDialogOFM();
                }
                else if(position ==6 && MainActivity.saveGame.getHamBurGer() >=3)
                {
                    MainActivity.createNotification(R.drawable.hamburger, "Bạn đã ăn món này quá nhiều", FoodActivity.this);
                }
                if (arrFood.get(position).getFoodName() == "Bánh mì"
                        && MainActivity.saveGame.getMoney() >= arrFood.get(7).getPrice()
                        && MainActivity.saveGame.getBanhMi() <3) {
                    try {
                        Activity.CreateDialog("Bánh mì","Món ăn");
                        MainActivity.saveGame.saveBanhMi(MainActivity.saveGame.getBanhMi() + 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position ==7 && MainActivity.saveGame.getBanhMi() <3&& MainActivity.saveGame.getMoney() <= arrFood.get(7).getPrice()){
                    createDialogOFM();
                }
                else if(position ==7 && MainActivity.saveGame.getBanhMi() >=3)
                {
                    MainActivity.createNotification(R.drawable.bread, "Bạn đã ăn món này quá nhiều", FoodActivity.this);
                }
                if (arrFood.get(position).getFoodName() == "Mỳ"
                        && MainActivity.saveGame.getMoney() >= arrFood.get(8).getPrice()
                        && MainActivity.saveGame.getMy() <3) {
                    try {
                        Activity.CreateDialog("Mỳ","Món ăn");
                        MainActivity.saveGame.saveMy(MainActivity.saveGame.getMy() + 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position ==8 && MainActivity.saveGame.getMy() <3&& MainActivity.saveGame.getMoney() <= arrFood.get(8).getPrice()){
                    createDialogOFM();
                }
                else if(position ==8 && MainActivity.saveGame.getMy() >=3)
                {
                    MainActivity.createNotification(R.drawable.noodle, "Bạn đã ăn món này quá nhiều", FoodActivity.this);
                }
                if (arrFood.get(position).getFoodName() == "Trái cây"
                        && MainActivity.saveGame.getMoney() >= arrFood.get(9).getPrice()
                        && MainActivity.saveGame.getTraiCay() <3) {
                    try {
                        Activity.CreateDialog("Trái cây","Món ăn");
                        MainActivity.saveGame.saveTraiCay(MainActivity.saveGame.getTraiCay() + 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position ==9 && MainActivity.saveGame.getTraiCay() <3&& MainActivity.saveGame.getMoney() <= arrFood.get(9).getPrice()){
                    createDialogOFM();
                }
                else if(position ==9 && MainActivity.saveGame.getTraiCay() >=3)
                {
                    MainActivity.createNotification(R.drawable.fruits, "Bạn đã ăn món này quá nhiều", FoodActivity.this);
                }
                if (arrFood.get(position).getFoodName() == "Pizza"
                        && MainActivity.saveGame.getMoney() >= arrFood.get(10).getPrice()
                        && MainActivity.saveGame.getPizza() <3) {
                    try {
                        Activity.CreateDialog("Pizza","Món ăn");
                        MainActivity.saveGame.savePizza(MainActivity.saveGame.getPizza() + 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position ==10 && MainActivity.saveGame.getPizza() <3&& MainActivity.saveGame.getMoney() <= arrFood.get(10).getPrice()){
                    createDialogOFM();
                }
                else if(position ==10 && MainActivity.saveGame.getPizza() >=3)
                {
                    MainActivity.createNotification(R.drawable.pizza, "Bạn đã ăn món này quá nhiều", FoodActivity.this);
                }
                if (arrFood.get(position).getFoodName() == "Lẩu"
                        && MainActivity.saveGame.getMoney() >= arrFood.get(11).getPrice()
                        && MainActivity.saveGame.getLau() <3) {
                    try {
                        Activity.CreateDialog("Lẩu","Món ăn");
                        MainActivity.saveGame.saveLau(MainActivity.saveGame.getLau() + 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position ==11 && MainActivity.saveGame.getLau() <3&& MainActivity.saveGame.getMoney() <= arrFood.get(11).getPrice()){
                    createDialogOFM();
                }
                else if(position ==11 && MainActivity.saveGame.getLau() >=3)
                {
                    MainActivity.createNotification(R.drawable.bibimbap, "Bạn đã ăn món này quá nhiều", FoodActivity.this);
                }
                if (arrFood.get(position).getFoodName() == "Cơm"
                        && MainActivity.saveGame.getMoney() >= arrFood.get(12).getPrice()
                        && MainActivity.saveGame.getCom() <3) {
                    try {
                        Activity.CreateDialog("Cơm","Món ăn");
                        MainActivity.saveGame.saveCom(MainActivity.saveGame.getCom() + 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position ==12 && MainActivity.saveGame.getCom() <3&& MainActivity.saveGame.getMoney() <= arrFood.get(12).getPrice()){
                    createDialogOFM();
                }
                else if(position ==12 && MainActivity.saveGame.getCom() >=3)
                {
                    MainActivity.createNotification(R.drawable.rice_bowl, "Bạn đã ăn món này quá nhiều", FoodActivity.this);
                }
                if (arrFood.get(position).getFoodName() == "Hải sản"
                        && MainActivity.saveGame.getMoney() >= arrFood.get(13).getPrice()
                        && MainActivity.saveGame.getHaiSan() <3) {
                    try {
                        Activity.CreateDialog("Hải sản","Món ăn");
                        MainActivity.saveGame.saveHaiSan(MainActivity.saveGame.getHaiSan() + 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position ==13 && MainActivity.saveGame.getHaiSan() <3&& MainActivity.saveGame.getMoney() <= arrFood.get(13).getPrice()){
                    createDialogOFM();
                }
                else if(position ==13 && MainActivity.saveGame.getHaiSan() >=3)
                {
                    MainActivity.createNotification(R.drawable.seafood, "Bạn đã ăn món này quá nhiều", FoodActivity.this);
                }
                if (arrFood.get(position).getFoodName() == "Gà"
                        && MainActivity.saveGame.getMoney() >= arrFood.get(14).getPrice()
                        && MainActivity.saveGame.getGa() <3) {
                    try {
                        Activity.CreateDialog("Gà","Món ăn");
                        MainActivity.saveGame.saveGa(MainActivity.saveGame.getGa() + 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position ==14 && MainActivity.saveGame.getGa() <3&& MainActivity.saveGame.getMoney() <= arrFood.get(14).getPrice()){
                    createDialogOFM();
                }
                else if(position ==14 && MainActivity.saveGame.getGa() >=3)
                {
                    MainActivity.createNotification(R.drawable.thanksgiving, "Bạn đã ăn món này quá nhiều", FoodActivity.this);
                }
                if (arrFood.get(position).getFoodName() == "Rau củ"
                        && MainActivity.saveGame.getMoney() >= arrFood.get(15).getPrice()
                        && MainActivity.saveGame.getRauCu() <3) {
                    try {
                        Activity.CreateDialog("Rau củ","Món ăn");
                        MainActivity.saveGame.saveRauCu(MainActivity.saveGame.getRauCu() + 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position ==15 && MainActivity.saveGame.getRauCu() <3&& MainActivity.saveGame.getMoney() <= arrFood.get(15).getPrice()){
                    createDialogOFM();
                }
                else if(position ==15 && MainActivity.saveGame.getRauCu() >=3)
                {
                    MainActivity.createNotification(R.drawable.salad, "Bạn đã ăn món này quá nhiều", FoodActivity.this);
                }
                if (arrFood.get(position).getFoodName() == "Kẹo"
                        && MainActivity.saveGame.getMoney() >= arrFood.get(16).getPrice()
                        && MainActivity.saveGame.getKeo() <3) {
                    try {
                        Activity.CreateDialog("Kẹo","Món ăn");
                        MainActivity.saveGame.saveKeo(MainActivity.saveGame.getKeo() + 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position ==16 && MainActivity.saveGame.getKeo() <3&& MainActivity.saveGame.getMoney() <= arrFood.get(16).getPrice()){
                    createDialogOFM();
                }
                else if(position ==16 && MainActivity.saveGame.getKeo() >=3)
                {
                    MainActivity.createNotification(R.drawable.lollipop, "Bạn đã ăn món này quá nhiều", FoodActivity.this);
                }
                if (arrFood.get(position).getFoodName() == "Thức ăn nhanh"
                        && MainActivity.saveGame.getMoney() >= arrFood.get(17).getPrice()
                        && MainActivity.saveGame.getFastFood() <3) {
                    try {
                        Activity.CreateDialog("Thức ăn nhanh","Món ăn");
                        MainActivity.saveGame.saveFastFood(MainActivity.saveGame.getFastFood() + 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position ==17 && MainActivity.saveGame.getFastFood() <3&& MainActivity.saveGame.getMoney() <= arrFood.get(17).getPrice()){
                    createDialogOFM();
                }
                else if(position ==17 && MainActivity.saveGame.getFastFood() >=3)
                {
                    MainActivity.createNotification(R.drawable.fast_food, "Bạn đã ăn món này quá nhiều", FoodActivity.this);
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
    private void Anhxa() {
        lvFood = (ListView) findViewById(R.id.listviewFood);
        txtName = findViewById(R.id.textviewName);
        txtJob = findViewById(R.id.textviewJob);
        txtMoney = findViewById(R.id.textviewMoney);
        imgAvatar = findViewById(R.id.imageAvatar);
    }
    String readEvent()
    {
        jsonEvent = null;
        try {
            InputStream inputStream = getAssets().open("food.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonEvent = new String(buffer, "UTF-8");
            Foodjs = new JSONObject(jsonEvent);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return jsonEvent;
    }
    public void gotoMainMenu(View view)
    {
        //startActivity(new Intent(FoodActivity.this, HoatDong.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
        onBackPressed();
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadGame();
    }
}
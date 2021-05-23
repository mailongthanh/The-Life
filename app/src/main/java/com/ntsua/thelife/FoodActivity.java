package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity {

    ListView lvFood;
    FoodAdapter adapter;
    ArrayList<Food> arrFood;
    TextView txtName, txtJob, txtMoney;

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
        txtMoney.setText(MainActivity.saveGame.getMoney() + "VND");
        txtJob.setText(MainActivity.saveGame.getJob());
    }

    private void addFood() {
        arrFood = new ArrayList<>();
        arrFood.add(new Food("Nhà hàng", "...k", R.drawable.restaurant, 0));
        arrFood.add(new Food("Nước ép trái cây", "...k", R.drawable.drink, 0));
        arrFood.add(new Food("Trà sữa", "...k", R.drawable.bubble_tea, 0));
        arrFood.add(new Food("Cà phê", "...k", R.drawable.coffee, 0));
        arrFood.add(new Food("Rượu", "...k", R.drawable.alcohol, 0));
        arrFood.add(new Food("Bia", "...k", R.drawable.beer, 0));
        arrFood.add(new Food("Hamburger", "...k", R.drawable.hamburger, 0));
        arrFood.add(new Food("Bánh mì", "...k", R.drawable.bread, 0));
        arrFood.add(new Food("Mỳ", "...k", R.drawable.noodle, 0));
        arrFood.add(new Food("Trái cây", "...k", R.drawable.fruits, 0));
        arrFood.add(new Food("Pizza", "...k", R.drawable.pizza, 0));
        arrFood.add(new Food("Lẩu", "...k", R.drawable.bibimbap, 0));
        arrFood.add(new Food("Cơm", "...k", R.drawable.rice_bowl, 0));
        arrFood.add(new Food("Hải sản", "...k", R.drawable.seafood, 0));
        arrFood.add(new Food("Gà", "...k", R.drawable.thanksgiving, 0));
        arrFood.add(new Food("Rau củ", "...k", R.drawable.salad, 0));
        arrFood.add(new Food("Kẹo", "...k", R.drawable.lollipop, 0));
        arrFood.add(new Food("Thức ăn nhanh", "...k", R.drawable.fast_food, 0));

        adapter = new FoodAdapter(this, R.layout.food_line, arrFood);
        lvFood.setAdapter(adapter);

        lvFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(FoodActivity.this, "Here", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void Anhxa() {
        lvFood = (ListView) findViewById(R.id.listviewFood);
        txtName = findViewById(R.id.textviewName);
        txtJob = findViewById(R.id.textviewJob);
        txtMoney = findViewById(R.id.textviewMoney);
    }

    public void gotoMainMenu(View view)
    {
        //startActivity(new Intent(FoodActivity.this, HoatDong.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
        onBackPressed();
    }
}
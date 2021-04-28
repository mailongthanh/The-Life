package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity {

    ListView lvFood;
    FoodAdapter adapter;
    ArrayList<Food> arrFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        Anhxa();
        addFood();

    }

    private void addFood() {
        arrFood = new ArrayList<>();
        arrFood.add(new Food("Nhà hàng", "...k", R.drawable.restaurant));
        arrFood.add(new Food("Nước ép trái cây", "...k", R.drawable.drink));
        arrFood.add(new Food("Trà sữa", "...k", R.drawable.bubble_tea));
        arrFood.add(new Food("Cà phê", "...k", R.drawable.coffee));
        arrFood.add(new Food("Rượu", "...k", R.drawable.alcohol));
        arrFood.add(new Food("Bia", "...k", R.drawable.beer));
        arrFood.add(new Food("Hamburger", "...k", R.drawable.hamburger));
        arrFood.add(new Food("Bánh mì", "...k", R.drawable.bread));
        arrFood.add(new Food("Mỳ", "...k", R.drawable.noodle));
        arrFood.add(new Food("Trái cây", "...k", R.drawable.fruits));
        arrFood.add(new Food("Pizza", "...k", R.drawable.pizza));
        arrFood.add(new Food("Lẩu", "...k", R.drawable.bibimbap));
        arrFood.add(new Food("Cơm", "...k", R.drawable.rice_bowl));
        arrFood.add(new Food("Hải sản", "...k", R.drawable.seafood));
        arrFood.add(new Food("Gà", "...k", R.drawable.thanksgiving));
        arrFood.add(new Food("Rau củ", "...k", R.drawable.salad));
        arrFood.add(new Food("Kẹo", "...k", R.drawable.lollipop));
        arrFood.add(new Food("Thức ăn nhanh", "...k", R.drawable.fast_food));

        adapter = new FoodAdapter(this, R.layout.food_line, arrFood);
        lvFood.setAdapter(adapter);
    }

    private void Anhxa() {
        lvFood = (ListView) findViewById(R.id.listviewFood);
        TextView txtTilte = (TextView) findViewById(R.id.textviewTitle);
    }

    public void gotoMainMenu(View view)
    {
        startActivity(new Intent(FoodActivity.this, MainActivity.class));
    }
}
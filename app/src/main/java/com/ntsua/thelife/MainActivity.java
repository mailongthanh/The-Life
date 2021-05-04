package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btnActivity, btnRelationship;
    TextView txtContent;
    SharedPreferences preferences;
    SaveGame saveGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        //Test
        try {
            init("Thành", "vn");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //SpannableString s = new SpannableString(txtContent.getText().toString());
        //s.setSpan(new RelativeSizeSpan(2f), 0, 3, 0);
        //txtContent.setText(saveGame.getDetailActivity());
        //saveGame.saveDetailActivity(txtContent.getText().toString());

    }

    private void AnhXa() {
        btnRelationship = (Button) findViewById(R.id.buttonRelationship);
        btnActivity = (Button) findViewById(R.id.buttonActivity);
        txtContent = findViewById(R.id.textViewDetail);

        preferences = getSharedPreferences("data", MODE_PRIVATE);
        saveGame = new SaveGame(preferences);
    }

    public void gotoActivity(View view)
    {
        startActivity(new Intent(MainActivity.this, SportActivity.class));
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
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        txtContent.setText(s);
    }
}
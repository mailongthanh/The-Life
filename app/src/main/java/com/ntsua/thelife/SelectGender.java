package com.ntsua.thelife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SelectGender extends AppCompatActivity {
    private Button btn_back;
    private Button btn_SelectNam;
    private Button btn_SelectNu;
    private EditText edtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectgender);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectGender.this, CreateName.class);
                startActivity(intent);
            }
        });
        btn_SelectNam = (Button) findViewById(R.id.btn_SelectNam);
        btn_SelectNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectGender.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btn_SelectNu = (Button) findViewById(R.id.btn_SelectNu);
        btn_SelectNu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectGender.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}


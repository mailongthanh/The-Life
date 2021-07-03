package com.ntsua.thelife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SelectGender extends AppCompatActivity {
    private Button btn_back;
    private EditText edtName;
    private Button btn_SelectNam;
    private Button btn_SelectNu;
    boolean isBoy = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectgender);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_SelectNam = (Button) findViewById(R.id.btn_SelectNam);
        btn_SelectNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isBoy = true;
                gotoMain();
            }
        });
        btn_SelectNu = (Button) findViewById(R.id.btn_SelectNu);
        btn_SelectNu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               isBoy = false;
               gotoMain();
            }
        });
    }

    void gotoMain()
    {
        Intent intent = new Intent();
        intent.putExtra("gender", isBoy);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}


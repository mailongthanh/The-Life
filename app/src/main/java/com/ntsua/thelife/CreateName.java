package com.ntsua.thelife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CreateName extends AppCompatActivity {
    private Button btn_Create;
    private EditText edtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createname);
        btn_Create = (Button) findViewById(R.id.btn_create);
        btn_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateName.this, SelectGender.class);
                startActivity(intent);
            }
        });
    }
}

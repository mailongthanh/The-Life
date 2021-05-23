package com.ntsua.thelife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CreateName extends AppCompatActivity {
    private Button btn_Create;
    private EditText edtName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createname);

        edtName = findViewById(R.id.editextName);

        btn_Create = (Button) findViewById(R.id.btn_create);
        btn_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtName.getText().toString().equals(""))
                {
                    Toast.makeText(CreateName.this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(CreateName.this, SelectGender.class);
                startActivityForResult(intent, 123);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 123 && resultCode == RESULT_OK && data != null)
        {
            String gender = data.getStringExtra("gender");
            Intent intent = new Intent();
            intent.putExtra("gender", gender);

            String name = edtName.getText().toString();
            intent.putExtra("name", name);

            setResult(RESULT_OK, intent);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}

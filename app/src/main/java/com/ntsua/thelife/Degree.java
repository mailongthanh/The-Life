package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class Degree extends AppCompatActivity {

    ListView lvDegree;
    FoodAdapter adapter;
    ArrayList<Food> arrDegree;
    TextView txtName, txtJob, txtMoney;
    JSONObject jsonDegree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_degree);

        txtName = findViewById(R.id.textviewName);
        txtJob = findViewById(R.id.textviewJob);
        txtMoney = findViewById(R.id.textviewMoney);
        loadGame();
        try {
            readDegree();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        lvDegree = (ListView) findViewById((R.id.listviewDegree));
        arrDegree = new ArrayList<>();
        arrDegree.add(new Food("Bằng tiếng anh", "5 triệu", R.drawable.book, 5000));
        arrDegree.add(new Food("Bằng lái xe", "600 nghìn", R.drawable.motorcycle, 600));
        adapter = new FoodAdapter(this, R.layout.food_line, arrDegree);
        lvDegree.setAdapter(adapter);

        lvDegree.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MainActivity.saveGame.getMoney() < arrDegree.get(position).getPrice())
                {
                    MainActivity.createNotification(R.drawable.money, "Chả đủ tiền, bạn bị viêm màng túi rồi", Degree.this);
                    return;
                }
                //Toast.makeText(Degree.this, MainActivity.saveGame.getMoney() + " - " + arrDegree.get(position).getFoodName(), Toast.LENGTH_SHORT).show();
                switch (position)
                {
                    case 0:
                        if (MainActivity.saveGame.getEnglish())
                        {
                            MainActivity.createNotification(R.drawable.cancel, "Bạn đã có bằng tiếng anh rồi, không cần phải thi lại đâu", Degree.this);
                        } else {
                            try {
                                //Toast.makeText(Degree.this, "here", Toast.LENGTH_SHORT).show();
                                JSONArray arrEnglish = jsonDegree.getJSONArray("english");
                                JSONArray arrQuestion = chooseQuestion(arrEnglish);
                                dialogEvent(arrQuestion, 0, "english");
                                MainActivity.saveGame.saveMoney(MainActivity.saveGame.getMoney() - arrDegree.get(position).getPrice());
                                txtMoney.setText(MainActivity.saveGame.getMoney() + " VND");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 1:
                        if (MainActivity.saveGame.getDriving())
                        {
                            MainActivity.createNotification(R.drawable.cancel, "Bạn đã có bằng lái xe rồi, không cần phải thi lại đâu", Degree.this);
                        } else {
                            try {
                                //Toast.makeText(Degree.this, "here", Toast.LENGTH_SHORT).show();
                                JSONArray arrDriving = jsonDegree.getJSONArray("driving");
                                JSONArray arrQuestion = chooseQuestion(arrDriving);
                                dialogEvent(arrQuestion, 0, "driving");
                                MainActivity.saveGame.saveMoney(MainActivity.saveGame.getMoney() - arrDegree.get(position).getPrice());
                                txtMoney.setText(MainActivity.saveGame.getMoney() + " VND");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                }
            }
        });
    }

    void dialogEvent(JSONArray arrQuestion, int index, String field) throws JSONException
    {
        if (index == 2)
        {
            dialogResult(true, field);
            return;
        }

        JSONObject object = arrQuestion.getJSONObject(index);
        //Tao dialog va them cac button lua chon vao dialog
        Dialog dialog = createDialog(object.getString("question"));
        int correctAnswer = object.getInt("correct");
        LinearLayout dialogCustom = dialog.findViewById(R.id.dialog_event);
        JSONArray arrSelect = object.getJSONArray("answers");
        for (int i = 0; i < arrSelect.length(); i++) {
            Button btn = addButton(dialogCustom, arrSelect.getString(i));
            int finalI = i;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalI == correctAnswer) {
                        try {
                            dialogEvent(arrQuestion, index + 1, field);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else dialogResult(false, field);
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }

    void dialogResult(boolean result, String field)
    {
        if (result) {
            MainActivity.createNotification(R.drawable.holding_hands, "Bạn xuất sắc quá đi mất, trả lời đúng tất cả. Chúc mừng đã thi đạt", Degree.this);
            switch (field)
            {
                case "english":
                    MainActivity.saveGame.saveEnglish(true);
                    break;
                case "driving":
                    MainActivity.saveGame.saveDriving(true);
                    break;
            }
        }
        else MainActivity.createNotification(R.drawable.cancel, "Trả lời sai bét, thi trượt mất rồi", Degree.this);
    }

    Dialog createDialog(String event){
        //Dinh dang dialog
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_event);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogEventAnimation;
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK)
                {
                    return true;
                }
                return false;
            }
        });

        //Anh xa cac phan tu trong dialog
        TextView txtTitle = dialog.findViewById(R.id.textviewDialogEventTitle);
        TextView txtContent = dialog.findViewById(R.id.textviewDialogEventContent);

        //Gan gia tri vao view
        txtContent.setText(event);
        txtTitle.setText("Câu hỏi");

        return dialog;
    }

    JSONArray chooseQuestion(JSONArray arrEnglish) throws JSONException {
        JSONArray arrQuestion = new JSONArray();
        Random random = new Random();
        int first = random.nextInt(arrEnglish.length());

        arrQuestion.put(arrEnglish.getJSONObject(first));

        int second = first;
        while (first == second)
        {
            second = random.nextInt(arrEnglish.length());
        }
        arrQuestion.put(arrEnglish.getJSONObject(second));

        return arrQuestion;
    }

    @SuppressLint("ResourceAsColor")
    Button addButton(LinearLayout dialogCustom, String text)
    {
        Button btn = new Button(dialogCustom.getContext());
        btn.setText(text);
        btn.setTextSize(15);
        btn.setBackgroundResource(R.drawable.custom_dialog);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(800, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 10;
        params.topMargin = 10;
        params.gravity = Gravity.CENTER_HORIZONTAL;
        btn.setLayoutParams(params);
        btn.setTextColor( Color.argb(255,16,54,103));
        dialogCustom.addView(btn);

        return btn;
    }


    private void loadGame() {
        txtName.setText(MainActivity.saveGame.getName());
        txtMoney.setText(MainActivity.saveGame.getMoney() + "VND");
        txtJob.setText(MainActivity.saveGame.getJob());
    }

    public void gotoMainMenu(View view)
    {
        //startActivity(new Intent(Degree.this, HoatDong.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
        onBackPressed();
    }

    void readDegree() throws JSONException {
        String jsonEvent = null;
        try {
            InputStream inputStream = getAssets().open("degree.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonEvent = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        jsonDegree = new JSONObject(jsonEvent);
    }
}
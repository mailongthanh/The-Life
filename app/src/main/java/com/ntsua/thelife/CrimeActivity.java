package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
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

import static com.ntsua.thelife.MainActivity.saveGame;

public class CrimeActivity extends AppCompatActivity {

    ListView lvCrime;
    CrimeAdapter adapter;
    ArrayList<Food> arrCrime;
    TextView txtName, txtJob, txtMoney;
    ImageView imgAvatar;
    JSONObject JsonCrime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

        txtName = findViewById(R.id.textviewName);
        txtJob = findViewById(R.id.textviewJob);
        txtMoney = findViewById(R.id.textviewMoney);
        imgAvatar = findViewById(R.id.imageAvatar);
        loadGame();

        try {
            readEvent();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        lvCrime = findViewById(R.id.listviewCrime);
        arrCrime = new ArrayList<>();

        arrCrime.add(new Food("Móc túi", "đi Loot", R.drawable.pickpocket, 0));
        arrCrime.add(new Food("Cướp cửa hàng", "$$$$$$$$", R.drawable.robber, 0));
        arrCrime.add(new Food("Buôn rau", "kinh doanh rau muống đột biến", R.drawable.cannabis, 0));
        arrCrime.add(new Food("Hack", "Wảning", R.drawable.hacker, 0));

        adapter = new CrimeAdapter(this, R.layout.crime, arrCrime);
        lvCrime.setAdapter(adapter);

        lvCrime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(arrCrime.get(position).getFoodName()=="Móc túi") {
                    if (saveGame.getCrime() < 3) {
                        try {
                            GetEventCrime("Móc túi");
                            saveGame.saveCrime(saveGame.getCrime() +1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        MainActivity.createNotification(R.drawable.police,"Hoạt động gần đây của bạn đã khiến cảnh sát chú ý tới, có lẽ nên ngừng hành động một thời gian",CrimeActivity.this);
                    }
                }
                if(arrCrime.get(position).getFoodName() == "Cướp cửa hàng"){
                    if(saveGame.getCrime() <3){
                        try {
                            GetEventCrime("Cướp cửa hàng");
                            saveGame.saveCrime(saveGame.getCrime()+1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else{
                        MainActivity.createNotification(R.drawable.police,"Hoạt động gần đây của bạn đã khiến cảnh sát chú ý tới, có lẽ nên ngừng hành động một thời gian",CrimeActivity.this);
                    }
                }
                if(arrCrime.get(position).getFoodName() == "Buôn rau"){
                    if(saveGame.getCrime()<3){
                        try {
                            GetEventCrime("Buôn rau");
                            saveGame.saveCrime(saveGame.getCrime()+1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else{
                        MainActivity.createNotification(R.drawable.police,"Hoạt động gần đây của bạn đã khiến cảnh sát chú ý tới, có lẽ nên ngừng hành động một thời gian",CrimeActivity.this);
                    }
                }
                if(arrCrime.get(position).getFoodName() == "Hack"){
                    if(saveGame.getCrime()<3){
                        try {
                            GetEventCrime("Hack");
                            saveGame.saveCrime(saveGame.getCrime()+1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else{
                        MainActivity.createNotification(R.drawable.police,"Hoạt động gần đây của bạn đã khiến cảnh sát chú ý tới, có lẽ nên ngừng hành động một thời gian",CrimeActivity.this);
                    }
                }
            }
        });
    }

    private void loadGame() {
        txtName.setText(saveGame.getName());
        txtMoney.setText(saveGame.getMoney() + "VND");
        txtJob.setText(saveGame.getJob());
        imgAvatar.setImageResource(saveGame.getAvatar());
    }

    public void gotoMainMenu(View view)
    {
        //startActivity(new Intent(CrimeActivity.this, HoatDong.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
        onBackPressed();
    }

    void readEvent() throws JSONException {
        String jsonEvent = null;
        try {
            InputStream inputStream = getAssets().open("Crime_event.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonEvent = new String(buffer, "UTF-8");
            JsonCrime = new JSONObject(jsonEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void GetEventCrime(String name) throws JSONException
    {
        JSONArray arrCrime = JsonCrime.getJSONArray(name);
        Random random= new Random();

        final JSONObject[] objects = {arrCrime.getJSONObject(random.nextInt(arrCrime.length()))};

        final String[] event = {objects[0].getString("event")};

        final boolean[] isSelection = {objects[0].getBoolean("selection")};

        if(!isSelection[0])
        {
            dialogEventResult(objects[0], name);
        }
        else dialogJobEventWithAsset(objects[0],name);

    }

    void dialogJobEventWithAsset(JSONObject jsonObject,String title) throws JSONException {
        //Tao dialog asset
        Dialog dialog = createAssetDialog();

        //Anh xa
        TextView txtContent = dialog.findViewById(R.id.textviewAssetContent);
        ImageView imgAsset = dialog.findViewById(R.id.imageviewAsset);
        TextView txtAssetName = dialog.findViewById(R.id.textviewAsset);
        Button btnAccept = dialog.findViewById(R.id.buttonAssetAccept);
        Button btnCancel = dialog.findViewById(R.id.buttonAssetCancel);
        TextView txtTitle = dialog.findViewById(R.id.textviewDialogAssetTitle);

        int imageID = getResources().getIdentifier(jsonObject.getString("asset"), "drawable", this.getPackageName());
        //Gan gia tri
        txtTitle.setText(title);
        txtContent.setText(jsonObject.getString("event"));
        imgAsset.setImageResource(imageID);
        txtAssetName.setText(jsonObject.getString("name"));
        JSONArray arrSelect = jsonObject.getJSONArray("select");

        boolean isOwn = false;
        ArrayList<Food> arrProduct = saveGame.getAsset();
        if (arrProduct != null) {
            for (int i = 0; i < arrProduct.size(); i++) {
                if (imageID == arrProduct.get(i).getImage()) //Trung ID hinh la so huu
                {
                    isOwn = true;
                    break;
                }
            }
        }
        if (!isOwn)
            btnAccept.setBackgroundResource(R.drawable.list_item_unable);
        else btnAccept.setBackgroundResource(R.drawable.custom_button_menu);

        boolean finalIsOwn = isOwn;
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!finalIsOwn)
                    Toast.makeText(CrimeActivity.this, "Bạn đã sở hữu mòn đồ này đâu mà đồng ý!!!", Toast.LENGTH_SHORT).show();
                else {
                    try {
                        JSONArray arr = arrSelect.getJSONArray(0);
                        JSONObject jsSelection = arr.getJSONObject(new Random().nextInt(arr.length()));
                        dialogEventResult(jsSelection,title);
                        dialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONArray arr = null;
                try {
                    arr = arrSelect.getJSONArray(1);
                    JSONObject jsSelection = arr.getJSONObject(new Random().nextInt(arr.length()));
                    dialogEventResult(jsSelection, title);
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        dialog.show();
    }

    Dialog createAssetDialog(){
        //Dinh dang dialog
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_job_asset);
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
        return dialog;
    }

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

    void dialogEventResult(JSONObject object, String title) throws JSONException {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_event_result);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //Ánh xạ các view
        LinearLayout DialogResult = dialog.findViewById(R.id.dialog_event_result);
        Button btnOke = dialog.findViewById(R.id.buttonDialogEventOke);
        TextView txtTitle = dialog.findViewById(R.id.textviewDialogEventTitle);
        TextView txtContent = dialog.findViewById(R.id.textviewDialogEventContent);
        TextView txtHappy = dialog.findViewById(R.id.textviewResultHappy);
        TextView txtHealth = dialog.findViewById(R.id.textviewResultHealth);
        TextView txtSmart = dialog.findViewById(R.id.textviewResultSmart);
        TextView txtAppearance = dialog.findViewById(R.id.textviewResultAppearance);
        TextView txtAssets = dialog.findViewById(R.id.textviewResultMoney);

        //Gán giá trị
        txtTitle.setText(title);
        String event = object.getString("event");
        txtContent.setText(event);


        int happy = MainActivity.saveGame.getHappy();
        int health = MainActivity.saveGame.getHealth();
        int smart = MainActivity.saveGame.getSmart();
        int appearance = MainActivity.saveGame.getAppearance();

        int value = 0;
        value = object.getInt("happy");
        if (value == 0) {
            DialogResult.removeView(dialog.findViewById(R.id.linearHappy));
        } else {
            toString(value, txtHappy);
            happy += value;
        }

        value = object.getInt("health");
        if (value == 0) {
            DialogResult.removeView(dialog.findViewById(R.id.linearHealth));
        } else {
            toString(value, txtHealth);
            health += value;
        }

        value = object.getInt("smart");
        if (value == 0) {
            DialogResult.removeView(dialog.findViewById(R.id.linearSmart));
        } else {
            toString(value, txtSmart);
            smart += value;
        }

        value = object.getInt("appearance");
        if (value == 0) {
            DialogResult.removeView(dialog.findViewById(R.id.linearAppearance));
        } else {
            toString(value, txtAppearance);
            appearance += value;
        }

        value = object.getInt("assets");
        if (value == 0) {
            DialogResult.removeView(dialog.findViewById(R.id.linearMoney));
        } else {
            String money = String.format("%,d", value * 1000);
            if (value > 0)
                money = "+ " + money;
            else {
                money = "- " + money;
                txtAssets.setTextColor(Color.RED);
            }
            txtAssets.setText(money);
            MainActivity.saveGame.saveMoney(MainActivity.saveGame.getMoney() + value);
            txtAssets.setText("$" + value);
        }

        MainActivity.saveGame.savePlayerInfo(happy, health, smart, appearance);
        String contentHTML = MainActivity.saveGame.getDetailActivity();
        contentHTML += event + "<br>";
        MainActivity.saveGame.saveDetailActivity(contentHTML);

        btnOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cap nhat lai tien hien thi
                Activity activity = (Activity) CrimeActivity.this;
                TextView txtMoney = activity.findViewById(R.id.textviewMoney);
                txtMoney.setText(MainActivity.saveGame.getMoney() + " VND");
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
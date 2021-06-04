package com.ntsua.thelife;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.LauncherActivity;
import android.app.UiAutomation;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnActivity, btnRelationship, btnWork, btnAssets;;
    ImageButton ibtnAddAge;
    ProgressBar prbHappy, prbHealth, prbSmart, prbAppearance;
    ScrollView scrollView;
    TextView txtContent, txtHappy, txtHealth, txtSmart, txtAppearance, txtMoney, txtName, txtJob;
    SharedPreferences preferences;
    static public SaveGame saveGame;
    static public  ArrayList<QuanHe> arrFriend;
    ArrayList<QuanHe> arrRelationship;
    JSONArray arrJsonAge;
    JSONObject jsonResult, jsonAllJob, jsonJob;
    String contentHtml;
    int money;
    long currentTime = 0;
    Toast backToast;

    int REQUEST_CODE_INIT = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        //Test
        //startActivityForResult(new Intent(MainActivity.this,  CreateName.class), REQUEST_CODE_INIT);

        try {
            if (saveGame.getDetailActivity().equals("")) {
                startActivityForResult(new Intent(MainActivity.this, CreateName.class), REQUEST_CODE_INIT);
            }
            else {
                loadGame();
                readEvent();
                //Toast.makeText(this, "hể", Toast.LENGTH_SHORT).show();
                readJob();
                readFriend();
                changeWork();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ibtnAddAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addAge();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        btnWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    doWork();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        //createNotification(R.drawable.holding_hands, "Bạn đã có bằng tiếng anh rồi Bạn đã có bằng tiếng anh rồi Bạn đã có bằng tiếng anh rồi", this);

        //dialogUniversity();

    }

    void dialogJob(JSONArray arrJob) throws JSONException {
        Dialog dialog = createDialog("Làm việc", "Làm, làm nữa, làm mãi");
        LinearLayout dialogCustom = dialog.findViewById(R.id.dialog_event);


        for (int i=0; i<arrJob.length(); i++)
        {
            JSONObject object = arrJob.getJSONObject(i);
            Button btn = addButton(dialogCustom, object.getString("content"));
            int finalI = i;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        JSONArray array = object.getJSONArray("event");
                        jsonResult = array.getJSONObject(new Random().nextInt(array.length()));
                        dialog.dismiss();
                        //saveGame.saveSkill(saveGame.getSkill() + jsonResult.getInt("skill"));
                        //Toast.makeText(MainActivity.this, "" + saveGame.getSkill(), Toast.LENGTH_SHORT).show();
                        dialogEventResult(object.getString("content"), false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        dialog.show();
    }

    void doWork() throws JSONException {
        JSONArray arrJob = jsonJob.getJSONArray("work");
        dialogJob(arrJob);
//        if (jsonJob == null)
//            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
    }

    void addAge() throws JSONException {
        //them tuoi
        int age = saveGame.getAge() + 1;

        if (age == 18)
        {
            dialogUniversity();
        }

        //lay su kien tuoi
        JSONArray arrAge = arrJsonAge.getJSONArray(age);
        Random random = new Random();
        //Toast.makeText(this, arrAge.length() + " - " + age, Toast.LENGTH_SHORT).show();
        final JSONObject[] object = {arrAge.getJSONObject(random.nextInt(arrAge.length()))};

        final String[] event = {object[0].getString("event")};
        String title = object[0].getString("title");

        //Kiem tra su kien co su lua chon hay khong
        final boolean[] isSelection = {object[0].getBoolean("selection")};

        //Tao dialog hien thi su kien
        if (isSelection[0])
            dialogEvent(object, isSelection, title, event);
        else {
            jsonResult = object[0];
            addAgeHTML(age);
            dialogEventResult(title, true);
        }
    }

    void dialogEvent(JSONObject[] object, boolean[] isSelection, String title, String[] event) throws JSONException
    {
        jsonResult = object[0];
        if (!isSelection[0]) {
            JSONArray arr = jsonResult.getJSONArray("event");
            jsonResult = arr.getJSONObject(new Random().nextInt(arr.length()));
            int age = saveGame.getAge() + 1;
            addAgeHTML(age);
            //Tao dialog hien thi ket qua cua event
            dialogEventResult(title, true);
            return;
        }


        //Them thong tin da choi vao textview
        //contentHtml += event[0] + "<br>";

        //Tao dialog va them cac button lua chon vao dialog
        Dialog dialog = createDialog(title, event[0]);
        LinearLayout dialogCustom = dialog.findViewById(R.id.dialog_event);
        JSONArray arrSelect = object[0].getJSONArray("select");
        for (int i = 0; i < arrSelect.length(); i++) {
            JSONObject objectSelect = arrSelect.getJSONObject(i);
            Button btn = addButton(dialogCustom, objectSelect.getString("content"));
            int finalI = i;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        object[0] = arrSelect.getJSONObject(finalI);
                        isSelection[0] = object[0].getBoolean("selection");
                        event[0] = object[0].getString("event");
                        dialog.dismiss();
                        dialogEvent(object, isSelection, title, event);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        dialog.show();
    }

    void dialogEventResult(String title, boolean isAgeEvent) throws JSONException {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_event_result);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogResultAnimation;


        //Lay gia tri gan vao dialog
        LinearLayout dialogResult = dialog.findViewById(R.id.dialog_event_result);
        Button btnOke = dialog.findViewById(R.id.buttonDialogEventOke);
        TextView txtTitle = dialog.findViewById(R.id.textviewDialogEventTitle);
        TextView txtContent= dialog.findViewById(R.id.textviewDialogEventContent);
        TextView txtHappy = dialog.findViewById(R.id.textviewResultHappy);
        TextView txtHealth = dialog.findViewById(R.id.textviewResultHealth);
        TextView txtSmart= dialog.findViewById(R.id.textviewResultSmart);
        TextView txtAppearance = dialog.findViewById(R.id.textviewResultAppearance);
        TextView txtAssets = dialog.findViewById(R.id.textviewResultMoney);

        txtTitle.setText(title);
        txtContent.setText(jsonResult.getString("event"));
        contentHtml += txtContent.getText().toString() + "<br>";



        int value = 0;
        value = jsonResult.getInt("happy");
        if (value == 0)
        {
            dialogResult.removeView(dialog.findViewById(R.id.linearHappy));
        } else {
            toString(value, txtHappy);
            prbHappy.setProgress(prbHappy.getProgress() + value);
            this.txtHappy.setText(prbHappy.getProgress() + "%");
        }
        value = jsonResult.getInt("health");
        if (value == 0)
        {
            dialogResult.removeView(dialog.findViewById(R.id.linearHealth));
        } else {
            toString(value, txtHealth);
            prbHealth.setProgress(prbHealth.getProgress() + value);
            this.txtHealth.setText(prbHealth.getProgress() + "%");
        }
        value = jsonResult.getInt("smart");
        if (value == 0)
        {
            dialogResult.removeView(dialog.findViewById(R.id.linearSmart));
        } else {
            toString(value, txtSmart);
            prbSmart.setProgress(prbSmart.getProgress() + value);
            this.txtSmart.setText(prbSmart.getProgress() + "%");
        }
        value = jsonResult.getInt("appearance");
        if (value == 0)
        {
            dialogResult.removeView(dialog.findViewById(R.id.linearAppearance));
        } else {
            toString(value, txtAppearance);
            prbAppearance.setProgress(prbAppearance.getProgress() + value);
            this.txtAppearance.setText(prbAppearance.getProgress() + "%");
        }
        value = jsonResult.getInt("assets");
        if (value == 0)
        {
            dialogResult.removeView(dialog.findViewById(R.id.linearMoney));
        } else {
            toString(value, txtAssets);
            money += value;
            this.txtMoney.setText(money + " VND");
            saveGame.saveMoney(money);
        }
        saveGame.savePlayerInfo(prbHappy.getProgress(), prbHealth.getProgress(), prbSmart.getProgress(), prbAppearance.getProgress());
        saveGame.saveDetailActivity(contentHtml);
        this.txtContent.setText(android.text.Html.fromHtml(contentHtml));
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });

        btnOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                try {
                    if (isAgeEvent)
                        initNewAge();
                    else jobEvent();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        changeProgressBackground(prbAppearance);
        changeProgressBackground(prbHappy);
        changeProgressBackground(prbHealth);
        changeProgressBackground(prbSmart);

        dialog.show();
    }

    void dialogJobEvent(String title) throws JSONException {
        //Toast.makeText(this, "ssdfsgsdgsdgd", Toast.LENGTH_LONG).show();
        if (!jsonResult.getBoolean("selection")) {
            JSONArray arr = jsonResult.getJSONArray("event");
            jsonResult = arr.getJSONObject(new Random().nextInt(arr.length()));
            saveGame.saveSalary(jsonResult.getInt("salary"));
            //Tao dialog hien thi ket qua cua event
            dialogEventResult(title, false);
            return;
        }

        //Tao dialog va them cac button lua chon vao dialog
        Dialog dialog = createDialog(title, jsonResult.getString("event"));
        LinearLayout dialogCustom = dialog.findViewById(R.id.dialog_event);
        JSONArray arrSelect = jsonResult.getJSONArray("select");
        for (int i = 0; i < arrSelect.length(); i++) {
            JSONObject objectSelect = arrSelect.getJSONObject(i);
            Button btn = addButton(dialogCustom, objectSelect.getString("content"));
            int finalI = i;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        jsonResult = arrSelect.getJSONObject(finalI);
                        dialog.dismiss();
                        dialogJobEvent(title);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        dialog.show();
    }

    void dialogJobEventWithAsset(String title) throws JSONException {
        //Tao dialog asset
        Dialog dialog = createAssetDialog();

        //Anh xa
        TextView txtContent = dialog.findViewById(R.id.textviewAssetContent);
        ImageView imgAsset = dialog.findViewById(R.id.imageviewAsset);
        TextView txtAssetName = dialog.findViewById(R.id.textviewAsset);
        Button btnAccept = dialog.findViewById(R.id.buttonAssetAccept);
        Button btnCancel = dialog.findViewById(R.id.buttonAssetCancel);

        int imageID = getResources().getIdentifier(jsonResult.getString("asset"), "drawable", this.getPackageName());
        //Gan gia tri
        txtContent.setText(jsonResult.getString("event"));
        imgAsset.setImageResource(imageID);
        txtAssetName.setText(jsonResult.getString("name"));

        JSONArray arrSelect = jsonResult.getJSONArray("select");

        //Kiem tra xem co so huu do vat nay hay chua
        boolean isOwn = false;
        ArrayList<product> arrProduct = saveGame.getAsset();
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
                    createNotification(R.drawable.cancel, "Bạn đã sở hữu mòn đồ này đâu mà đồng ý!!!", MainActivity.this);
                else {
                    try {
                        JSONArray arr = arrSelect.getJSONArray(0);
                        jsonResult = arr.getJSONObject(new Random().nextInt(arr.length()));
                        dialogEventResult(title, false);
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
                    jsonResult = arr.getJSONObject(new Random().nextInt(arr.length()));
                    dialogEventResult(title, false);
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        dialog.show();
    }

    void jobEvent() throws JSONException {
        int currentSkill = saveGame.getSkill();
        int addSkill = jsonResult.getInt("skill");
        saveGame.saveSkill(currentSkill + addSkill);

        JSONArray arrJob = jsonJob.getJSONArray("event");
        for (int i=0; i<arrJob.length(); i++)
        {
            //Toast.makeText(MainActivity.this, "error1", Toast.LENGTH_SHORT).show();
            jsonResult =  arrJob.getJSONObject(i);
            int require = jsonResult.getInt("require");
            if (currentSkill < require && currentSkill + addSkill >= require)
            {
                if (!jsonResult.getString("asset").equals(""))
                    dialogJobEventWithAsset("Công Việc");
                else if (jsonResult.getBoolean("selection")) {
                    dialogJobEvent("Công việc");
                } else {
                    saveGame.saveSalary(jsonResult.getInt("salary"));
                    dialogEventResult("Công việc", false);
                    //Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    Button addButton(LinearLayout dialogCustom, String text)
    {
        Button btn = new Button(dialogCustom.getContext());
        btn.setText(text);
        btn.setTextSize(15);
        btn.setBackgroundResource(R.drawable.custom_button_menu);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(800, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 10;
        params.topMargin = 10;
        params.gravity = Gravity.CENTER_HORIZONTAL;
        btn.setLayoutParams(params);
        btn.setTextColor( Color.argb(255,16,54,103));
        dialogCustom.addView(btn);

        return btn;
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

    Dialog createDialog(String title, String event){
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
        txtTitle.setText(title);

        return dialog;
    }

    public static Dialog createNotification(int image, String content, Context context)
    {
        //Tao dialog
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_notification);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogResultAnimation;

        //Anh xa
        TextView txtTitle   = dialog.findViewById(R.id.textviewNotificationTitle);
        TextView txtContent = dialog.findViewById(R.id.textviewNotificationContent);
        ImageView imageView = dialog.findViewById(R.id.imageviewNotification);
        Button btnOke       = dialog.findViewById(R.id.buttonNotificationtOke);

        //Gan gia tri
        txtTitle.setText("Thông báo");
        txtContent.setText(content);
        imageView.setImageResource(image);

        //ViewGroup.LayoutParams layoutParams = txtContent.getLayoutParams();
        //LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(0, layoutParams.height);
        //imageView.setLayoutParams(paramsImage);


        btnOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

        return dialog;
    }

    void readEvent() throws JSONException {
        String jsonEvent = null;
        try {
            InputStream inputStream = getAssets().open("age_event.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonEvent = new String(buffer, "UTF-8");
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        arrJsonAge = new JSONArray(jsonEvent);
    }

    void readFriend() throws JSONException {
        String jsonEvent = null;
        try {
            InputStream inputStream = getAssets().open("friend.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonEvent = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        arrFriend = new ArrayList<>();
        JSONArray jsonFriend;
        jsonFriend = new JSONArray(jsonEvent);

        for (int i=0; i<jsonFriend.length(); i++)
        {
            JSONObject object = jsonFriend.getJSONObject(i);
            String name = object.getString("name");
            int age = saveGame.getAge() + (new Random().nextInt(5) - 2);
            String avatar = object.getString("avatar");

            arrFriend.add(new QuanHe(name, age, new Random().nextInt(30) + 30,
                    "Bạn bè", getResources().getIdentifier(avatar, "drawable", this.getPackageName())));
        }
    }

    void readJob() throws JSONException {
        String jsonEvent = null;
        try {
            InputStream inputStream = getAssets().open("job.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonEvent = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        jsonAllJob = new JSONObject(jsonEvent);
    }
    public void gotoAsset(View view)
    {
        startActivity(new Intent(MainActivity.this, Asset.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }




    void loadGame()
    {
        contentHtml = saveGame.getDetailActivity();
        txtContent.setText(android.text.Html.fromHtml(contentHtml));
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });


        prbAppearance.setProgress(saveGame.getAppearance());
        prbHappy.setProgress(saveGame.getHappy());
        prbHealth.setProgress(saveGame.getHealth());
        prbSmart.setProgress(saveGame.getSmart());

        txtAppearance.setText(prbAppearance.getProgress() + "%");
        txtHappy.setText(prbHappy.getProgress() + "%");
        txtSmart.setText(prbSmart.getProgress() + "%");
        txtHealth.setText(prbHealth.getProgress() + "%");

        money = saveGame.getMoney();
        txtMoney.setText(money + "VND");
        txtName.setText(saveGame.getName());
        txtJob.setText(saveGame.getJob());
        arrRelationship = saveGame.getRelationship();

        changeProgressBackground(prbAppearance);
        changeProgressBackground(prbHappy);
        changeProgressBackground(prbHealth);
        changeProgressBackground(prbSmart);
    }

    void changeWork() throws JSONException {
        switch (saveGame.getJob())
        {
            case "Lập trình viên":
                jsonJob = jsonAllJob.getJSONObject("coder");
                break;
            case "Trẻ trâu":
                jsonJob = jsonAllJob.getJSONObject("student");
                break;
        }

    }

    // init tam thoi
    void init(String name, String gender) throws JSONException {
        String json = null;
        try {
            InputStream inputStream = getAssets().open("parent.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject object = new JSONObject(json);
        JSONArray arrFather = object.getJSONArray("father");
        JSONArray arrMother = object.getJSONArray("mother");
        JSONArray arrJob = object.getJSONArray("job");
        Random random = new Random();

        //Tao quan he Bo Me
        String fatherName = arrFather.getString(random.nextInt(arrFather.length()));
        int fatherAge = (random.nextInt(11) + 20);
        QuanHe father = new QuanHe(fatherName, fatherAge, 100, "Bố", R.drawable.boy); //Thay hinh sau
        String motherName = arrMother.getString(random.nextInt(arrMother.length()));
        int motherAge = (random.nextInt(11) + 20);
        QuanHe mother = new QuanHe(motherName, motherAge, 100, "Mẹ", R.drawable.girl); //Thay hinh sau

        ArrayList<QuanHe> arrRelationship = new ArrayList<>();
        arrRelationship.add(father);
        arrRelationship.add(mother);
        //Them cho vui
        arrRelationship.add(new QuanHe("Trần Thanh Vũ", 19, 50, "Bạn bè",R.drawable.boy));
        arrRelationship.add(new QuanHe("Nguyễn Thiện Sua", 19, 50, "Bạn bè",R.drawable.boy));
        arrRelationship.add(new QuanHe("Nguyễn Hiếu Nghĩa", 19, 50, "Bạn bè",R.drawable.boy));
        arrRelationship.add(new QuanHe("Mai Long Thành", 19, 80, "Bạn bè",R.drawable.boy));
        arrRelationship.add(new QuanHe("Võ Thành Phát", 19, 20, "Bạn bè",R.drawable.boy));
        arrRelationship.add(new QuanHe("Hoàng Nhật Tiến", 19, 50, "Bạn bè",R.drawable.boy));

        saveGame.saveRelationship(arrRelationship);
        this.arrRelationship = arrRelationship;
        saveGame.saveNewFriendInYear(0);
        saveGame.saveNumberOfFriends(0);

        //Tao ngay sinh
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        contentHtml = "<h5> <font color=\"blue\">Tuổi 0</font></h5>" +
                "Tôi tên " + name + " - " + gender + "<br>" +
                "Sinh ngày " + format.format(date) + "<br>" +
                "Bố tôi là " + fatherName + " - " +
                arrJob.getString(random.nextInt(arrJob.length())) +
                " (" + fatherAge + " tuổi )" + "<br>" +
                "Mẹ tôi là " + motherName + " - " +
                arrJob.getString(random.nextInt(arrJob.length())) +
                " (" + motherAge + " tuổi )" + "<br>";
        //Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        saveGame.saveAge(0);
        saveGame.saveMoney(50000);
        saveGame.saveDetailActivity(contentHtml);
        saveGame.saveName(name);
        saveGame.saveJob("Trẻ trâu");
        saveGame.saveSkill(0);
        saveGame.saveExercise(0);
        saveGame.saveJogging(0);
        saveGame.saveJogging(0);
        txtJob.setText(saveGame.getJob());
        txtMoney.setText("0 VND");
        txtContent.setText(android.text.Html.fromHtml(contentHtml));
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
        txtName.setText(name);

        prbHealth.setProgress(100);
        prbHappy.setProgress(100);
        prbSmart.setProgress(30);
        prbAppearance.setProgress(50);
        changeProgressBackground(prbAppearance);
        changeProgressBackground(prbHappy);
        changeProgressBackground(prbHealth);
        changeProgressBackground(prbSmart);

        txtAppearance.setText(prbAppearance.getProgress() + "%");
        txtHappy.setText(prbHappy.getProgress() + "%");
        txtSmart.setText(prbSmart.getProgress() + "%");
        txtHealth.setText(prbHealth.getProgress() + "%");
        saveGame.savePlayerInfo(prbHappy.getProgress(), prbHealth.getProgress(), prbSmart.getProgress(), prbAppearance.getProgress());
    }

    void initNewAge()
    {
        //Toast.makeText(this, "new age", Toast.LENGTH_SHORT).show();
        int age = saveGame.getAge() + 1;
        //saveGame.saveAge(age);
        saveGame.saveExercise(0);
        saveGame.saveJogging(0);
        saveGame.saveJogging(0);
        saveGame.saveNewFriendInYear(0);


        for (int i=0; i<arrRelationship.size(); i++)
        {
            QuanHe friend = arrRelationship.get(i);
            friend.setTuoi(friend.getTuoi() + 1); // Them tuoi
            if (friend.getQuanHe() == "Bạn bè")
            {
                friend.setDoThanMat(friend.getDoThanMat() - 20); //Giam moi quan he
            }
        }

        saveGame.saveRelationship(arrRelationship);
        dialogLostFriend(0);
    }

    void dialogLostFriend(int index) {
        if (index == arrRelationship.size()) {
            saveGame.saveRelationship(arrRelationship);
            return;
        }
        QuanHe friend = arrRelationship.get(index);
        if (friend.getDoThanMat() <= 0) {
            Dialog dialog = createNotification(R.drawable.cancel, "Tình bạn giữa bạn và " + friend.getHoten() + " đã rạn nứt.", this);
            Button btnOke       = dialog.findViewById(R.id.buttonNotificationtOke);
            btnOke.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    arrRelationship.remove(index);
                    saveGame.saveNumberOfFriends(saveGame.getNumberOfFriends() - 1);
                    dialog.dismiss();
                    dialogLostFriend(index);
                }
            });
        }
        else {
            dialogLostFriend(index + 1);
        }
    }
    void addAgeHTML(int age)
    {
        contentHtml += "<h5> <font color=\"blue\">Tuổi " + age + "</font></h5>";
        txtContent.setText(android.text.Html.fromHtml(contentHtml));
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
        saveGame.saveDetailActivity(contentHtml);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (currentTime + 2000 > System.currentTimeMillis())
        {
            backToast.cancel();
            this.finishAffinity();
        }
        else {
            backToast = Toast.makeText(this, "Ấn lần nữa để thoát", Toast.LENGTH_SHORT);
            backToast.show();
        }
        currentTime = System.currentTimeMillis();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_INIT && resultCode == RESULT_OK && data != null)
        {
            String name = data.getStringExtra("name");
            String gender = data.getStringExtra("gender");
            try {
                init(name, gender);
                readJob();
                changeWork();
                //Toast.makeText(this, "Changed", Toast.LENGTH_SHORT).show();
                readEvent();
                //Toast.makeText(this, "Event", Toast.LENGTH_SHORT).show();
                readFriend();
                //Toast.makeText(this, "Friend", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    void changeProgressBackground(ProgressBar pb)
    {
        int progress = pb.getProgress();
        if (progress >= 80 )
            pb.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progressbar));
        else if (progress<80 && progress > 30)
            pb.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progressbar_medium));
        else pb.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progressbar_low));
        //Toast.makeText(this, "a", Toast.LENGTH_SHORT).show();
    }

    void dialogUniversity()
    {
        Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_university);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogResultAnimation;

        //Anh xa
        ListView lvUni = dialog.findViewById(R.id.listViewUni);
        ArrayList<University> arrUniversity = new ArrayList<>();

        arrUniversity.add(new University(R.drawable.angiang, "Đại học An Giang", 40));
        arrUniversity.add(new University(R.drawable.uit, "Đại học Công nghệ Thông tin", 60));
        arrUniversity.add(new University(R.drawable.fpt, "Đại học FPT", 40));
        arrUniversity.add(new University(R.drawable.bachkhoa, "Đại học Bách Khoa", 60));
        arrUniversity.add(new University(R.drawable.hoasen, "Đại học Hoa Sen", 50));
        arrUniversity.add(new University(R.drawable.yduoc, "Đại học Y Dược", 55));
        arrUniversity.add(new University(R.drawable.cantho, "Đại học Cần Thơ", 45));
        arrUniversity.add(new University(R.drawable.rmit, "Đại học RMIT", 55));
        arrUniversity.add(new University(R.drawable.ngoaithuong, "Đại học Ngoại thương", 55));
        arrUniversity.add(new University(R.drawable.kinhte, "Đại học Kinh tế", 55));
        arrUniversity.add(new University(R.drawable.supham, "Đại học Sư phạm", 45));
        arrUniversity.add(new University(R.drawable.suphamkithuat, "Đại học Sư phạm Kĩ thuật", 55));
        arrUniversity.add(new University(R.drawable.tonducthang, "Đại học Tôn Đức Thắng", 55));

        UniversityAdapter adapter = new UniversityAdapter(this, R.layout.university_line, arrUniversity);
        lvUni.setAdapter(adapter);

//        for (int i=0; i<5; i++)
//        {
//            if (arrUniversity.get(i).getScore() < 55)
//            {
//                lvUni.getChildAt(i).setBackgroundColor(Color.RED);//R.drawable.list_item_unable);
//            }
//        }

        dialog.show();
    }

    private void AnhXa() {
        btnRelationship = (Button) findViewById(R.id.buttonRelationship);
        btnActivity = (Button) findViewById(R.id.buttonActivity);

        txtContent = findViewById(R.id.textViewDetail);
        txtAppearance = findViewById(R.id.txtAppearance);
        txtHappy = findViewById(R.id.txtHappy);
        txtSmart = findViewById(R.id.txtSmart);
        txtHealth = findViewById(R.id.txtHealth);
        txtMoney = findViewById(R.id.textviewMoney);
        txtName = findViewById(R.id.textviewName);
        txtJob = findViewById(R.id.textviewJob);

        prbAppearance = findViewById(R.id.progressbarAppearance);
        prbHappy = findViewById(R.id.progressbarHappy);
        prbHealth = findViewById(R.id.progressbarHealth);
        prbSmart = findViewById(R.id.progressbarSmart);

        ibtnAddAge = findViewById(R.id.imagebuttonAddAge);
        btnAssets = findViewById(R.id.buttonAssets);
        btnWork = findViewById(R.id.buttonInfant);
        scrollView = findViewById(R.id.scrollViewText);


        preferences = getSharedPreferences("data", MODE_PRIVATE);
        saveGame = new SaveGame(preferences);
    }

    public void gotoActivity(View view)
    {
        startActivity(new Intent(MainActivity.this, HoatDong.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    public void gotoRelationship(View view)
    {
        startActivity(new Intent(MainActivity.this, RelationShip.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }
}
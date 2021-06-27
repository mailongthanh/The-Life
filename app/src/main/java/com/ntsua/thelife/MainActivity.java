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
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.ContactsContract;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

    Button btnActivity, btnRelationship, btnWork, btnAssets;
    ImageView imgAvatar;
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
//        saveGame.saveAge(17);
//        saveGame.savePlayerInfo(100, 100, 60, 100);
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
        Button btn = addButton(dialogCustom, "Bỏ qua");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
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
            addAgeHTML(18);
            dialogUniversity();
            return;
        }
//        if (age == 6)
//        {
//            saveGame.saveJob("Học sinh");
//            //Change work
//            txtContent.setText("Học sinh");
//        }
        //lay su kien tuoi
        JSONArray arrAge = arrJsonAge.getJSONArray(age);
        Random random = new Random();
        //Toast.makeText(this, arrAge.length() + " - " + age, Toast.LENGTH_SHORT).show();
        if (arrAge.length() == 0)
        {
            createNotification(saveGame.getAvatar(), "Năm nay không có sự kiện gì đặc biệt",this);
            initNewAge();
            addAgeHTML(age);
            contentHtml = saveGame.getDetailActivity();
            contentHtml += "Năm nay không có sự kiện gì đặc biệt<br>";
            txtContent.setText(android.text.Html.fromHtml(contentHtml));
            saveGame.saveDetailActivity(contentHtml);
            return;
        }
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
        //Toast.makeText(this, "hey", Toast.LENGTH_SHORT).show();
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
                    if (saveGame.getHealth() <= 0)
                        checkMySelf(MainActivity.this, "\"Qua đời vì sức khỏe yếu kéo dài, không chịu nổi những biến cố trong cuộc sống\"");
                    else if (isAgeEvent)
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

        //Kiem tra co bi benh hay khong
        String sick  = jsonResult.getString("sick");
        if (!sick.equals("")) //Co benh
        {
            ArrayList<Sick> arrSick = saveGame.getSick();
            int index = -1;
            for (int i=0; i<arrSick.size(); i++)
            {
                if (arrSick.get(i).getSickName().equals(sick))
                {
                    index = i;
                    break;
                }
            }
            if (index == -1)
            {
                Toast.makeText(this, "cant find sick name", Toast.LENGTH_SHORT).show();
                return;
            }
            arrSick.get(index).setSick(true);
            saveGame.saveSick(arrSick);
        }

    }

    static public void checkMySelf(Context context, String content)
    {
        if (saveGame.getHealth() <= 0)
        {
            Dialog dialog = createNotification(R.drawable.cancel, "Bạn đã qua đời vì sức khỏe quá yếu", context);
            //gọi acitvity died
            Button btn = dialog.findViewById(R.id.buttonNotificationtOke);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Dialog death = new Dialog(context);
                    death.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    death.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    death.setContentView(R.layout.dialog_death);
                    death.setCanceledOnTouchOutside(false);
                    death.getWindow().getAttributes().windowAnimations = R.style.DialogResultAnimation;
                    death.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_BACK)
                            {
                                return true;
                            }
                            return false;
                        }
                    });

                    //Ánh xạ
                    ImageView imgAvatar = death.findViewById(R.id.imvBoy);
                    ImageView imgFlower = death.findViewById(R.id.imvFlower);
                    TextView txtName = death.findViewById(R.id.tvTen);
                    TextView txtAge = death.findViewById(R.id.tvTuoi);
                    TextView txtBorn = death.findViewById(R.id.tvNgaysinh);
                    TextView txtCountry = death.findViewById(R.id.tvQuequan);
                    TextView txtJob = death.findViewById(R.id.tvNghenghiep);
                    TextView txtAsset = death.findViewById(R.id.tvTaisan);
                    TextView txtContent = death.findViewById(R.id.tvNoiDung);

                    //Gán giá trị
                    imgAvatar.setImageResource(saveGame.getAvatar());
                    txtName.setText(saveGame.getName());
                    txtAge.setText("Hưởng thọ: " + saveGame.getAge() + " tuổi");
                    txtBorn.setText("Ngày sinh: " + saveGame.getBirthDay());
                    txtCountry.setText("Quê quá: Vietnam");
                    txtJob.setText("Nghề nghiệp: " + saveGame.getJob());
                    txtAsset.setText("Số tiền để lại: " + String.format( "%,d", saveGame.getMoney()*1000) + " VND");
                    txtContent.setText(content);
                    saveGame.saveDetailActivity(""); //Reset hoat dong

                    imgFlower.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Hieu ung
                            Animation anim = AnimationUtils.loadAnimation(context, R.anim.flower);
                            v.startAnimation(anim);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    context.startActivity(new Intent(context, MainActivity.class));
                                }
                            }, 2300);
                        }
                    });

                    death.show();
                }
            });
            return;
        }
        if (saveGame.getHappy() < 30)
        {
            createNotification(R.drawable.heartbeat, "Bạn có dấu hiệu bị trầm cảm, tốt nhất nên đến bác sĩ để chưa trị", context);
        }
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
                    createNotification(R.drawable.cancel, "Bạn đã sở hữu món đồ này đâu mà đồng ý!!!", MainActivity.this);
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
                else if (!jsonResult.getString("newjob").equals(""))
                {
                    saveGame.saveJob(jsonResult.getString("newjob"));
                    changeWork();saveGame.saveSalary(jsonResult.getInt("salary"));
                    dialogEventResult("Công việc", false);
                    //Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
                else if (jsonResult.getBoolean("selection")) {
                    dialogJobEvent("Công việc");
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
        JSONArray jsonFriend = new JSONArray(jsonEvent);

        for (int i=0; i<jsonFriend.length(); i++)
        {
            JSONObject object = jsonFriend.getJSONObject(i);
            String name = object.getString("name");
            int age = saveGame.getAge() + (new Random().nextInt(5) - 2);
            String avatar = object.getString("avatar");

            boolean isBoy = false;
            if (object.getString("gender").equals("boy")) {
                isBoy = true;
            }

            arrFriend.add(new QuanHe(name, age, new Random().nextInt(30) + 30,
                    NameOfRelationship.Friend, getResources().getIdentifier(avatar, "drawable", this.getPackageName()), isBoy));
        }
        setPeopleAvatar(arrFriend);
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


        imgAvatar.setImageResource(saveGame.getAvatar());
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
            case "Trẻ trâu":
                jsonJob = jsonAllJob.getJSONObject("student");
                break;
            case "Lập trình viên":
                jsonJob = jsonAllJob.getJSONObject("coder");
                break;
            case "Chuyên gia công nghệ":
                jsonJob = jsonAllJob.getJSONObject("Chuyên gia công nghệ");
                break;
            case "Cài WIN dạo":
                jsonJob = jsonAllJob.getJSONObject("Cài win dạo");
                break;
            case "Chủ tịch tập đoàn công nghệ thông tin":
                jsonJob = jsonAllJob.getJSONObject("Chủ tịch tập đoàn công nghệ thông tin");
                break;
            case "Ca sĩ đám cưới":
                jsonJob = jsonAllJob.getJSONObject("Ca sĩ đám cưới");
                break;
            case "Ca sĩ phòng trà":
                jsonJob = jsonAllJob.getJSONObject("Ca sĩ phòng trà");
                break;
            case "Ca sĩ thần tượng":
                jsonJob = jsonAllJob.getJSONObject("Ca sĩ thần tượng");
                break;
            case "DIVA":
                jsonJob = jsonAllJob.getJSONObject("DIVA");
                break;
            case "Phụ bếp":
                jsonJob = jsonAllJob.getJSONObject("Phụ bếp");
                break;
            case "Đầu bếp":
                jsonJob = jsonAllJob.getJSONObject("Đầu bếp");
                break;
            case "Chuyên gia ẩm thực":
                jsonJob = jsonAllJob.getJSONObject("Chuyên gia ẩm thực");
                break;
            case "VUA ĐẦU BẾP":
                jsonJob = jsonAllJob.getJSONObject("VUA ĐẦU BẾP");
                break;
            case "Phóng viên":
                jsonJob = jsonAllJob.getJSONObject("Phóng viên");
                break;
            case "Trưởng chuyên mục":
                jsonJob = jsonAllJob.getJSONObject("Trưởng chuyên mục");
                break;
            case "Thư ký tòa soạn":
                jsonJob = jsonAllJob.getJSONObject("Thư ký tòa soạn");
                break;
            case "Tổng biên tập":
                jsonJob = jsonAllJob.getJSONObject("Tổng biên tập");
                break;
            case "Cầu thủ dự bị":
                jsonJob = jsonAllJob.getJSONObject("Cầu thủ dự bị");
                break;
            case "Chân sút triển vọng":
                jsonJob = jsonAllJob.getJSONObject("Chân sút triển vọng");
                break;
            case "Ngôi sao bóng đá":
                jsonJob = jsonAllJob.getJSONObject("Ngôi sao bóng đá");
                break;
            case "Huyền thoại bóng đá":
                jsonJob = jsonAllJob.getJSONObject("Huyền thoại bóng đá");
                break;
            case "Bồi bàn":
                jsonJob = jsonAllJob.getJSONObject("Bồi bàn");
                break;
            case "Thu ngân":
                jsonJob = jsonAllJob.getJSONObject("Thu ngân");
                break;
            case "Quản lý nhà hàng":
                jsonJob = jsonAllJob.getJSONObject("Quản lý nhà hàng");
                break;
            case "Chủ nhà hàng":
                jsonJob = jsonAllJob.getJSONObject("Chủ nhà hàng");
                break;
            case "Diễn viên đóng thế":
                jsonJob = jsonAllJob.getJSONObject("Diễn viên đóng thế");
                break;
            case "Diễn viên chính":
                jsonJob = jsonAllJob.getJSONObject("Diễn viên chính");
                break;
            case "Ngôi sao điện ảnh":
                jsonJob = jsonAllJob.getJSONObject("Ngôi sao điện ảnh");
                break;
            case "Thực tập sinh":
                jsonJob = jsonAllJob.getJSONObject("Thực tập sinh");
                break;
            case "Giáo viên":
                jsonJob = jsonAllJob.getJSONObject("Giáo viên");
                break;
            case "Trưởng bộ môn":
                jsonJob = jsonAllJob.getJSONObject("Trưởng bộ môn");
                break;
            case "Hiệu trưởng":
                jsonJob = jsonAllJob.getJSONObject("Hiệu trưởng");
                break;
            case "Bán hàng rong":
                jsonJob = jsonAllJob.getJSONObject("Bán hàng rong");
                break;
            case "Chủ shop online":
                jsonJob = jsonAllJob.getJSONObject("Chủ shop online");
                break;
            case "Quản lý siêu thị mini":
                jsonJob = jsonAllJob.getJSONObject("Quản lý siêu thị mini");
                break;
            case "Binh nhất":
                jsonJob = jsonAllJob.getJSONObject("Binh nhất");
                break;
            case "Trung sĩ":
                jsonJob = jsonAllJob.getJSONObject("Trung sĩ");
                break;
            case "Thượng úy":
                jsonJob = jsonAllJob.getJSONObject("Thượng úy");
                break;
            case "Đại tá":
                jsonJob = jsonAllJob.getJSONObject("Đại tá");
                break;
            case "Nhân viên sale":
                jsonJob = jsonAllJob.getJSONObject("Nhân viên sale");
                break;
            case "Trưởng phòng marketing":
                jsonJob = jsonAllJob.getJSONObject("Trưởng phòng marketing");
                break;
            case "Giám đốc kinh doanh":
                jsonJob = jsonAllJob.getJSONObject("Giám đốc kinh doanh");
                break;
            case "Chạy Grab":
                jsonJob = jsonAllJob.getJSONObject("Chạy Grab");
                break;
            case "Tài xế Taxi":
                jsonJob = jsonAllJob.getJSONObject("Tài xế Taxi");
                break;
            case "Quản lý đội xe":
                jsonJob = jsonAllJob.getJSONObject("Quản lý đội xe");
                break;
            case "Chủ công ty Taxi":
                jsonJob = jsonAllJob.getJSONObject("Chủ công ty Taxi");
                break;
            case "Bác sĩ thực tập":
                jsonJob = jsonAllJob.getJSONObject("Bác sĩ thực tập");
                break;
            case "Bác sĩ chính":
                jsonJob = jsonAllJob.getJSONObject("Bác sĩ chính");
                break;
            case "Bác sĩ trưởng khoa":
                jsonJob = jsonAllJob.getJSONObject("Bác sĩ trưởng khoa");
                break;
            case "Viện trưởng":
                jsonJob = jsonAllJob.getJSONObject("Viện trưởng");
                break;
        }
    }

    // init tam thoi
    void init(String name, boolean isBoy) throws JSONException {
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
        QuanHe father = new QuanHe(fatherName, fatherAge, 100, NameOfRelationship.Dad, R.drawable.boy_5, true); //Thay hinh sau
        String motherName = arrMother.getString(random.nextInt(arrMother.length()));
        int motherAge = (random.nextInt(11) + 20);
        QuanHe mother = new QuanHe(motherName, motherAge, 100, NameOfRelationship.Mom, R.drawable.girl_5, false); //Thay hinh sau

        ArrayList<QuanHe> arrRelationship = new ArrayList<>();
        arrRelationship.add(father);
        arrRelationship.add(mother);
        //Them cho vui
//        arrRelationship.add(new QuanHe("Trần Thanh Vũ", 19, 50, NameOfRelationship.Friend,R.drawable.boy, true));
//        arrRelationship.add(new QuanHe("Nguyễn Thiện Sua", 19, 50, NameOfRelationship.Friend, R.drawable.boy, true));
//        arrRelationship.add(new QuanHe("Nguyễn Hiếu Nghĩa", 19, 50, NameOfRelationship.Friend, R.drawable.boy, true));
//        arrRelationship.add(new QuanHe("Mai Long Thành", 19, 80, NameOfRelationship.Friend, R.drawable.boy, true));
//        arrRelationship.add(new QuanHe("Võ Thành Phát", 19, 20, NameOfRelationship.Friend, R.drawable.boy, true));
//        arrRelationship.add(new QuanHe("Hoàng Nhật Tiến", 19, 50, NameOfRelationship.Friend, R.drawable.boy, true));
          arrRelationship.add(new QuanHe("Crush", 19, 90, NameOfRelationship.Friend, R.drawable.boy, true));

        saveGame.saveRelationship(arrRelationship);
        this.arrRelationship = arrRelationship;
        saveGame.saveNewFriendInYear(0);
        saveGame.saveNumberOfFriends(0);
        saveGame.saveNumberOfGirlFriend(0);

        String gender = "Nữ";
        if (isBoy)
            gender = "Nam";
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

        saveGame.saveGender(isBoy);
        int avatarID = getResources().getIdentifier("boy_0", "drawable", getPackageName());
        if (!isBoy)
            avatarID = getResources().getIdentifier("girl_0", "drawable", getPackageName());
        imgAvatar.setImageResource(avatarID);
        saveGame.saveAvatar(avatarID);
        saveGame.saveAge(0);
        saveGame.saveBirthDay(format.format(date));
        saveGame.saveMoney(50000);
        saveGame.saveDetailActivity(contentHtml);
        saveGame.saveName(name);
        saveGame.saveJob("VUA ĐẦU BẾP");
        saveGame.saveSkill(0);
        saveGame.saveExercise(0);
        saveGame.saveJogging(0);
        saveGame.saveJogging(0);
        saveGame.saveCrime(0);
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
        prbHappy.setProgress(80);
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

        ArrayList<Sick> arrSick = new ArrayList<>();
        arrSick.add( new Sick(false, "đau răng", "Nha sĩ", "nhasi", 10));
        arrSick.add(  new Sick(false, "đau mắt", "Bác sĩ mắt", "mat", 10));
        arrSick.add(  new Sick(false, "tai mũi họng", "Bác sĩ tai mũi họng", "taimuihong", 100));
        arrSick.add(  new Sick(false, "mẫn ngứa", "Bác sĩ da liễu", "dalieu", 10));
        arrSick.add(  new Sick(false, "cảm","Bác sĩ cảm sốt", "camsot", 20));
        arrSick.add(  new Sick(false, "trĩ", "Bác sĩ trĩ", "tri", 30));
        arrSick.add(  new Sick(false, "sốt xuất huyết", "Bác sĩ cảm sốt", "sotxuathuyet", 100));

        saveGame.saveSick(arrSick);
    }

    void initNewAge()
    {
        //Toast.makeText(this, "new age", Toast.LENGTH_SHORT).show();
        int age = saveGame.getAge() + 1;
        saveGame.saveAge(age);
        saveGame.saveExercise(0);
        saveGame.saveJogging(0);
        saveGame.saveJogging(0);
        saveGame.saveLibrary(0);
        saveGame.saveNewFriendInYear(0);
        saveGame.saveNhaHang(0);
        saveGame.saveNuocEp(0);
        saveGame.saveTraSua(0);
        saveGame.saveCaPhe(0);
        saveGame.saveRuou(0);
        saveGame.saveBia(0);
        saveGame.saveHamBurGer(0);
        saveGame.saveBanhMi(0);
        saveGame.saveMy(0);
        saveGame.saveTraiCay(0);
        saveGame.savePizza(0);
        saveGame.saveLau(0);
        saveGame.saveCom(0);
        saveGame.saveHaiSan(0);
        saveGame.saveGa(0);
        saveGame.saveRauCu(0);
        saveGame.saveKeo(0);
        saveGame.saveFastFood(0);
        saveGame.saveCrime(0);
        for (int i=0; i<arrRelationship.size(); i++)
        {
            QuanHe friend = arrRelationship.get(i);
            friend.setInteraction(0);
            friend.setTuoi(friend.getTuoi() + 1); // Them tuoi
            if (friend.getQuanHe() == NameOfRelationship.Friend ||
                    friend.getQuanHe() == NameOfRelationship.GirlFriend ||
                    friend.getQuanHe() == NameOfRelationship.BoyFriend)
            {
                arrRelationship.get(i).setDoThanMat(friend.getDoThanMat() - 20); //Giam moi quan he
            }
        }
        setPeopleAvatar(arrRelationship);
        saveGame.saveRelationship(arrRelationship);

        for (int i=0; i<arrFriend.size(); i++)
        {
            arrFriend.get(i).setTuoi(arrFriend.get(i).getTuoi() + 1);
        }
        setPeopleAvatar(arrFriend);

        setAvatar();
        dialogLostFriend(0);

        //xet suc khoe
        int health = saveGame.getHealth();
        if (prbHappy.getProgress() < 30) //Kiem tra xem co van de ve tam li hay khong (happy<30)
        {
            health -= (30 - prbHappy.getProgress());
        }
        health -= (int) (saveGame.getAge() / 4); //Tru suc khoe theo do tuoi, tuoi cang cao tru cang nhieu
        String reason = "";
        if (health <=0 )
            reason = "\"Qua đời vì tuổi già sức yếu\"";
        else {
            ArrayList<Sick> arrSick = saveGame.getSick(); //Kiem tra co benh ma khong chua hay khong
            for (int i = 0; i < arrSick.size(); i++) {
                if (arrSick.get(i).isSick()) {
                    health -= arrSick.get(i).getHealth();
                }
            }
            if (health <=0 )
                reason = "\"Qua đời vì sức khỏe yếu kéo dài, bệnh tật không được chữa trị kịp thời\"";
        }
        //Luu suc khoe lai
        saveGame.savePlayerInfo(saveGame.getHappy(), health, saveGame.getSmart(), saveGame.getAppearance());
        prbHealth.setProgress(health);
        txtHealth.setText(prbHealth.getProgress() + "%");
        changeProgressBackground(prbHealth);
        checkMySelf(this, reason);
    }

    void setPeopleAvatar(ArrayList<QuanHe> array)
    {
        for (int i=0; i<array.size(); i++) {
            QuanHe quanHe = array.get(i);
            int id = quanHe.HinhAnh;
            if (quanHe.isBoy()) //Boy
            {
                if (quanHe.getTuoi() >= 60) id = R.drawable.boy_8;
                else if (quanHe.getTuoi() >= 40) {
                    id = R.drawable.boy_7;
                } else if (quanHe.getTuoi() >= 30) {
                    id = R.drawable.boy_6;
                } else if (quanHe.getTuoi() >= 20) {
                    id = R.drawable.boy_5;
                } else if (quanHe.getTuoi() >= 15) {
                    id = R.drawable.boy_4;
                } else if (quanHe.getTuoi() >= 10) {
                    id = R.drawable.boy_3;
                } else if (quanHe.getTuoi() >= 5) {
                    id = R.drawable.boy_2;
                } else if (quanHe.getTuoi() >= 2) {
                    id = R.drawable.boy;
                }
            } else {
                if (quanHe.getTuoi() >= 60) id = R.drawable.girl_8;
                else if (quanHe.getTuoi() >= 40) {
                    id = R.drawable.girl_7;
                } else if (quanHe.getTuoi() >= 30) {
                    id = R.drawable.girl_6;
                } else if (quanHe.getTuoi() >= 20) {
                    id = R.drawable.girl_5;
                } else if (quanHe.getTuoi() >= 15) {
                    id = R.drawable.girl_4;
                } else if (quanHe.getTuoi() >= 10) {
                    id = R.drawable.girl_3;
                } else if (quanHe.getTuoi() >= 5) {
                    id = R.drawable.girl_2;
                } else if (quanHe.getTuoi() >= 2) {
                    id = R.drawable.girl;
                }
            }
            quanHe.setHinhAnh(id);
        }
    }

    void setAvatar()
    {
        int age = saveGame.getAge();
        String avatarName = "";

        if (saveGame.getGender()) //Boy
        {
            if (age == 2)  avatarName = "boy";
            else if (age == 5){ avatarName = "boy_2";}
            else if (age == 10){ avatarName = "boy_3";}
            else if (age == 15){ avatarName = "boy_4";}
            else if (age == 20){ avatarName = "boy_5";}
            else if (age == 30){ avatarName = "boy_6";}
            else if (age == 40){ avatarName = "boy_7";}
            else if (age >= 60){ avatarName = "boy_8";}
        } else {
            if (age == 2)  avatarName = "girl";
            else if (age == 5){ avatarName = "girl_2";}
            else if (age == 10){ avatarName = "girl_3";}
            else if (age == 15){ avatarName = "girl_4";}
            else if (age == 20){ avatarName = "girl_5";}
            else if (age == 30){ avatarName = "girl_6";}
            else if (age == 40){ avatarName = "girl_7";}
            else if (age >= 60){ avatarName = "girl_8";}
        }

        if (avatarName.equals(""))
            return;
        int id = getResources().getIdentifier(avatarName, "drawable", getPackageName());
        saveGame.saveAvatar(id);
        imgAvatar.setImageResource(id);
    }

    void dialogLostFriend(int index) {
        if (index == arrRelationship.size()) {
            saveGame.saveRelationship(arrRelationship);
            return;
        }
        QuanHe friend = arrRelationship.get(index);
        if (friend.getDoThanMat() <= 0) {
            Dialog dialog = createNotification(R.drawable.cancel, "Môi quan hệ giữa bạn và " + friend.getHoten() + " đã đổ vỡ.", this);
            Button btnOke       = dialog.findViewById(R.id.buttonNotificationtOke);
            arrRelationship.remove(index);
            saveGame.saveNumberOfFriends(saveGame.getNumberOfFriends() - 1);
            btnOke.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
            boolean isboy = data.getBooleanExtra("gender", false);

            try {
                init(name, isboy);
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

        arrUniversity.add(new University(R.drawable.angiang, "Đại học An Giang", 1, 40));
        arrUniversity.add(new University(R.drawable.uit, "Đại học Công nghệ Thông tin", 5,60));
        arrUniversity.add(new University(R.drawable.fpt, "Đại học FPT", 1, 40));
        arrUniversity.add(new University(R.drawable.bachkhoa, "Đại học Bách Khoa", 5, 60));
        arrUniversity.add(new University(R.drawable.hoasen, "Đại học Hoa Sen", 3, 50));
        arrUniversity.add(new University(R.drawable.yduoc, "Đại học Y Dược", 4, 55));
        arrUniversity.add(new University(R.drawable.cantho, "Đại học Cần Thơ", 2, 45));
        arrUniversity.add(new University(R.drawable.rmit, "Đại học RMIT", 4, 55));
        arrUniversity.add(new University(R.drawable.ngoaithuong, "Đại học Ngoại thương", 4, 55));
        arrUniversity.add(new University(R.drawable.kinhte, "Đại học Kinh tế", 4, 55));
        arrUniversity.add(new University(R.drawable.supham, "Đại học Sư phạm", 2, 45));
        arrUniversity.add(new University(R.drawable.suphamkithuat, "Đại học Sư phạm Kĩ thuật", 4, 55));
        arrUniversity.add(new University(R.drawable.tonducthang, "Đại học Tôn Đức Thắng", 4, 55));
        arrUniversity.add(new University(R.drawable.cancel, "Không học", 0, 0));
        UniversityAdapter adapter = new UniversityAdapter(this, R.layout.university_line, arrUniversity);
        lvUni.setAdapter(adapter);

        lvUni.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (arrUniversity.get(position).getName().equals("Không học")){
                    contentHtml += "Bạn quyết định không học đại học<br/>";
                    saveGame.saveJob("Thất nghiệp");
                }
                else {
                    contentHtml += "Bạn chọn vào học tại trường " + arrUniversity.get(position).getName() + "<br/>";
                    saveGame.saveJob("Sinh viên");
                }
                saveGame.saveDetailActivity(contentHtml);
                txtContent.setText(android.text.Html.fromHtml(contentHtml));
                txtJob.setText(saveGame.getJob());
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void AnhXa() {
        btnRelationship = (Button) findViewById(R.id.buttonRelationship);
        btnActivity = (Button) findViewById(R.id.buttonActivity);

        imgAvatar = findViewById(R.id.imageAvatar);
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


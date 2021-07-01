package com.ntsua.thelife;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMain extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentMain() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMain.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMain newInstance(String param1, String param2) {
        FragmentMain fragment = new FragmentMain();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    View view;
    Button btnActivity, btnRelationship, btnWork, btnAssets;
    ImageView imgAvatar;
    ImageButton ibtnAddAge;
    ProgressBar prbHappy, prbHealth, prbSmart, prbAppearance;
    ScrollView scrollView;
    TextView txtContent, txtHappy, txtHealth, txtSmart, txtAppearance, txtMoney, txtName, txtJob;
    ArrayList<QuanHe> arrRelationship;
    JSONArray arrJsonAge;
    JSONObject jsonResult, jsonAllJob, jsonJob;
    String contentHtml;
    int money;
    int REQUEST_CODE_INIT = 123;

    CircleImageView imgUserAvatar;
    TextView txtUserName, txtPlayerName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    void dialogJob(JSONArray arrJob) throws JSONException {
        Dialog dialog = createDialog("Làm việc", "Làm, làm nữa, làm mãi");
        LinearLayout dialogCustom = dialog.findViewById(R.id.dialog_event);


        for (int i = 0; i < arrJob.length(); i++) {
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
        int age = MainActivity.saveGame.getAge() + 1;

        if (age == 18) {
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
        if (arrAge.length() == 0) {
            MainActivity.createNotification(MainActivity.saveGame.getAvatar(), "Năm nay không có sự kiện gì đặc biệt", view.getContext());
            initNewAge();
            addAgeHTML(age);
            contentHtml = MainActivity.saveGame.getDetailActivity();
            contentHtml += "Năm nay không có sự kiện gì đặc biệt<br>";
            txtContent.setText(android.text.Html.fromHtml(contentHtml));
            MainActivity.saveGame.saveDetailActivity(contentHtml);
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

    void dialogEvent(JSONObject[] object, boolean[] isSelection, String title, String[] event) throws JSONException {
        jsonResult = object[0];
        if (!isSelection[0]) {
            JSONArray arr = jsonResult.getJSONArray("event");
            jsonResult = arr.getJSONObject(new Random().nextInt(arr.length()));
            int age = MainActivity.saveGame.getAge() + 1;
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
        Dialog dialog = new Dialog(view.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_event_result);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogResultAnimation;


        //Lay gia tri gan vao dialog
        LinearLayout dialogResult = dialog.findViewById(R.id.dialog_event_result);
        Button btnOke = dialog.findViewById(R.id.buttonDialogEventOke);
        TextView txtTitle = dialog.findViewById(R.id.textviewDialogEventTitle);
        TextView txtContent = dialog.findViewById(R.id.textviewDialogEventContent);
        TextView txtHappy = dialog.findViewById(R.id.textviewResultHappy);
        TextView txtHealth = dialog.findViewById(R.id.textviewResultHealth);
        TextView txtSmart = dialog.findViewById(R.id.textviewResultSmart);
        TextView txtAppearance = dialog.findViewById(R.id.textviewResultAppearance);
        TextView txtAssets = dialog.findViewById(R.id.textviewResultMoney);

        txtTitle.setText(title);
        txtContent.setText(jsonResult.getString("event"));
        contentHtml += txtContent.getText().toString() + "<br>";

        int value = 0;
        value = jsonResult.getInt("happy");
        if (value == 0) {
            dialogResult.removeView(dialog.findViewById(R.id.linearHappy));
        } else {
            toString(value, txtHappy);
            prbHappy.setProgress(prbHappy.getProgress() + value);
            this.txtHappy.setText(prbHappy.getProgress() + "%");
        }
        value = jsonResult.getInt("health");
        if (value == 0) {
            dialogResult.removeView(dialog.findViewById(R.id.linearHealth));
        } else {
            toString(value, txtHealth);
            prbHealth.setProgress(prbHealth.getProgress() + value);
            this.txtHealth.setText(prbHealth.getProgress() + "%");
        }
        value = jsonResult.getInt("smart");
        if (value == 0) {
            dialogResult.removeView(dialog.findViewById(R.id.linearSmart));
        } else {
            toString(value, txtSmart);
            prbSmart.setProgress(prbSmart.getProgress() + value);
            this.txtSmart.setText(prbSmart.getProgress() + "%");
        }
        value = jsonResult.getInt("appearance");
        if (value == 0) {
            dialogResult.removeView(dialog.findViewById(R.id.linearAppearance));
        } else {
            toString(value, txtAppearance);
            prbAppearance.setProgress(prbAppearance.getProgress() + value);
            this.txtAppearance.setText(prbAppearance.getProgress() + "%");
        }
        value = jsonResult.getInt("assets");
        if (value == 0) {
            dialogResult.removeView(dialog.findViewById(R.id.linearMoney));
        } else {
            toString(value, txtAssets);
            money += value;
            this.txtMoney.setText(money + " VND");
            MainActivity.saveGame.saveMoney(money);
        }
        MainActivity.saveGame.savePlayerInfo(prbHappy.getProgress(), prbHealth.getProgress(), prbSmart.getProgress(), prbAppearance.getProgress());
        MainActivity.saveGame.saveDetailActivity(contentHtml);
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
                    if (MainActivity.saveGame.getHealth() <= 0)
                        MainActivity.checkMySelf(view.getContext(), "\"Qua đời vì sức khỏe yếu kéo dài, không chịu nổi những biến cố trong cuộc sống\"");
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
        String sick = jsonResult.getString("sick");
        if (!sick.equals("")) //Co benh
        {
            ArrayList<Sick> arrSick = MainActivity.saveGame.getSick();
            int index = -1;
            for (int i = 0; i < arrSick.size(); i++) {
                if (arrSick.get(i).getSickName().equals(sick)) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                //Toast.makeText(this, "cant find sick name", Toast.LENGTH_SHORT).show();
                return;
            }
            arrSick.get(index).setSick(true);
            MainActivity.saveGame.saveSick(arrSick);
        }

    }


    void dialogJobEvent(String title) throws JSONException {
        //Toast.makeText(this, "ssdfsgsdgsdgd", Toast.LENGTH_LONG).show();
        if (!jsonResult.getBoolean("selection")) {
            JSONArray arr = jsonResult.getJSONArray("event");
            jsonResult = arr.getJSONObject(new Random().nextInt(arr.length()));
            MainActivity.saveGame.saveSalary(jsonResult.getInt("salary"));
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

        //Toast.makeText(this, "Asset", Toast.LENGTH_SHORT).show();
        //Anh xa
        TextView txtContent = dialog.findViewById(R.id.textviewAssetContent);
        ImageView imgAsset = dialog.findViewById(R.id.imageviewAsset);
        TextView txtAssetName = dialog.findViewById(R.id.textviewAsset);
        Button btnAccept = dialog.findViewById(R.id.buttonAssetAccept);
        Button btnCancel = dialog.findViewById(R.id.buttonAssetCancel);

        int imageID = getResources().getIdentifier(jsonResult.getString("asset"), "drawable", view.getContext().getPackageName());
        //Gan gia tri
        txtContent.setText(jsonResult.getString("event"));
        imgAsset.setImageResource(imageID);
        txtAssetName.setText(jsonResult.getString("name"));

        JSONArray arrSelect = jsonResult.getJSONArray("select");

        //Kiem tra xem co so huu do vat nay hay chua
        boolean isOwn = false;
        ArrayList<Food> arrProduct = MainActivity.saveGame.getAsset();
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
                    MainActivity.createNotification(R.drawable.cancel, "Bạn đã sở hữu món đồ này đâu mà đồng ý!!!", view.getContext());
                else {
                    try {
                        dialog.dismiss();
                        JSONArray arr = arrSelect.getJSONArray(0);
                        jsonResult = arr.getJSONObject(new Random().nextInt(arr.length()));
                        dialogEventResult(title, false);
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
                    //Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                    arr = arrSelect.getJSONArray(1);
                    jsonResult = arr.getJSONObject(new Random().nextInt(arr.length()));
                    dialog.dismiss();
                    dialogEventResult(title, false);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        dialog.show();
    }

    void jobEvent() throws JSONException {
        int currentSkill = MainActivity.saveGame.getSkill();
        int addSkill = jsonResult.getInt("skill");
        MainActivity.saveGame.saveSkill(currentSkill + addSkill);

        JSONArray arrJob = jsonJob.getJSONArray("event");
        for (int i = 0; i < arrJob.length(); i++) {
            //Toast.makeText(MainActivity.this, "error1", Toast.LENGTH_SHORT).show();
            jsonResult = arrJob.getJSONObject(i);
            int require = jsonResult.getInt("require");
            if (currentSkill < require && currentSkill + addSkill >= require) {
                if (!jsonResult.getString("asset").equals(""))
                    dialogJobEventWithAsset("Công Việc");
                else if (jsonResult.getBoolean("selection")) {
                    dialogJobEvent("Công việc");
                } else {
                    MainActivity.saveGame.saveSalary(jsonResult.getInt("salary"));
                    dialogEventResult("Công việc", false);
                    //Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    Button addButton(LinearLayout dialogCustom, String text) {
        Button btn = new Button(dialogCustom.getContext());
        btn.setText(text);
        btn.setTextSize(15);
        btn.setBackgroundResource(R.drawable.custom_button_menu);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(800, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 10;
        params.topMargin = 10;
        params.gravity = Gravity.CENTER_HORIZONTAL;
        btn.setLayoutParams(params);
        btn.setTextColor(Color.argb(255, 16, 54, 103));
        dialogCustom.addView(btn);

        return btn;
    }

    Dialog createAssetDialog() {
        //Dinh dang dialog
        Dialog dialog = new Dialog(view.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_job_asset);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogEventAnimation;
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        return dialog;
    }

    Dialog createDialog(String title, String event) {
        //Dinh dang dialog
        Dialog dialog = new Dialog(view.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_event);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogEventAnimation;
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
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


    void readEvent() throws JSONException {
        String jsonEvent = null;
        try {
            InputStream inputStream = view.getContext().getAssets().open("age_event.json");
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
            InputStream inputStream = view.getContext().getAssets().open("friend.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonEvent = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        MainActivity.arrFriend = new ArrayList<>();
        JSONArray jsonFriend = new JSONArray(jsonEvent);

        for (int i = 0; i < jsonFriend.length(); i++) {
            JSONObject object = jsonFriend.getJSONObject(i);
            String name = object.getString("name");
            int age = MainActivity.saveGame.getAge() + (new Random().nextInt(5) - 2);
            String avatar = object.getString("avatar");

            boolean isBoy = false;
            if (object.getString("gender").equals("boy")) {
                isBoy = true;
            }

            MainActivity.arrFriend.add(new QuanHe(name, age, new Random().nextInt(30) + 30,
                    NameOfRelationship.Friend, getResources().getIdentifier(avatar, "drawable", view.getContext().getPackageName()), isBoy));
        }
        setPeopleAvatar(MainActivity.arrFriend);
    }

    void readJob() throws JSONException {

        String jsonEvent = null;
        try {
            InputStream inputStream = view.getContext().getAssets().open("job.json");
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



    void loadGame() {
        contentHtml = MainActivity.saveGame.getDetailActivity();
        txtContent.setText(android.text.Html.fromHtml(contentHtml));
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });


        imgAvatar.setImageResource(MainActivity.saveGame.getAvatar());
        prbAppearance.setProgress(MainActivity.saveGame.getAppearance());
        prbHappy.setProgress(MainActivity.saveGame.getHappy());
        prbHealth.setProgress(MainActivity.saveGame.getHealth());
        prbSmart.setProgress(MainActivity.saveGame.getSmart());

        txtAppearance.setText(prbAppearance.getProgress() + "%");
        txtHappy.setText(prbHappy.getProgress() + "%");
        txtSmart.setText(prbSmart.getProgress() + "%");
        txtHealth.setText(prbHealth.getProgress() + "%");

        money = MainActivity.saveGame.getMoney();
        txtMoney.setText(money + "VND");
        txtName.setText(MainActivity.saveGame.getName());
        txtJob.setText(MainActivity.saveGame.getJob());
        arrRelationship = MainActivity.saveGame.getRelationship();

        changeProgressBackground(prbAppearance);
        changeProgressBackground(prbHappy);
        changeProgressBackground(prbHealth);
        changeProgressBackground(prbSmart);

        setUserInfo();
    }

    private void setUserInfo() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txtUserName = getActivity().findViewById(R.id.textViewUserName);
                txtPlayerName = getActivity().findViewById(R.id.textViewPlayerName);
                imgUserAvatar = getActivity().findViewById(R.id.imageViewUserAvatar);

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                txtUserName.setText(user.getDisplayName());
                txtPlayerName.setText(MainActivity.saveGame.getName());
                Picasso.get().load(user.getPhotoUrl()).into(imgUserAvatar);
            }
        }, 2000);
    }

    void changeWork() throws JSONException {
        switch (MainActivity.saveGame.getJob()) {
            case "Lập trình viên":
                jsonJob = jsonAllJob.getJSONObject("coder");
                break;
            case "Trẻ trâu":
                jsonJob = jsonAllJob.getJSONObject("student");
                break;
        }
    }

    // init tam thoi
    void init(String name, boolean isBoy) throws JSONException {
        String json = null;
        try {
            InputStream inputStream = view.getContext().getAssets().open("parent.json");
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

        MainActivity.saveGame.saveRelationship(arrRelationship);
        this.arrRelationship = arrRelationship;
        MainActivity.saveGame.saveNewFriendInYear(0);
        MainActivity.saveGame.saveNumberOfFriends(0);
        MainActivity.saveGame.saveNumberOfGirlFriend(0);

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

        MainActivity.saveGame.saveGender(isBoy);
        int avatarID = getResources().getIdentifier("boy_0", "drawable", view.getContext().getPackageName());
        if (!isBoy)
            avatarID = getResources().getIdentifier("girl_0", "drawable", view.getContext().getPackageName());
        imgAvatar.setImageResource(avatarID);
        MainActivity.saveGame.saveAvatar(avatarID);
        MainActivity.saveGame.saveAge(0);
        MainActivity.saveGame.saveBirthDay(format.format(date));
        MainActivity.saveGame.saveMoney(50000);
        MainActivity.saveGame.saveDetailActivity(contentHtml);
        MainActivity.saveGame.saveName(name);
        MainActivity.saveGame.saveJob("Trẻ trâu");
        MainActivity.saveGame.saveSkill(0);
        MainActivity.saveGame.saveExercise(0);
        MainActivity.saveGame.saveJogging(0);
        MainActivity.saveGame.saveJogging(0);
        MainActivity.saveGame.saveCrime(0);
        txtJob.setText(MainActivity.saveGame.getJob());
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
        MainActivity.saveGame.savePlayerInfo(prbHappy.getProgress(), prbHealth.getProgress(), prbSmart.getProgress(), prbAppearance.getProgress());

        ArrayList<Sick> arrSick = new ArrayList<>();
        arrSick.add(new Sick(false, "đau răng", "Nha sĩ", "nhasi", 10));
        arrSick.add(new Sick(false, "đau mắt", "Bác sĩ mắt", "mat", 10));
        arrSick.add(new Sick(false, "tai mũi họng", "Bác sĩ tai mũi họng", "taimuihong", 100));
        arrSick.add(new Sick(false, "mẫn ngứa", "Bác sĩ da liễu", "dalieu", 10));
        arrSick.add(new Sick(false, "cảm", "Bác sĩ cảm sốt", "camsot", 20));
        arrSick.add(new Sick(false, "trĩ", "Bác sĩ trĩ", "tri", 30));
        arrSick.add(new Sick(false, "sốt xuất huyết", "Bác sĩ cảm sốt", "sotxuathuyet", 100));

        MainActivity.saveGame.saveSick(arrSick);
    }

    void initNewAge() {
        //Toast.makeText(this, "new age", Toast.LENGTH_SHORT).show();
        int age = MainActivity.saveGame.getAge() + 1;
        MainActivity.saveGame.saveAge(age);
        MainActivity.saveGame.saveExercise(0);
        MainActivity.saveGame.saveJogging(0);
        MainActivity.saveGame.saveJogging(0);
        MainActivity.saveGame.saveLibrary(0);
        MainActivity.saveGame.saveNewFriendInYear(0);
        MainActivity.saveGame.saveNhaHang(0);
        MainActivity.saveGame.saveNuocEp(0);
        MainActivity.saveGame.saveTraSua(0);
        MainActivity.saveGame.saveCaPhe(0);
        MainActivity.saveGame.saveRuou(0);
        MainActivity.saveGame.saveBia(0);
        MainActivity.saveGame.saveHamBurGer(0);
        MainActivity.saveGame.saveBanhMi(0);
        MainActivity.saveGame.saveMy(0);
        MainActivity.saveGame.saveTraiCay(0);
        MainActivity.saveGame.savePizza(0);
        MainActivity.saveGame.saveLau(0);
        MainActivity.saveGame.saveCom(0);
        MainActivity.saveGame.saveHaiSan(0);
        MainActivity.saveGame.saveGa(0);
        MainActivity.saveGame.saveRauCu(0);
        MainActivity.saveGame.saveKeo(0);
        MainActivity.saveGame.saveFastFood(0);
        MainActivity.saveGame.saveCrime(0);

        for (int i = 0; i < arrRelationship.size(); i++) {
            QuanHe friend = arrRelationship.get(i);
            friend.setInteraction(0);
            friend.setTuoi(friend.getTuoi() + 1); // Them tuoi
            if (friend.getQuanHe() == NameOfRelationship.Friend ||
                    friend.getQuanHe() == NameOfRelationship.GirlFriend ||
                    friend.getQuanHe() == NameOfRelationship.BoyFriend) {
                arrRelationship.get(i).setDoThanMat(friend.getDoThanMat() - 20); //Giam moi quan he
            }
        }
        setPeopleAvatar(arrRelationship);
        MainActivity.saveGame.saveRelationship(arrRelationship);

        for (int i = 0; i < MainActivity.arrFriend.size(); i++) {
            MainActivity.arrFriend.get(i).setTuoi(MainActivity.arrFriend.get(i).getTuoi() + 1);
        }
        setPeopleAvatar(MainActivity.arrFriend);

        setAvatar();
        dialogLostFriend(0);

        //xet suc khoe
        int health = MainActivity.saveGame.getHealth();
        if (prbHappy.getProgress() < 30) //Kiem tra xem co van de ve tam li hay khong (happy<30)
        {
            health -= (30 - prbHappy.getProgress());
        }
        health -= (int) (MainActivity.saveGame.getAge() / 4); //Tru suc khoe theo do tuoi, tuoi cang cao tru cang nhieu
        String reason = "";
        if (health <= 0)
            reason = "\"Qua đời vì tuổi già sức yếu\"";
        else {
            ArrayList<Sick> arrSick = MainActivity.saveGame.getSick(); //Kiem tra co benh ma khong chua hay khong
            for (int i = 0; i < arrSick.size(); i++) {
                if (arrSick.get(i).isSick()) {
                    health -= arrSick.get(i).getHealth();
                }
            }
            if (health <= 0)
                reason = "\"Qua đời vì sức khỏe yếu kéo dài, bệnh tật không được chữa trị kịp thời\"";
        }
        //Luu suc khoe lai
        MainActivity.saveGame.savePlayerInfo(MainActivity.saveGame.getHappy(),
                health,
                MainActivity.saveGame.getSmart(),
                MainActivity.saveGame.getAppearance());
        prbHealth.setProgress(health);
        txtHealth.setText(prbHealth.getProgress() + "%");
        changeProgressBackground(prbHealth);
        MainActivity.checkMySelf(view.getContext(), reason);
    }

    void setPeopleAvatar(ArrayList<QuanHe> array) {
        for (int i = 0; i < array.size(); i++) {
            QuanHe quanHe = array.get(i);
            int id = quanHe.getHinhAnh();
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

    void setAvatar() {
        int age = MainActivity.saveGame.getAge();
        String avatarName = "";

        if (MainActivity.saveGame.getGender()) //Boy
        {
            if (age == 2) avatarName = "boy";
            else if (age == 5) {
                avatarName = "boy_2";
            } else if (age == 10) {
                avatarName = "boy_3";
            } else if (age == 15) {
                avatarName = "boy_4";
            } else if (age == 20) {
                avatarName = "boy_5";
            } else if (age == 30) {
                avatarName = "boy_6";
            } else if (age == 40) {
                avatarName = "boy_7";
            } else if (age >= 60) {
                avatarName = "boy_8";
            }
        } else {
            if (age == 2) avatarName = "girl";
            else if (age == 5) {
                avatarName = "girl_2";
            } else if (age == 10) {
                avatarName = "girl_3";
            } else if (age == 15) {
                avatarName = "girl_4";
            } else if (age == 20) {
                avatarName = "girl_5";
            } else if (age == 30) {
                avatarName = "girl_6";
            } else if (age == 40) {
                avatarName = "girl_7";
            } else if (age >= 60) {
                avatarName = "girl_8";
            }
        }

        if (avatarName.equals(""))
            return;
        int id = getResources().getIdentifier(avatarName, "drawable", view.getContext().getPackageName());
        MainActivity.saveGame.saveAvatar(id);
        imgAvatar.setImageResource(id);
    }

    void dialogLostFriend(int index) {
        if (index == arrRelationship.size()) {
            MainActivity.saveGame.saveRelationship(arrRelationship);
            return;
        }
        QuanHe friend = arrRelationship.get(index);
        if (friend.getDoThanMat() <= 0) {
            Dialog dialog = MainActivity.createNotification(R.drawable.cancel, "Môi quan hệ giữa bạn và " + friend.getHoten() + " đã đổ vỡ.", view.getContext());
            Button btnOke = dialog.findViewById(R.id.buttonNotificationtOke);
            arrRelationship.remove(index);
            MainActivity.saveGame.saveNumberOfFriends(MainActivity.saveGame.getNumberOfFriends() - 1);
            btnOke.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    dialogLostFriend(index);
                }
            });
        } else {
            dialogLostFriend(index + 1);
        }
    }

    void addAgeHTML(int age) {
        contentHtml += "<h5> <font color=\"blue\">Tuổi " + age + "</font></h5>";
        txtContent.setText(android.text.Html.fromHtml(contentHtml));
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
        MainActivity.saveGame.saveDetailActivity(contentHtml);
    }

    void toString(int value, TextView txtResult) {
        if (value > 0)
            txtResult.setText("+ " + value);
        else if (value < 0) {
            txtResult.setText("- " + (-1 * value));
            txtResult.setTextColor(Color.RED);
        }
    }

    

    void changeProgressBackground(ProgressBar pb) {
        int progress = pb.getProgress();
        if (progress >= 80)
            pb.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progressbar));
        else if (progress < 80 && progress > 30)
            pb.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progressbar_medium));
        else pb.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progressbar_low));
        //Toast.makeText(this, "a", Toast.LENGTH_SHORT).show();
    }

    void dialogUniversity() {
        Dialog dialog = new Dialog(view.getContext());
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_university);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogResultAnimation;

        //Anh xa
        ListView lvUni = dialog.findViewById(R.id.listViewUni);
        ArrayList<University> arrUniversity = new ArrayList<>();

        arrUniversity.add(new University(R.drawable.angiang, "Đại học An Giang", 1, 40));
        arrUniversity.add(new University(R.drawable.uit, "Đại học Công nghệ Thông tin", 5, 60));
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
        UniversityAdapter adapter = new UniversityAdapter(view.getContext(), R.layout.university_line, arrUniversity);
        lvUni.setAdapter(adapter);

        lvUni.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (arrUniversity.get(position).getName().equals("Không học")) {
                    contentHtml += "Bạn quyết định không học đại học<br/>";
                    MainActivity.saveGame.saveJob("Thất nghiệp");
                } else {
                    contentHtml += "Bạn chọn vào học tại trường " + arrUniversity.get(position).getName() + "<br/>";
                    MainActivity.saveGame.saveJob("Sinh viên");
                }
                MainActivity.saveGame.saveDetailActivity(contentHtml);
                txtContent.setText(android.text.Html.fromHtml(contentHtml));
                txtJob.setText(MainActivity.saveGame.getJob());
                dialog.dismiss();
                initNewAge();
            }
        });

        dialog.show();
    }

    private void AnhXa() {
        btnRelationship = (Button) view.findViewById(R.id.buttonRelationship);
        btnActivity = (Button) view.findViewById(R.id.buttonActivity);

        imgAvatar = view.findViewById(R.id.imageAvatar);
        txtContent = view.findViewById(R.id.textViewDetail);
        txtAppearance = view.findViewById(R.id.txtAppearance);
        txtHappy = view.findViewById(R.id.txtHappy);
        txtSmart = view.findViewById(R.id.txtSmart);
        txtHealth = view.findViewById(R.id.txtHealth);
        txtMoney = view.findViewById(R.id.textviewMoney);
        txtName = view.findViewById(R.id.textviewName);
        txtJob = view.findViewById(R.id.textviewJob);

        prbAppearance = view.findViewById(R.id.progressbarAppearance);
        prbHappy = view.findViewById(R.id.progressbarHappy);
        prbHealth = view.findViewById(R.id.progressbarHealth);
        prbSmart = view.findViewById(R.id.progressbarSmart);

        ibtnAddAge = view.findViewById(R.id.imagebuttonAddAge);
        btnAssets = view.findViewById(R.id.buttonAssets);
        btnWork = view.findViewById(R.id.buttonInfant);
        scrollView = view.findViewById(R.id.scrollViewText);


        if (MainActivity.saveGame == null) {
            MainActivity.saveGame = new SaveGame();
            //Toast.makeText(this, "Save game null", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main, container, false);
        AnhXa();

        MainActivity.saveGame.setOnLoaded(new LoadDone() {
            @Override
            public void onLoaded() {
                //Toast.makeText(MainActivity.this, "Loaded " + time, Toast.LENGTH_SHORT).show();
                try {
                    if (MainActivity.saveGame.getDetailActivity().equals("")) {
                        //startActivityForResult(new Intent(getActivity(), CreateName.class), REQUEST_CODE_INIT);
                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.startInit();
                    }
                    else {
                        loadGame();
                        readEvent();
                        readJob();
                        readFriend();
                        changeWork();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        //Toast.makeText(this, "Load Data", Toast.LENGTH_SHORT).show();
        MainActivity.saveGame.loadData();

        ibtnAddAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(view.getContext(), "new age", Toast.LENGTH_SHORT).show();
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
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if (requestCode == REQUEST_CODE_INIT && resultCode == RESULT_OK && data != null) {
            String name = data.getStringExtra("name");
            boolean isBoy = data.getBooleanExtra("gender", false);

            //Toast.makeText(getActivity(), "INIT", Toast.LENGTH_SHORT).show();
            try {
                init(name, isBoy);
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
}
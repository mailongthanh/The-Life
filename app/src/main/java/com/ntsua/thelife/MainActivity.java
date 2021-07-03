package com.ntsua.thelife;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

<<<<<<< HEAD
    Button btnActivity, btnRelationship, btnWork, btnAssets;
    ImageView imgAvatar;
    ImageButton ibtnAddAge;
    ProgressBar prbHappy, prbHealth, prbSmart, prbAppearance;
    ScrollView scrollView;
    TextView txtContent, txtHappy, txtHealth, txtSmart, txtAppearance, txtMoney, txtName, txtJob, txtScrollviewContent;
    SharedPreferences preferences;
    static public SaveGame saveGame;
    static public  ArrayList<QuanHe> arrFriend;
    public static final String year ="";
    ArrayList<QuanHe> arrRelationship;
    JSONArray arrJsonAge;
    JSONObject jsonResult, jsonAllJob, jsonJob;
    String contentHtml;
    Bundle bundle;
    int money, TempAge, prisonYear = 0;
=======
>>>>>>> d3245fd50e62a2b85fbf1cc6ce288ed922a017a6
    long currentTime = 0;
    Toast backToast;
    static public SaveGame saveGame;
    static public ArrayList<QuanHe> arrFriend;

    Toolbar toolbar;
    DrawerLayout drawerLayout;

    private static final int FRAGMENT_MAIN = 1;
    private static final int FRAGMENT_SHARE = 2;
    private static final int FRAGMENT_RATE = 3;
    int REQUEST_CODE_INIT = 123;

<<<<<<< HEAD
    @SuppressLint("ResourceAsColor")
=======
    int currentFragment = FRAGMENT_MAIN;
    FragmentMain fragmentMain;

    //auth.signOut();
    //LoginManager.getInstance().logOut();

>>>>>>> d3245fd50e62a2b85fbf1cc6ce288ed922a017a6
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD
        AnhXa();

        Intent intent = getIntent();
        bundle = intent.getExtras();

        if(bundle!=null)
        {
            prisonYear = bundle.getInt(year);
        }

        if(prisonYear!=0)
        {
            createNotification(R.drawable.police,"Bạn đã bị bắt, bạn cần phải xám hối cho những hành vi tội lỗi của mình, hiện tại bạn sẽ không thể thực hiện được một số hoạt động thường ngày của mình",this);
            TempAge = saveGame.getAge();
            scrollView.setBackgroundResource(R.drawable.background_prison);
            txtScrollviewContent.setVisibility(View.INVISIBLE);
            btnAssets.setEnabled(false);
            btnActivity.setEnabled(false);
            btnRelationship.setEnabled(false);
        }
        //Test
        //startActivityForResult(new Intent(MainActivity.this,  CreateName.class), REQUEST_CODE_INIT);
        try {
             if (saveGame.getDetailActivity().equals("")) {
                startActivityForResult(new Intent(MainActivity.this, CreateName.class), REQUEST_CODE_INIT);
             } else {
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
=======

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
>>>>>>> d3245fd50e62a2b85fbf1cc6ce288ed922a017a6

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

<<<<<<< HEAD
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

    void DeathofRelation()
    {
        Random random = new Random();
        int happy = saveGame.getHappy();
        NameOfRelationship dad = NameOfRelationship.Dad;
        NameOfRelationship mom = NameOfRelationship.Mom;
        int image = getResources().getIdentifier("death","drawable", this.getPackageName());

        for(int i = 0; i< arrRelationship.size();i++)
        {
            QuanHe Relatives = arrRelationship.get(i);
            if(Relatives.getTuoi() >= 75 && Relatives.getTuoi() <=90 && Relatives.HinhAnh != image)
            {
                int flag = random.nextInt(4);
                if(Relatives.getTuoi() == 90) flag = 1;
                if(flag == 1) {
                    Relatives.HinhAnh = image;
                    if(Relatives.getQuanHe() == dad || Relatives.getQuanHe() == mom) {
                        createNotification(Relatives.getHinhAnh(), Relatives.getQuanHe() + " của bạn đã mất", this);
                    } else createNotification(Relatives.getHinhAnh(),Relatives.getQuanHe() + " của bạn" + Relatives.getHoten() + " đã mất",this);
                    prbHappy.setProgress(prbHappy.getProgress() - 30);
                    this.txtHappy.setText(prbHappy.getProgress() + "%");
                }
            }
        }
        saveGame.saveRelationship(arrRelationship);
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
        int year = TempAge + prisonYear - age;
        if(prisonYear!=0) {
            if (age >= TempAge + prisonYear) {
                btnRelationship.setEnabled(true);
                btnActivity.setEnabled(true);
                scrollView.setBackgroundColor(Color.WHITE);
                txtScrollviewContent.setVisibility(View.VISIBLE);
                btnAssets.setEnabled(true);
                prisonYear = 0;

                if(age > TempAge + prisonYear)
                {
                    createNotification(R.drawable.police,"Bạn đã được thả, hi vọng bạn đã nhận ra tội lỗi của mình", this);
                }
            }
            if(age < TempAge + prisonYear ) {
                createNotification(R.drawable.police, "Bạn còn "+ year +" năm tù trước khi được thả tự do" , this);
            }
            initNewAge();
            addAgeHTML(age);
        }
        else {
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
                createNotification(saveGame.getAvatar(), "Năm nay không có sự kiện gì đặc biệt", this);
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
=======
        if  (fragmentMain == null)
            fragmentMain = new FragmentMain();
        replaceFragment(fragmentMain);
    }



    public void startInit()
    {
        startActivityForResult(new Intent(this, CreateName.class), REQUEST_CODE_INIT);
>>>>>>> d3245fd50e62a2b85fbf1cc6ce288ed922a017a6
    }

    public static Dialog createNotification(int image, String content, Context context) {
        //Tao dialog
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_notification);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogResultAnimation;

        //Anh xa
        TextView txtTitle = dialog.findViewById(R.id.textviewNotificationTitle);
        TextView txtContent = dialog.findViewById(R.id.textviewNotificationContent);
        ImageView imageView = dialog.findViewById(R.id.imageviewNotification);
        Button btnOke = dialog.findViewById(R.id.buttonNotificationtOke);

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
<<<<<<< HEAD
                try {
                    if (saveGame.getHealth() <= 0)
                        checkMySelf(MainActivity.this, "\"Qua đời vì sức khỏe yếu kéo dài, không chịu nổi những biến cố trong cuộc sống\"");
                    else if (isAgeEvent)
                    {
                        initNewAge();
                        DeathofRelation();
                    }
                    else jobEvent();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
=======
>>>>>>> d3245fd50e62a2b85fbf1cc6ce288ed922a017a6
            }
        });

        dialog.show();

        return dialog;
    }


    static public void checkMySelf(Context context, String content) {
        if (saveGame.getHealth() <= 0) {
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
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
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
                    txtAsset.setText("Số tiền để lại: " + String.format("%,d", saveGame.getMoney() * 1000) + " VND");
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
        if (saveGame.getHappy() < 30) {
            createNotification(R.drawable.heartbeat, "Bạn có dấu hiệu bị trầm cảm, tốt nhất nên đến bác sĩ để chưa trị", context);
        }
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else {
            if (currentTime + 2000 > System.currentTimeMillis()) {
                backToast.cancel();
                this.finishAffinity();
            } else {
                backToast = Toast.makeText(this, "Ấn lần nữa để thoát", Toast.LENGTH_SHORT);
                backToast.show();
            }
            currentTime = System.currentTimeMillis();
        }
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_main:
                if (currentFragment != FRAGMENT_MAIN)
                {
                    if  (fragmentMain == null)
                        fragmentMain = new FragmentMain();
                    replaceFragment(fragmentMain);
                    currentFragment = FRAGMENT_MAIN;
                }
                break;
            case R.id.menu_share:
                if (currentFragment != FRAGMENT_SHARE)
                {
                    FragmentShare share = new FragmentShare();
                    replaceFragment(share);
                    currentFragment = FRAGMENT_SHARE;

//                    FragmentShare fragmentShare = (FragmentShare) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
//
//                    ConstraintLayout layout = findViewById(R.id.content_main);
//                    Bitmap bitmap = takeScreenshotForView(layout);
//                    fragmentShare.imgTest.setImageBitmap(bitmap);
                }
                break;
<<<<<<< HEAD
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
        saveGame.saveJob("Trẻ trâu");
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

        int health = saveGame.getHealth();
        if (prbHappy.getProgress() < 30)
        {
            health -= (30 - prbHappy.getProgress());
        }
        health -= (int) (saveGame.getAge() / 4);
        saveGame.savePlayerInfo(saveGame.getHappy(), health, saveGame.getSmart(), saveGame.getAppearance());
        prbHealth.setProgress(health);
        txtHealth.setText(prbHealth.getProgress() + "%");
        changeProgressBackground(prbHealth);

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
=======
            case R.id.menu_rank:
                if (currentFragment != FRAGMENT_RATE)
                {
                    replaceFragment(new FragmentRate());
                    currentFragment = FRAGMENT_RATE;
>>>>>>> d3245fd50e62a2b85fbf1cc6ce288ed922a017a6
                }
        }

<<<<<<< HEAD
    void setPeopleAvatar(ArrayList<QuanHe> array)
    {
        for (int i=0; i<array.size(); i++) {
            QuanHe quanHe = array.get(i);
            int id = quanHe.HinhAnh;

            if(id == R.drawable.death)
            {
                continue;
            }

            if (quanHe.isBoy() && id != R.drawable.death) //Boy
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
=======
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
>>>>>>> d3245fd50e62a2b85fbf1cc6ce288ed922a017a6
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void gotoAsset(View view) {
        startActivity(new Intent(view.getContext(), Asset.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    public void gotoActivity(View view) {
        startActivity(new Intent(view.getContext(), HoatDong.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    public void gotoRelationship(View view) {
        startActivity(new Intent(view.getContext(), RelationShip.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //android.app.Fragment fragmentMain = getFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        if  (fragmentMain == null)
            fragmentMain = new FragmentMain();
        fragmentMain.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

<<<<<<< HEAD
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
        txtScrollviewContent = findViewById(R.id.textViewDetail);


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
=======
    public Bitmap takeScreenshotForView(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(view.getHeight(), View.MeasureSpec.EXACTLY));
        view.layout((int) view.getX(), (int) view.getY(), (int) view.getX() + view.getMeasuredWidth(), (int) view.getY() + view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
>>>>>>> d3245fd50e62a2b85fbf1cc6ce288ed922a017a6
    }
}


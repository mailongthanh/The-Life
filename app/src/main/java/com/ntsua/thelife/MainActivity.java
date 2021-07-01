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

    int currentFragment = FRAGMENT_MAIN;
    FragmentMain fragmentMain;

    //auth.signOut();
    //LoginManager.getInstance().logOut();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        if  (fragmentMain == null)
            fragmentMain = new FragmentMain();
        replaceFragment(fragmentMain);
    }



    public void startInit()
    {
        startActivityForResult(new Intent(this, CreateName.class), REQUEST_CODE_INIT);
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
        saveGame.saveTuVi(0);
        saveGame.saveBoiSN(0);
        saveGame.saveBoiTinh(0);
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
=======
            case R.id.menu_rank:
                if (currentFragment != FRAGMENT_RATE)
                {
                    replaceFragment(new FragmentRate());
                    currentFragment = FRAGMENT_RATE;
>>>>>>> dd128fb2c2d832fd636d6aeb5fa01a6e773d446c
                }
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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

    public Bitmap takeScreenshotForView(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(view.getHeight(), View.MeasureSpec.EXACTLY));
        view.layout((int) view.getX(), (int) view.getY(), (int) view.getX() + view.getMeasuredWidth(), (int) view.getY() + view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }
}


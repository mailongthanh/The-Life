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
            MainActivity.saveGame.saveNamTu(0);
            MainActivity.saveGame.saveAsset(null);

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
            case R.id.menu_rank:
                if (currentFragment != FRAGMENT_RATE)
                {
                    replaceFragment(new FragmentRate());
                    currentFragment = FRAGMENT_RATE;
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


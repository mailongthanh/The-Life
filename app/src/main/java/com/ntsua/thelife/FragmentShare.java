package com.ntsua.thelife;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentShare#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentShare extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentShare() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentShare.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentShare newInstance(String param1, String param2) {
        FragmentShare fragment = new FragmentShare();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    View view;
    TextView txtTotal, txtHappy, txtHealth, txtSmart, txtAppearance, txtMoney, txtName, txtJob;
    ProgressBar prbHappy, prbHealth, prbSmart, prbAppearance;
    ImageView imgAvatar;
    ShareButton btnShare;
    Bitmap bitmapShare;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        view = inflater.inflate(R.layout.fragment_share, container, false);
        AnhXa();
        loadData();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RelativeLayout relativeLayout = view.findViewById(R.id.relativeData);
                bitmapShare = takeScreenshotForView(relativeLayout);
                SharePhoto photo = new  SharePhoto.Builder()
                        .setBitmap(bitmapShare)
                        .build();

                SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();

                btnShare.setShareContent(sharePhotoContent);
            }
        }, 1000);



//        RelativeLayout relativeLayout = view.findViewById(R.id.relativeData);
//
//        SharePhoto photo = new  SharePhoto.Builder()
//                .setBitmap(bitmapShare)
//                .build();
//
//        SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder()
//                .addPhoto(photo)
//                .build();
//
//        btnShare.setShareContent(sharePhotoContent);
//        btnShare.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RelativeLayout relativeLayout = view.findViewById(R.id.relativeData);
//                bitmapShare = takeScreenshotForView(relativeLayout);
//                SharePhoto photo = new  SharePhoto.Builder()
//                        .setBitmap(bitmapShare)
//                        .build();
//
//                SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder()
//                        .addPhoto(photo)
//                        .build();
//
//                btnShare.setShareContent(sharePhotoContent);
//            }
//        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void loadData() {
        imgAvatar.setImageResource(MainActivity.saveGame.getAvatar());
        prbAppearance.setProgress(MainActivity.saveGame.getAppearance());
        prbHappy.setProgress(MainActivity.saveGame.getHappy());
        prbHealth.setProgress(MainActivity.saveGame.getHealth());
        prbSmart.setProgress(MainActivity.saveGame.getSmart());

        txtAppearance.setText(prbAppearance.getProgress() + "%");
        txtHappy.setText(prbHappy.getProgress() + "%");
        txtSmart.setText(prbSmart.getProgress() + "%");
        txtHealth.setText(prbHealth.getProgress() + "%");

        txtTotal.setText("Tổng tài sản: " + totalAsset() + "K VND");

        txtMoney.setText(MainActivity.saveGame.getMoney() + "K VND");
        txtName.setText(MainActivity.saveGame.getName());
        txtJob.setText(MainActivity.saveGame.getJob());
        changeProgressBackground(prbAppearance);
        changeProgressBackground(prbHappy);
        changeProgressBackground(prbHealth);
        changeProgressBackground(prbSmart);
    }

    int totalAsset()
    {
        int money = MainActivity.saveGame.getMoney();
        ArrayList<Food> arrAsset = MainActivity.saveGame.getAsset();
        if (arrAsset == null)
            return money;
        for (int i=0; i<arrAsset.size(); i++)
        {
            money += arrAsset.get(i).getPrice();
        }

        return money;
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

    public Bitmap takeScreenshotForView(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(view.getHeight(), View.MeasureSpec.EXACTLY));
        view.layout((int) view.getX(), (int) view.getY(), (int) view.getX() + view.getMeasuredWidth(), (int) view.getY() + view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    private void AnhXa()
    {
        imgAvatar = view.findViewById(R.id.imageAvatar);

        txtAppearance = view.findViewById(R.id.txtAppearance);
        txtHappy = view.findViewById(R.id.txtHappy);
        txtSmart = view.findViewById(R.id.txtSmart);
        txtHealth = view.findViewById(R.id.txtHealth);
        txtMoney = view.findViewById(R.id.textviewMoney);
        txtName = view.findViewById(R.id.textviewName);
        txtJob = view.findViewById(R.id.textviewJob);
        txtTotal = view.findViewById(R.id.textViewTotal);

        prbAppearance = view.findViewById(R.id.progressbarAppearance);
        prbHappy = view.findViewById(R.id.progressbarHappy);
        prbHealth = view.findViewById(R.id.progressbarHealth);
        prbSmart = view.findViewById(R.id.progressbarSmart);

        btnShare = view.findViewById(R.id.buttonShare);
    }
}
package com.ntsua.thelife;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Random;

public class game2048 extends AppCompatActivity {
    private GridView gdvGamePlay;
    private View.OnTouchListener listener;
    private float X,Y;
    private game2048_oSoAdapter adapter;

    private ArrayList<Integer> arrSO = new ArrayList<>();
    private int[] mangMau;
    private int[][] mangHaiChieu = new int[4][4];
    private Random r = new Random();

    public void intt(){
        //mContext = context;
        for (int i=0; i<4; i++){
            for(int j=0;j<4;j++){
                mangHaiChieu[i][j]=0;
                arrSO.add(0);
            }
        }
        TypedArray ta= this.getResources().obtainTypedArray(R.array.mauNenCuaSo);
        mangMau = new int[ta.length()];
        for (int i=0; i<ta.length(); i++){
            mangMau[i] = ta.getColor(i,0);

        }
        ta.recycle();
        taoSo();
        chuyenDoi();
    }
    public ArrayList<Integer> getArrSO() {
        return arrSO;
    }
    public int colorr(int so){
        if (so==0){
            return Color.WHITE;
        }else{
            int a= (int) (Math.log(so)/Math.log(2));
            return mangMau [a-1];
        }
    }
    public void taoSo(){
        int so0=0;
        for (int i=0; i<16; i++) {
            if (arrSO.get(i)==0) {
                so0++;
            }
        }
        int soOTao;
        if (so0>1){
            soOTao =r.nextInt(2)+1;
        }else {
            if(so0==1){
                soOTao=1;
            }else{
                soOTao=0;
                //Toast.makeText(mContext, "Thua", Toast.LENGTH_SHORT).show();
            }
        }
        int zero = 0;
        for (int i=0; i<4; i++)
        {
            for (int j=0; j<4; j++)
            {
                if (mangHaiChieu[i][j] == 0){
                    zero = 1;
                    break;
                }
            }
            if (zero == 1)
                break;
        }
        if(zero == 0 && isLose())
            dialogGameOver();

        while (soOTao!=0){
            int i =r.nextInt(4), j =r.nextInt(4);
            if (mangHaiChieu[i][j]==0){
                mangHaiChieu[i][j]=2;
                soOTao--;
            }
        }
    }
    public void chuyenDoi(){
        arrSO.clear();
        for (int i=0; i<4; i++){
            for(int j=0;j<4;j++){
                arrSO.add(mangHaiChieu[i][j]);
            }
        }
    }
    public void vuotTrai(){
            for (int i=0;i<4;i++){
                for (int j=0; j<4; j++){
                    int so=mangHaiChieu[i][j];
                    if (so==0){
                        continue;
                    }else {
                        for (int k=j+1;k<4;k++){
                            int sox=mangHaiChieu[i][k];
                            if (sox==0){
                                continue;
                            }else{
                                if (sox==so){
                                    mangHaiChieu[i][j]=so*2;
                                    mangHaiChieu[i][k]=0;
                                    break;
                                }else {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            for (int i=0;i<4;i++){
                for (int j=0;j<4;j++){
                    int so=mangHaiChieu[i][j];
                    if(so==0){
                        for (int k=j+1;k<4;k++){
                            int so1=mangHaiChieu[i][k];
                            if (so1==0){
                                continue;
                            }else {
                                mangHaiChieu[i][j]=mangHaiChieu[i][k];
                                mangHaiChieu[i][k]=0;
                                break;
                            }
                        }
                    }
                }
            }
            taoSo();
            chuyenDoi();
        }
    public void vuotPhai(){
        for (int i=3;i>=0;i--){
            for (int j=3; j>=0; j--){
                int so=mangHaiChieu[i][j];
                if (so==0){
                    continue;
                }else {
                    for (int k=j-1;k>=0;k--){
                        int sox=mangHaiChieu[i][k];
                        if (sox==0){
                            continue;
                        }else{
                            if (sox==so){
                                mangHaiChieu[i][j]=so*2;
                                mangHaiChieu[i][k]=0;
                                break;
                            }else {
                                break;
                            }
                        }
                    }
                }
            }
        }
        for (int i=3;i>=0;i--){
            for (int j=3; j>=0; j--){
                int so=mangHaiChieu[i][j];
                if(so==0){
                    for (int k=j-1;k>=0;k--){
                        int so1=mangHaiChieu[i][k];
                        if (so1==0){
                            continue;
                        }else {
                            mangHaiChieu[i][j]=mangHaiChieu[i][k];
                            mangHaiChieu[i][k]=0;
                            break;
                        }
                    }
                }
            }
        }
        taoSo();
        chuyenDoi();
    }
    public void vuotXuong(){
        for (int i=0;i<4;i++){
            for (int j=0; j<4; j++){
                int so=mangHaiChieu[j][i];
                if (so==0){
                    continue;
                }else {
                    for (int k=j+1;k<4;k++){
                        int sox=mangHaiChieu[k][i];
                        if (sox==0){
                            continue;
                        }else{
                            if (sox==so){
                                mangHaiChieu[j][i]=so*2;
                                mangHaiChieu[k][i]=0;
                                break;
                            }else {
                                break;
                            }
                        }
                    }
                }
            }
        }
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                int so=mangHaiChieu[j][i];
                if(so==0){
                    for (int k=j+1;k<4;k++){
                        int so1=mangHaiChieu[k][i];
                        if (so1==0){
                            continue;
                        }else {
                            mangHaiChieu[j][i]=mangHaiChieu[k][i];
                            mangHaiChieu[k][i]=0;
                            break;
                        }
                    }
                }
            }
        }
        taoSo();
        chuyenDoi();
    }
    public void vuotLen(){
        for (int i=3;i>=0;i--){
            for (int j=3; j>=0; j--){
                int so=mangHaiChieu[j][i];
                if (so==0){
                    continue;
                }else {
                    for (int k=j-1;k>=0;k--){
                        int sox=mangHaiChieu[k][i];
                        if (sox==0){
                            continue;
                        }else{
                            if (sox==so){
                                mangHaiChieu[j][i]=so*2;
                                mangHaiChieu[k][i]=0;
                                break;
                            }else {
                                break;
                            }
                        }
                    }
                }
            }
        }
        for (int i=3;i>=0;i--){
            for (int j=3; j>=0; j--){
                int so=mangHaiChieu[j][i];
                if(so==0){
                    for (int k=j-1;k>=0;k--){
                        int so1=mangHaiChieu[k][i];
                        if (so1==0){
                            continue;
                        }else {
                            mangHaiChieu[j][i]=mangHaiChieu[k][i];
                            mangHaiChieu[k][i]=0;
                            break;
                        }
                    }
                }
            }
        }
        taoSo();
        chuyenDoi();
    }

    private void dialogGameOver()
    {
        int score = getScore();
        Dialog dialog = MainActivity.createNotification(R.drawable.cancel,
                "Bạn đã thua       \nTổng điểm: " + score,
                this);

        MainActivity.saveGame.saveMoney(MainActivity.saveGame.getMoney() + score);
        MainActivity.saveGame.savePlayerInfo(
                MainActivity.saveGame.getHappy() + score / 100,
                MainActivity.saveGame.getHealth(),
                MainActivity.saveGame.getSmart() + score / 100,
                MainActivity.saveGame.getAppearance()
        );

        Button btn = dialog.findViewById(R.id.buttonNotificationtOke);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(game2048.this, game2048_batdau.class));
                //overridePendingTransition(R.anim.enter, R.anim.exit);
                onBackPressed();
            }
        });
    }

    int getScore()
    {
        int score = 0;
        for (int i=0; i<4; i++) {
            for (int j = 0; j < 4; j++) {
                score += mangHaiChieu[i][j];
            }
        }

        return score;
    }

    boolean isLose()
    {
        for (int i=0; i<4; i++)
        {
            for (int j=0; j<4; j++)
            {
                if (i-1>=0 && mangHaiChieu[i-1][j] == mangHaiChieu[i][j])
                    return false;
                if (i+1<=3 && mangHaiChieu[i+1][j] == mangHaiChieu[i][j])
                    return false;
                if (j-1>=0 && mangHaiChieu[i][j-1] == mangHaiChieu[i][j])
                    return false;
                if (j+1<=3 && mangHaiChieu[i][j+1] == mangHaiChieu[i][j])
                    return false;
            }
        }
        return  true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2048);

        anhXa();
        khoiTao();
        setData();
        //dialogGameOver();
    }
    private void anhXa(){
        gdvGamePlay = (GridView)findViewById(R.id.gdvGamePlay);
//        txvThuaGame2048 = findViewById(R.id.txvThuaGame2048);
    }
    private void khoiTao(){
//        txvThuaGame2048.setVisibility(View.GONE);
        intt();
        adapter = new game2048_oSoAdapter(game2048.this,0,getArrSO());

        listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        X=event.getX();
                        Y=event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if(Math.abs(event.getX()-X)>Math.abs(event.getY()-Y)){
                            if (event.getX()<X){
                                vuotTrai();
                                adapter.notifyDataSetChanged();
                            }else{
                                vuotPhai();
                                adapter.notifyDataSetChanged();
                            }

                        }else {
                            if (event.getY()<Y){
                                vuotXuong();
                                adapter.notifyDataSetChanged();
                            }else{
                                vuotLen();
                                adapter.notifyDataSetChanged();
                            }
                        }
                        break;
                }

                return true;
            }
        };
    }

    private void setData(){
        gdvGamePlay.setAdapter(adapter);
        gdvGamePlay.setOnTouchListener(listener);
    }

    public void btn_back2048(View view) {
        //Intent i = new Intent(this,game2048_batdau.class);
        //startActivity(i);
        onBackPressed();
    }
}
package com.ntsua.thelife;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class game2048 extends AppCompatActivity {
    private GridView gdvGamePlay;
    private View.OnTouchListener listener;
    private float X,Y;
    private game2048_oSoAdapter adapter;


    private static game2048 game2048s;
    private ArrayList<Integer> arrSO = new ArrayList<>();
    private int[] mangMau;
    private int[][] mangHaiChieu = new int[4][4];
    private Random r = new Random();
//    TextView txvThuaGame2048;
    static {
        game2048s = new game2048();
    }
    public static game2048 getDatagame(){
        return game2048s;
    }
    public void intt(Context context){
        for (int i=0; i<4; i++){
            for(int j=0;j<4;j++){
                mangHaiChieu[i][j]=0;
                arrSO.add(0);
            }
        }
        TypedArray ta= context.getResources().obtainTypedArray(R.array.mauNenCuaSo);
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
            }
        }
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








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2048);

        anhXa();
        khoiTao();
        setData();
    }
    private void anhXa(){
        gdvGamePlay = (GridView)findViewById(R.id.gdvGamePlay);
//        txvThuaGame2048 = findViewById(R.id.txvThuaGame2048);
    }
    private void khoiTao(){
//        txvThuaGame2048.setVisibility(View.GONE);
        game2048.getDatagame().intt(game2048.this);
        adapter = new game2048_oSoAdapter(game2048.this,0,game2048.getDatagame().getArrSO());

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
                                game2048.getDatagame().vuotTrai();
                                adapter.notifyDataSetChanged();
                            }else{
                                game2048.getDatagame().vuotPhai();
                                adapter.notifyDataSetChanged();
                            }

                        }else {
                            if (event.getY()<Y){
                                game2048.getDatagame().vuotXuong();
                                adapter.notifyDataSetChanged();
                            }else{
                                game2048.getDatagame().vuotLen();
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
        Intent i = new Intent(this,game2048_batdau.class);
        startActivity(i);
    }
}
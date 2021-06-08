package com.ntsua.thelife;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import java.util.Random;

public class AiLaTrieuPhu_Dialog_KhanGia extends Dialog {
    public AiLaTrieuPhu_Dialog_KhanGia(Context context,int vtD) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_trogiupkhangia);
        TextView txvChonA,txvChonB,txvChonC,txvChonD;
        txvChonA = findViewById(R.id.txvChonA);
        txvChonB = findViewById(R.id.txvChonB);
        txvChonC = findViewById(R.id.txvChonC);
        txvChonD = findViewById(R.id.txvChonD);

        // 3
        int arrdrom[] = new int[]{0,0,0,0};
        int can = 25;
        for(int i=0;i<arrdrom.length;i++){
            arrdrom[i]=arrdrom[i]+can;// 25 50 100 125
            if(i==vtD-1){
                arrdrom[i]=arrdrom[i]+25; // 25 50 100
                can = can+25;//100
            }
            can = can+25;//can 125
        }

        int tong = 125;
        int arrPhanTranTl[] = new int[]{0,0,0,0}; // a  b  c d
        int soKhanGia = 200;
        Random r = new Random();
        for(int i=0;i<soKhanGia;i++){
            int chon = r.nextInt(tong);// 0 25 75 100 125
            if(chon>=0 && chon<arrdrom[0]){
                arrPhanTranTl[0]++;
            }else if(chon>= arrdrom[0] && chon<arrdrom[1]){
                arrPhanTranTl[1]++;
            }else if(chon>= arrdrom[1] && chon<arrdrom[2]){
                arrPhanTranTl[2]++;
            }else {
                arrPhanTranTl[3]++;
            }
        }
        txvChonA.setText("A : "+(int)(arrPhanTranTl[0]*100.0f/soKhanGia)+" %");
        txvChonB.setText("B : "+(int)(arrPhanTranTl[1]*100.0f/soKhanGia)+" %");
        txvChonC.setText("C : "+(int)(arrPhanTranTl[2]*100.0f/soKhanGia)+" %");
        txvChonD.setText("D : "+(int)(arrPhanTranTl[3]*100.0f/soKhanGia)+" %");

    }
}

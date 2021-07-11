package com.ntsua.thelife;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class AiLaTrieuPhu_ing extends AppCompatActivity {
    ListView lsvTienThuong;
    AiLaTrieuPhu_TienThuongAdapter tienThuongAdapter;
    ArrayList<String> arrTienThuong;
    AiLaTrieuPhu_CauHoi cauHoi;

    int viTriCauHoi = 1;
    View.OnClickListener listener;
    TextView txvCauHoi, txvCauTL1, txvCauTL2, txvCauTL3, txvCauTL4, txvThuaGame;
    ArrayList<TextView> arrTxvCauTraLoi;
    String cauTraLoi;
    AiLaTrieuPhu_FaceData faceData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ailatrieuphu_ing);
        init();
        anhXa();
        setUp();
        setClick();
    }
    public void init() {
        arrTienThuong = new ArrayList<>();


        arrTienThuong.add("10000000");
        arrTienThuong.add("5000000");
        arrTienThuong.add("2500000");
        arrTienThuong.add("125000");
        arrTienThuong.add("64000");
        arrTienThuong.add("32000");
        arrTienThuong.add("16000");
        arrTienThuong.add("8000");
        arrTienThuong.add("4000");
        arrTienThuong.add("2000");
        arrTienThuong.add("1000");
        arrTienThuong.add("500");
        arrTienThuong.add("300");
        arrTienThuong.add("200");
        arrTienThuong.add("100");

        tienThuongAdapter = new AiLaTrieuPhu_TienThuongAdapter(this, 0, arrTienThuong);

        cauHoi = new AiLaTrieuPhu_CauHoi();

        arrTxvCauTraLoi = new ArrayList<>();

        faceData = new AiLaTrieuPhu_FaceData(this);
    }
    public void anhXa() {
        lsvTienThuong = findViewById(R.id.lsvTienThuong);
        txvCauHoi = findViewById(R.id.txvCauHoi);
        txvCauTL1 = findViewById(R.id.txvCauTL1);
        txvCauTL2 = findViewById(R.id.txvCauTL2);
        txvCauTL3 = findViewById(R.id.txvCauTL3);
        txvCauTL4 = findViewById(R.id.txvCauTL4);
        txvThuaGame = findViewById(R.id.txvThuaGame);

        arrTxvCauTraLoi.add(txvCauTL1);
        arrTxvCauTraLoi.add(txvCauTL2);
        arrTxvCauTraLoi.add(txvCauTL3);
        arrTxvCauTraLoi.add(txvCauTL4);
    }
    public void setUp() {
        txvThuaGame.setVisibility(View.GONE);
        lsvTienThuong.setAdapter(tienThuongAdapter);
        hienCauHoi();
    }
    public void setClick() {
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCauTraLoi(((TextView) view));
            }
        };
        for (TextView t : arrTxvCauTraLoi) {
            t.setOnClickListener(listener);
        }
    }
    public void checkCauTraLoi(final TextView txv) {
        cauTraLoi = txv.getText().toString();
        txv.setBackgroundResource(R.drawable.altp_btn_selected);

        new CountDownTimer(2000, 100) {

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                for (TextView t : arrTxvCauTraLoi) {
                    String s = t.getText().toString();
                    if (s.equals(cauHoi.getDapAnDung())) {
                        t.setBackgroundResource(R.drawable.altp_btn_right);
                        break;
                    }
                }
                new CountDownTimer(2000, 100) {
                    @Override
                    public void onTick(long l) {
                    }

                    @Override
                    public void onFinish() {
                        if (cauTraLoi.equals(cauHoi.getDapAnDung())) {
                            viTriCauHoi++;
                            if (viTriCauHoi > 15) {
                                viTriCauHoi = 15;
                                txvThuaGame.setVisibility(View.VISIBLE);
                                txvThuaGame.setText("Chuc mung ban da duoc \n" + arrTienThuong.get(0) + "000 vnd");
                                MainActivity.saveGame.saveMoney(MainActivity.saveGame.getMoney()+150000000);
                                return;
                            }
                            hienCauHoi();
                        } else {
                            txvThuaGame.setVisibility(View.VISIBLE);
                            // 8 5
                            int vitriTienThuong = (viTriCauHoi / 5) * 5;
                            txvThuaGame.setText("Ban sẽ ra về với tiền thương là \n" + arrTienThuong.get(14 - vitriTienThuong) + "000 vnd");
                            int i = Integer.parseInt(arrTienThuong.get(14-vitriTienThuong));
                            i = i*1000;
                            MainActivity.saveGame.saveMoney(MainActivity.saveGame.getMoney()+i);
                        }
                    }
                }.start();
            }
        }.start();

    }

    public void setCauHoi() {
        cauHoi = faceData.taoCauHoi(viTriCauHoi);
    }

    public void hienCauHoi() {
        setCauHoi();
        txvCauHoi.setText(cauHoi.getNoiDung());
        ArrayList<String> arrCauTraLoi = new ArrayList<>(cauHoi.getArrDapAnSai());
        arrCauTraLoi.add(cauHoi.getDapAnDung());

        Random r = new Random();
        for (int i = 0; i < 5; i++) {
            int vt1 = r.nextInt(arrCauTraLoi.size());
            int vt2 = r.nextInt(arrCauTraLoi.size());
            String a = arrCauTraLoi.get(vt1);// 0 1 2 3
            arrCauTraLoi.set(vt1, arrCauTraLoi.get(vt2));
            arrCauTraLoi.set(vt2, a);
        }

        for (int i = 0; i < arrTxvCauTraLoi.size(); i++) {
            arrTxvCauTraLoi.get(i).setOnClickListener(listener);
            arrTxvCauTraLoi.get(i).setVisibility(View.VISIBLE);
            arrTxvCauTraLoi.get(i).setBackgroundResource(R.drawable.altp_btn);
            arrTxvCauTraLoi.get(i).setText(arrCauTraLoi.get(i));
        }

        tienThuongAdapter.setViTriCauHoi(viTriCauHoi);
    }


    boolean troGiupDoiCauHoi = true;
    public void btn_doicauhoi(View view) {
        if(troGiupDoiCauHoi == false){
            return;
        }
        hienCauHoi();
        troGiupDoiCauHoi =false;
    }

    boolean troGiup5050 = true;
    public void btn_5050(View view) {
        if(troGiup5050 == false){
            return;
        }
        Random r= new Random();
        int sodanAnAnDi =2;
        do{
            int vitriDanAnAn = r.nextInt(4);// 1
            TextView t = arrTxvCauTraLoi.get(vitriDanAnAn);

            if(t.getVisibility() == View.VISIBLE && t.getText().toString().equals(cauHoi.getDapAnDung())==false){
                t.setVisibility(View.INVISIBLE);
                t.setOnClickListener(null);
                sodanAnAnDi --;
            }
        }while (sodanAnAnDi>0);
        troGiup5050 = false;
    }


    boolean troGiupKhanGia = true;
    public void btn_hoikhangia(View view) {
        if (troGiupKhanGia == false){
            return;
        }
        for (int i=0;i<arrTxvCauTraLoi.size();i++){
            TextView t = arrTxvCauTraLoi.get(i);
            if(t.getText().toString().equals(cauHoi.getDapAnDung())){
                new AiLaTrieuPhu_Dialog_KhanGia(this,i+1).show();
                break;
            }
        }
        troGiupKhanGia =false;
    }


    public void btn_close(View view) {
        Intent i = new Intent(this,AiLaTrieuPhu.class);
        startActivity(i);
    }
}

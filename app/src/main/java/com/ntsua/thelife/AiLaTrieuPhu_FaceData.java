package com.ntsua.thelife;

import android.content.Context;

import java.util.ArrayList;
import java.util.Random;

public class AiLaTrieuPhu_FaceData {
    public AiLaTrieuPhu_FaceData(Context ct){
        layCauHoiSQL(ct);
    }

    public void layCauHoiSQL(Context ct){
        ALTP_QLDBCauHoi q = new ALTP_QLDBCauHoi(ct);
        q.open();
        arrCauHoi = new ArrayList<>(q.getcauhoi());
        q.close();
    }

    public AiLaTrieuPhu_CauHoi taoCauHoi(int capDo){
        Random r = new Random();
        ArrayList<AiLaTrieuPhu_CauHoi> arr = arrCauHoi.get(capDo - 1);
        return arr.get(r.nextInt(arr.size()));
    }

    ArrayList<ArrayList<AiLaTrieuPhu_CauHoi>> arrCauHoi = new ArrayList<>();

    private AiLaTrieuPhu_CauHoi taoCauHoi(String s1, String s2, String s3){
        AiLaTrieuPhu_CauHoi c1 = new AiLaTrieuPhu_CauHoi();
        c1.setNoiDung(s1);
        c1.setDapAnDung(s2);
        c1.setArrDapAnSai(s3);
        return c1;
    }
}

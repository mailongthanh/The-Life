package com.ntsua.thelife;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class ALTP_QLDBCauHoi {
    private Context context;
    private SQLiteDatabase database;
    private AiLaTrieuPhu_DSCauHoi dbHelper;

    public ALTP_QLDBCauHoi (Context c){
        this.context = c;
    }

    public ALTP_QLDBCauHoi open() throws SQLException {
        this.dbHelper = new AiLaTrieuPhu_DSCauHoi(this.context);
        this.database = this.dbHelper.getWritableDatabase();
        return  this;
    }

    public void close(){
        this.dbHelper.close();
    }

    public ArrayList<ArrayList<AiLaTrieuPhu_CauHoi>> getcauhoi(){
        ArrayList<ArrayList<AiLaTrieuPhu_CauHoi>> arr = new ArrayList<>();
        HashMap<Integer,ArrayList<AiLaTrieuPhu_CauHoi>> arrs = new HashMap<>();
        Cursor cursor = this.database.query("Cauhoi",
                new String[]{
                        "capdo",
                        "noidung",
                        "dapan",
                        "dapansai" }, null,null,null,null,null);

        while (cursor.moveToNext()){
            int capdo = cursor.getInt(cursor.getColumnIndex("capdo"));
            ArrayList<AiLaTrieuPhu_CauHoi> arrCapDo = arrs.get(capdo);
            if (arrCapDo == null){
                arrCapDo = new ArrayList<>();
            }
            arrCapDo.add(taoCauHoi(
                    cursor.getString(cursor.getColumnIndex("noidung")),
                    cursor.getString(cursor.getColumnIndex("dapan")),
                    cursor.getString(cursor.getColumnIndex("dapansai"))
            ));

            arrs.put(capdo,arrCapDo);
        }
        for (Integer key: arrs.keySet()){
            arr.add(arrs.get(key));
        }
        return arr;
    }


    public AiLaTrieuPhu_CauHoi taoCauHoi(String s1, String s2, String s3){
        AiLaTrieuPhu_CauHoi c1 = new AiLaTrieuPhu_CauHoi();
        c1.setNoiDung(s1);
        c1.setDapAnDung(s2);
        c1.setArrDapAnSai(s3);
        return c1;
    }

}

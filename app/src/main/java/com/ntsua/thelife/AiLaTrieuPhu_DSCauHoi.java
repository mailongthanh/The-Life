package com.ntsua.thelife;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AiLaTrieuPhu_DSCauHoi extends SQLiteOpenHelper {
    public static final String DATA_NAME="cauhoialtp.sqlite";
    public static final int DATA_VERSION=1;

    String createTableCauHoi = "CREATE TABLE "+"Cauhoi"
            +"("
            +"id "+"INTEGER "+"PRIMARY KEY" +" autoincrement, "
            +"capdo "+" INTEGER,"
            +"noidung "+" TEXT,"
            +"dapan "+" TEXT,"
            +"dapansai "+" TEXT"
            +")";

    public AiLaTrieuPhu_DSCauHoi(Context context) {
        super(context, DATA_NAME, null, DATA_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableCauHoi);
        fakedata(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    private void fakedata(SQLiteDatabase d){
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,1,\""+"noidung1"+"\",\""+"dapan1"+"\",\""+"dapansai1&dapansai2&dapansai3"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,2,\""+"noidung2"+"\",\""+"dapan1"+"\",\""+"dapansai1&dapansai2&dapansai3"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,3,\""+"noidung3"+"\",\""+"dapan1"+"\",\""+"dapansai1&dapansai2&dapansai3"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,4,\""+"noidung4"+"\",\""+"dapan1"+"\",\""+"dapansai1&dapansai2&dapansai3"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,5,\""+"noidung5"+"\",\""+"dapan1"+"\",\""+"dapansai1&dapansai2&dapansai3"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,6,\""+"noidung6"+"\",\""+"dapan1"+"\",\""+"dapansai1&dapansai2&dapansai3"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,7,\""+"noidung7"+"\",\""+"dapan1"+"\",\""+"dapansai1&dapansai2&dapansai3"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,8,\""+"noidung8"+"\",\""+"dapan1"+"\",\""+"dapansai1&dapansai2&dapansai3"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,9,\""+"noidung9"+"\",\""+"dapan1"+"\",\""+"dapansai1&dapansai2&dapansai3"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,10,\""+"noidung10"+"\",\""+"dapan1"+"\",\""+"dapansai1&dapansai2&dapansai3"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,11,\""+"noidung11"+"\",\""+"dapan1"+"\",\""+"dapansai1&dapansai2&dapansai3"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,12,\""+"noidung12"+"\",\""+"dapan1"+"\",\""+"dapansai1&dapansai2&dapansai3"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,13,\""+"noidung13"+"\",\""+"dapan1"+"\",\""+"dapansai1&dapansai2&dapansai3"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,14,\""+"noidung14"+"\",\""+"dapan1"+"\",\""+"dapansai1&dapansai2&dapansai3"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,15,\""+"noidung15"+"\",\""+"dapan1"+"\",\""+"dapansai1&dapansai2&dapansai3"+"\");");
    }
}

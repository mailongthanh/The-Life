package com.ntsua.thelife;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public final class SaveGame extends AppCompatActivity {
    private SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
    SharedPreferences.Editor editor = preferences.edit();

    public void saveMoney(int money)
    {
        editor.putInt("money", money);
    }

    public void savePlayerInfo(int happy, int health, int smart, int appearance)
    {
        editor.putInt("happy", happy);
        editor.putInt("health", health);
        editor.putInt("smart", smart);
        editor.putInt("appearance", appearance);
    }

    public void saveDetailActivity(String s){
        editor.putString("detail", s);
    }

    public void saveRelationship(ArrayList arrRelationship) throws IOException {
        editor.putString("relationship", serialize(arrRelationship));
    }

    public void saveJob(String job)
    {
        editor.putString("job", job);
    }


    public int getHappy()
    {
        return  preferences.getInt("happy", 0);
    }

    public int getHealth()
    {
        return  preferences.getInt("health", 0);
    }

    public int getSmart()
    {
        return  preferences.getInt("smart", 0);
    }

    public int getAppearance()
    {
        return  preferences.getInt("appearance", 0);
    }

    public void getJob()
    {
        preferences.getString("job", "unemployment");
    }

    public int getMoney()
    {
        return preferences.getInt("money", 0);
    }

    public ArrayList getDetailActivity() throws IOException {
        return (ArrayList) deserialize(preferences.getString("detail", ""));
    }

    private String serialize(Serializable obj) throws IOException {
        if (obj == null) return "";
        try {
            ByteArrayOutputStream serialObj = new ByteArrayOutputStream();
            ObjectOutputStream objStream = new ObjectOutputStream(serialObj);
            objStream.writeObject(obj);
            objStream.close();
            return encodeBytes(serialObj.toByteArray());
        } catch (Exception e) {
            return "";
        }
    }

    private String encodeBytes(byte[] bytes) {
        StringBuffer strBuf = new StringBuffer();

        for (int i = 0; i < bytes.length; i++) {
            strBuf.append((char) (((bytes[i] >> 4) & 0xF) + ((int) 'a')));
            strBuf.append((char) (((bytes[i]) & 0xF) + ((int) 'a')));
        }

        return strBuf.toString();
    }

    private Object deserialize(String str) throws IOException {
        if (str == null || str.length() == 0) return null;
        try {
            ByteArrayInputStream serialObj = new ByteArrayInputStream(decodeBytes(str));
            ObjectInputStream objStream = new ObjectInputStream(serialObj);
            return objStream.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    private byte[] decodeBytes(String str) {
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length(); i+=2) {
            char c = str.charAt(i);
            bytes[i/2] = (byte) ((c - 'a') << 4);
            c = str.charAt(i+1);
            bytes[i/2] += (c - 'a');
        }
        return bytes;
    }
}

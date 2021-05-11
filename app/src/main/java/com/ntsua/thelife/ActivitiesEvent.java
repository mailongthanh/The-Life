package com.ntsua.thelife;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Random;

public class ActivitiesEvent {
    JSONObject jsonevent;
    SaveGame savegame;
    Random random;
    Context context;

    public ActivitiesEvent(String Json, SaveGame savegame, Context context) {
        try {
            jsonevent = new JSONObject(Json);
            this.savegame = savegame;
            this.context = context;
        } catch (JSONException e) {
            e.printStackTrace();
        };
    }

    void CreateDialog(String name,String titleEvent) throws JSONException {
        JSONArray arrEvent= jsonevent.getJSONArray(name);
        random = new Random();

        final  JSONObject [] object = {arrEvent.getJSONObject(random.nextInt(arrEvent.length()))};
        final String[] event = {object[0].getString("event")};

    }

}

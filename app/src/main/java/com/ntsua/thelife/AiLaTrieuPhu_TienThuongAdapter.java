package com.ntsua.thelife;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AiLaTrieuPhu_TienThuongAdapter extends ArrayAdapter<String> {
    Context mct;
    ArrayList<String> arr;
    int viTriCauHoi =1;
    public AiLaTrieuPhu_TienThuongAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.mct = context;
        this.arr = new ArrayList<>(objects);
    }

    public void setViTriCauHoi(int viTriCauHoi) {
        this.viTriCauHoi = viTriCauHoi;
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)mct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_altp_tienthuong,null);
        }
        if(arr.size()>0){
            int pos = 15 - position;
            TextView txvTienThuong = convertView.findViewById(R.id.txvTienThuong);
            if(pos%5==0){
                txvTienThuong.setTextColor(Color.parseColor("#FFFFFF"));
            }else{
                txvTienThuong.setTextColor(Color.parseColor("#FF9800"));
            }
            String khoangTrang;
            if(pos/10>0){
                khoangTrang = "     ";
            }else{
                khoangTrang = "      ";
            }
            String textHienThi = pos+khoangTrang+"$"+arr.get(position);
            txvTienThuong.setText(textHienThi);

            if(pos == viTriCauHoi){
                txvTienThuong.setBackgroundColor(Color.parseColor("#FF9800"));
                txvTienThuong.setTextColor(Color.parseColor("#9C27B0"));
            }else{
                txvTienThuong.setBackgroundColor(Color.parseColor("#00FF9800"));
            }
        }
        return convertView;
    }
}

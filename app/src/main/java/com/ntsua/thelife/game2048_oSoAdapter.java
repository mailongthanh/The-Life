package com.ntsua.thelife;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class game2048_oSoAdapter extends ArrayAdapter<Integer> {
    private Context ct;
    private ArrayList<Integer> arr;

    public game2048_oSoAdapter(@NonNull Context context, int resource, @NonNull List<Integer> objects) {
        super(context, resource, objects);
        this.ct = context;
        this.arr = new ArrayList<>(objects);
    }

    @Override
    public void notifyDataSetChanged() {
        arr=game2048.getDatagame().getArrSO();
        super.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.game2048_item_ovuong,null);
        }
        if (arr.size()>0){
            game2048_oVuong o = (game2048_oVuong)convertView.findViewById(R.id.txtoVuong);
            o.setTextt(arr.get(position));
        }
        return convertView;
    }
}

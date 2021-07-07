package com.ntsua.thelife;

import android.content.Context;
import android.graphics.Color;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CrimeAdapter extends BaseAdapter {

    Context context;
    int layout;
    ArrayList<Food> arrCrime;

    public CrimeAdapter(Context context,int layout, ArrayList<Food> arrCrime)
    {
        this.context = context;
        this.layout = layout;
        this.arrCrime = arrCrime;
    }
    @Override
    public int getCount() {
        return arrCrime.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView txtCrimename, txtDescription;
        ImageView imgCrime;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;
        if(convertView==null)
        {
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);

            viewholder = new ViewHolder();
            viewholder.imgCrime = convertView.findViewById(R.id.imageCrime);
            viewholder.txtCrimename = convertView.findViewById(R.id.textviewCrime);
            viewholder.txtDescription = convertView.findViewById(R.id.textviewDescriptionCr);

            Food crime = arrCrime.get(position);
            viewholder.imgCrime.setImageResource(crime.getImage());
            viewholder.txtCrimename.setText(crime.getFoodName());
            viewholder.txtCrimename.setTextColor(Color.WHITE);
            viewholder.txtDescription.setText(crime.getDescription());
            viewholder.txtDescription.setTextColor(Color.WHITE);


            convertView.setTag(viewholder);
        }
        else {
            viewholder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }
}

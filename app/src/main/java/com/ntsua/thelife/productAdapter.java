package com.ntsua.thelife;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class productAdapter extends BaseAdapter {

    Context mycontext;
    int mylayout;
    ArrayList<product> arrPro;

    public productAdapter(Context mycontext, int mylayout, ArrayList<product> arrPro) {
        this.mycontext = mycontext;
        this.mylayout = mylayout;
        this.arrPro = arrPro;
    }

    @Override
    public int getCount() {
        return arrPro.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView tvTen, tvDescription;
        ImageView imgProduct;
    }

    ;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mylayout, null);

            holder = new ViewHolder();
            holder.imgProduct = (ImageView) convertView.findViewById(R.id.imageFood);
            holder.tvDescription = (TextView) convertView.findViewById(R.id.textviewDescription);
            holder.tvTen = (TextView) convertView.findViewById(R.id.textviewFoodName);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.imgProduct.setImageResource(arrPro.get(position).getImage());
        holder.tvTen.setText(arrPro.get(position).getProname());
        holder.tvDescription.setText(arrPro.get(position).getDescription());

        Animation anim = AnimationUtils.loadAnimation(mycontext, R.anim.listview_scale);
        convertView.startAnimation(anim);

        return convertView;
    }
}

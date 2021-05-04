package com.ntsua.thelife;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FoodAdapter extends BaseAdapter {

    Context context;
    int layout;
    ArrayList<Food> arrFood;

    public FoodAdapter(Context context, int layout, ArrayList<Food> arrFood) {
        this.context = context;
        this.layout = layout;
        this.arrFood = arrFood;
    }

    @Override
    public int getCount() {
        return arrFood.size();
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
        TextView txtFoodName;
        TextView txtDescription;
        ImageView imgFood;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            viewHolder = new ViewHolder();
            viewHolder.imgFood          = (ImageView) convertView.findViewById(R.id.imageFood);
            viewHolder.txtDescription   = (TextView) convertView.findViewById(R.id.textviewDescription);
            viewHolder.txtFoodName      = (TextView) convertView.findViewById(R.id.textviewFoodName);

            Food food = arrFood.get(position);
            viewHolder.imgFood.setImageResource(food.getImage());
            viewHolder.txtFoodName.setText(food.getFoodName());
            viewHolder.txtDescription.setText(food.getDescription());

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Animation anim = AnimationUtils.loadAnimation(context, R.anim.listview_scale);
        convertView.startAnimation(anim);

        return convertView;
    }
}

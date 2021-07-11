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

public class UniversityAdapter extends BaseAdapter {

    Context context;
    int layout;
    ArrayList<University> arrUniversity;

    public UniversityAdapter(Context context, int layout, ArrayList<University> arrUniversity) {
        this.context = context;
        this.layout = layout;
        this.arrUniversity = arrUniversity;
    }

    @Override
    public int getCount() {
        return arrUniversity.size();
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
        ImageView imgUni;
        TextView txtName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            viewHolder = new ViewHolder();
            viewHolder.imgUni = convertView.findViewById(R.id.imageviewUni);
            viewHolder.txtName = convertView.findViewById(R.id.textviewUni);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        University university = arrUniversity.get(position);
        viewHolder.imgUni.setImageResource(university.getImage());
        viewHolder.txtName.setText(university.getName());

        Animation anim = AnimationUtils.loadAnimation(context, R.anim.listview_scale);
        convertView.startAnimation(anim);
        if (university.getRequire() > MainActivity.saveGame.getSmart()) {
            convertView.setBackgroundResource(R.drawable.list_item_unable);
        }
        else convertView.setBackgroundResource(R.drawable.custom_dialog);

        return convertView;
    }
}

package com.ntsua.thelife;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ntsua.thelife.QuanHe;
import java.util.List;

public class QuanHeAdapter extends BaseAdapter {

    Context myContext;
    int myLayout;
    List<QuanHe> myQuanHeList;

    public QuanHeAdapter(Context context, int Layout, List<QuanHe> QuanHeList)
    {
        myContext=context;
        myLayout=Layout;
        myQuanHeList=QuanHeList;
    }

    @Override
    public int getCount() {
        return myQuanHeList.size();
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
        TextView tvTen, tvMoiQuanHe;
        ProgressBar PbThanhQH;
    };

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null)
        {

            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(myLayout,null);
            holder = new ViewHolder();
            //ánh xạ
            holder.tvTen= (TextView) convertView.findViewById(R.id.tvHoTen);
            holder.tvMoiQuanHe= (TextView) convertView.findViewById(R.id.tvMoiQuanHe);
            holder.PbThanhQH= (ProgressBar) convertView.findViewById(R.id.pbThanhQuanHe);
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder) convertView.getTag();
        }


        //ImageView ImgIcon = (ImageView) convertView.findViewById(R.id.ImvIcon);
        //ImgIcon.setImageResource(myQuanHeList.get(position).HinhAnh);

        //Gán giá trị
        holder.tvTen.setText(myQuanHeList.get(position).Hoten);
        holder.tvMoiQuanHe.setText("(" + myQuanHeList.get(position).QuanHe + ")");
        holder.PbThanhQH.setProgress(myQuanHeList.get(position).DoThanMat);
        return convertView;
    }
}


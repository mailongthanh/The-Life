package com.ntsua.thelife;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class HoatDongQHAdapter extends BaseAdapter {
    Context myContext;
    int myLayout;
    List<HoatDongQH> myHoatDongList;

    public HoatDongQHAdapter(Context context, int Layout, List<HoatDongQH> HoatdongList )
    {
        myContext = context;
        myLayout = Layout;
        myHoatDongList = HoatdongList;
    }


    @Override
    public int getCount() {
        return myHoatDongList.size();
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
        TextView tvTenHoatDong, tvChiTiet;
        ImageView imvicon;
    };

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        HoatDongQHAdapter.ViewHolder holder;

        if(convertView == null)
        {

            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(myLayout,null);
            holder = new HoatDongQHAdapter.ViewHolder();

            //ánh xạ
            holder.tvChiTiet= (TextView) convertView.findViewById(R.id.tvChiTiet);
            holder.tvTenHoatDong = (TextView) convertView.findViewById(R.id.tvHoatDong);
            holder.imvicon = convertView.findViewById(R.id.ImvIcon);
            convertView.setTag(holder);
        } else{
            holder = (HoatDongQHAdapter.ViewHolder) convertView.getTag();
        }

        holder.tvTenHoatDong.setText(myHoatDongList.get(position).TenHoatDong);
        holder.tvChiTiet.setText(myHoatDongList.get(position).ChiTiet);
        holder.imvicon.setImageResource(myHoatDongList.get(position).Icon);

        return convertView;
    }


}


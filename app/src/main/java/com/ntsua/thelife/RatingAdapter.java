package com.ntsua.thelife;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.MyHolder> {


    ArrayList<Ratings> arrRatings;

    public RatingAdapter(ArrayList<Ratings> arrRatings) {
        this.arrRatings = arrRatings;
    }

    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rating_line, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        Ratings ratings = arrRatings.get(position);
        Picasso.get().load(Uri.parse(ratings.getPhotoUri())).into(holder.imgAvater);
        holder.txtName.setText(ratings.getName());
        holder.txtMoney.setText("Tổng tài sản: " + ratings.getMoney());
        holder.txtRank.setText("" + (position + 1));
    }

    @Override
    public int getItemCount() {
        return arrRatings.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        public TextView txtName, txtMoney, txtRank;
        public CircleImageView imgAvater;
        public MyHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            txtMoney = itemView.findViewById(R.id.textviewRateMoney);
            txtName = itemView.findViewById(R.id.textViewRateName);
            txtRank = itemView.findViewById(R.id.textViewRank);
            imgAvater = itemView.findViewById(R.id.circleViewAvatar);
        }
    }
}

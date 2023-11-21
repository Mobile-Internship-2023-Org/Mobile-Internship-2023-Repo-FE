package com.example.foody_app.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody_app.R;
import com.example.foody_app.models.FoodModel;

import java.util.List;

public class FoodAdapter2 extends RecyclerView.Adapter<FoodAdapter2.ViewHolder> {

    private List<FoodModel> mList;

    public FoodAdapter2(List<FoodModel> list) {
        mList = list;
    }

    @NonNull
    @Override
    public FoodAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item_layout, parent, false);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter2.ViewHolder holder, int position) {
        holder.mImageView.setImageResource(R.drawable.img_food);
        holder.tvGia.setText(mList.get(position).getGiaBan()+"");
        holder.tvTen.setText(mList.get(position).getTen());
    }

    @Override
    public int getItemCount() {
        if(mList != null){
            return mList.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTen, tvGia;
        ImageView mImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imgAnhFood);
            tvTen = itemView.findViewById(R.id.tvTenMonAn);
            tvGia = itemView.findViewById(R.id.tvGiaBan);
        }
    }
}

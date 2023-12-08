package com.example.foody_app.adapter;


import static com.example.foody_app.activities.ChiTietMonAnActivity.currencyFormat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody_app.R;
import com.example.foody_app.activities.ChiTietMonAnActivity;
import com.example.foody_app.models.FoodModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodAdapter2 extends RecyclerView.Adapter<FoodAdapter2.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(FoodModel item);
    }

    private List<FoodModel> mList;
    private final OnItemClickListener mListener;

    public FoodAdapter2(List<FoodModel> list, final OnItemClickListener listener) {
        mList = list;
        mListener = listener;
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
        holder.bind(mList.get(position), mListener);
    }


    @Override
    public int getItemCount() {
        if(mList != null){
            return mList.size();
        }
        return 0;
    }
    public void updateList(List<FoodModel> newList) {
        mList.clear();
        mList.addAll(newList);
        notifyDataSetChanged();
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
        public void bind(final FoodModel item, final OnItemClickListener listener) {
            tvTen.setText(item.getTen());
            tvGia.setText(currencyFormat(item.getGiaBan()+"")+"đ");
            Picasso.get().load(item.getAnh()).into(mImageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });

        }

    }
}

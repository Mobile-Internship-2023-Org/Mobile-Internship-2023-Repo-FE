package com.example.foody_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foody_app.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MonanAdapter extends RecyclerView.Adapter<MonanAdapter.ViewHolder> {

    private Context context;
    private List<Map<String, Object>> monanList;
    private boolean showAllItems = false;

    public MonanAdapter(Context context, List<Map<String, Object>> monanList) {
        this.context = context;
        this.monanList = monanList;
        this.showAllItems = false;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_monan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!showAllItems && position >= 2) {
            // If not showing all items and position is 2 or more, hide the view
            holder.itemView.setVisibility(View.GONE);
            return;
        }

        Map<String, Object> monan = monanList.get(position);

        Glide.with(context).load((String) monan.get("anh")).into(holder.imageView);

        holder.textViewTen.setText((String) monan.get("ten"));

        double soLuong = (double) monan.get("soLuong");
        holder.textViewSoLuong.setText((int) Math.round(soLuong) + " x ");

        double giaBan = (double) monan.get("giaBan");
        double totalCost = soLuong * giaBan;

        String formattedTotalCost = NumberFormat.getIntegerInstance(Locale.getDefault()).format((int) Math.round(totalCost));
        holder.textViewGiaBan.setText(formattedTotalCost + "đ");
    }

    @Override
    public int getItemCount() {
        if (!showAllItems && monanList.size() > 2) {
            // If not showing all items and the list has more than 2 items, return 2
            return 2;
        }
        return monanList.size();
    }

    public void toggleShowAllItems() {
        showAllItems = !showAllItems;
        notifyDataSetChanged();
    }

    public boolean isShowAllItems() {
        return showAllItems;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewTen, textViewSoLuong, textViewGiaBan;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewMonan);
            textViewTen = itemView.findViewById(R.id.textViewTen);
            textViewSoLuong = itemView.findViewById(R.id.textViewSoLuong);
            textViewGiaBan = itemView.findViewById(R.id.textViewGiaBan);
        }
    }
}

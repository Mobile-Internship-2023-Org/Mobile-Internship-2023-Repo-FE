package com.example.foody_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foody_app.R;
import com.example.foody_app.models.InforModel;

import java.util.List;

public class GioHangAdapter extends BaseAdapter {
    private Context mContext;
    private List<InforModel> mInforModel;

    // Variable to store the selected item position
    private int selectedPosition = -1;

    public GioHangAdapter(Context context, List<InforModel> inforModels) {
        mContext = context;
        mInforModel = inforModels;
    }

    @Override
    public int getCount() {
        return mInforModel != null ? mInforModel.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mInforModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView btnTru, btnCong;
        ImageView mImageView;
        TextView tvTen, tvGia, tvSoLuong;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gio_hang_item_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mImageView = convertView.findViewById(R.id.imgAnhFoodGH);
            viewHolder.tvTen = convertView.findViewById(R.id.tvTenMonAnGH);
            viewHolder.tvGia = convertView.findViewById(R.id.tvGiaMonAnGH);
            viewHolder.tvSoLuong = convertView.findViewById(R.id.tvsoluong);
            viewHolder.btnCong = convertView.findViewById(R.id.btncong);
            viewHolder.btnTru = convertView.findViewById(R.id.btntru);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final InforModel currentItem = mInforModel.get(position);

        viewHolder.mImageView.setImageResource(R.drawable.image);
        viewHolder.tvTen.setText(currentItem.getTen());
        viewHolder.tvGia.setText(currentItem.getGiaBan() + "");
        viewHolder.tvSoLuong.setText(currentItem.getSoLuong() + "");

        // Highlight the selected item
        convertView.setBackgroundResource(selectedPosition == position ? R.color.selected_item_color : android.R.color.transparent);

        viewHolder.btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Increase the quantity
                currentItem.setSoLuong(currentItem.getSoLuong() + 1);

                // Notify the adapter that the data has changed
                notifyDataSetChanged();
            }
        });

        viewHolder.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Decrease the quantity only if it's greater than 1
                if (currentItem.getSoLuong() > 1) {
                    currentItem.setSoLuong(currentItem.getSoLuong() - 1);

                    // Notify the adapter that the data has changed
                    notifyDataSetChanged();
                }
            }
        });

        // Set click listener for the whole item to highlight it
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update the selected position and highlight the item
                selectedPosition = position;
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}

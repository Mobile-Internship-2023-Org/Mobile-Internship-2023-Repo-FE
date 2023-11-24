package com.example.foody_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foody_app.R;
import com.example.foody_app.models.FoodModel;
import com.example.foody_app.models.InforModel;

import java.util.List;

public class GioHangAdapter extends BaseAdapter {
    private Context mContext;
    private List<InforModel> mInforModel;
    private FoodAdapter maAdapter;

    public GioHangAdapter(Context context, List<InforModel> foodModels) {
        mContext = context;
        mInforModel = foodModels;
    }

    @Override
    public int getCount() {
        if (mInforModel != null) {
            return mInforModel.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return mInforModel.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mInforModel.get(i).getSoLuong();
    }

    private class ViewHolder {
        ImageView mImageView;
        TextView tvTen, tvGia, tvSoLuong;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.gio_hang_item_layout, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.mImageView = view.findViewById(R.id.imgAnhFoodGH);
            viewHolder.tvTen = view.findViewById(R.id.tvTenMonAnGH);
            viewHolder.tvGia = view.findViewById(R.id.tvGiaMonAnGH);
            viewHolder.tvSoLuong = view.findViewById(R.id.tvSoLuongGH);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.mImageView.setImageResource(R.drawable.image);
        viewHolder.tvTen.setText(mInforModel.get(i).getTen());
        viewHolder.tvGia.setText(mInforModel.get(i).getGia() + "");
        viewHolder.tvSoLuong.setText(2 + "");
        return view;
    }

}


package com.example.foody_app.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foody_app.R;
import com.example.foody_app.fragments.GioHangFragment;
import com.example.foody_app.models.InforModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GioHangAdapter extends BaseAdapter {
    private OnQuantityChangeListener quantityChangeListener;
    private GioHangFragment gioHangFragment, updatetotal;
    private Context mContext;
    private List<InforModel> mInforModel;


    // Variable to store the selected item position
    private int selectedPosition = -1;

    public interface OnQuantityChangeListener {
        void onQuantityChanged();
    }

    public void setQuantityChangeListener(OnQuantityChangeListener listener) {
        this.quantityChangeListener = listener;
    }


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


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final InforModel currentItem = mInforModel.get(position);

        Picasso.get().load(currentItem.getAnh()).into(viewHolder.mImageView);
        viewHolder.tvTen.setText(currentItem.getTen());
        viewHolder.tvGia.setText(currentItem.getGiaBan() + "");
        viewHolder.tvSoLuong.setText(currentItem.getSoLuong() + "");

        // Highlight the selected item
        convertView.setBackgroundResource(selectedPosition == position ? R.color.selected_item_color : android.R.color.transparent);



        // Set click listener for the whole item to highlight it
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update the selected position and highlight the item
                selectedPosition = position;

            }
        });

        return convertView;
    }

    private void showToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }


}

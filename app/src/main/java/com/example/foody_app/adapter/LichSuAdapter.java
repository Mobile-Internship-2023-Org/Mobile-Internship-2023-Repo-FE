package com.example.foody_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foody_app.R;
import com.example.foody_app.models.LichSuModel;

import java.util.List;

public class LichSuAdapter extends BaseAdapter {

    private Context mContext;
    private List<LichSuModel> mList;

    public LichSuAdapter(Context context, List<LichSuModel> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        if(mList != null){
            return mList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mList.get(i).getIdMonAn();
    }

    private class ViewHolder{
        ImageView mImageView;
        TextView tvtrangThai, tvdiaChi, tvTen, tvloai, tvGia, tvSoLuong;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.lich_su_item_layout, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.mImageView = view.findViewById(R.id.imgAnhMonAnLS);
            viewHolder.tvTen = view.findViewById(R.id.tvTenMonAnLS);
            viewHolder.tvdiaChi = view.findViewById(R.id.tvDiaChiLS);
            viewHolder.tvGia = view.findViewById(R.id.tvGiaMonAnLS);
            viewHolder.tvloai = view.findViewById(R.id.tvLoaiMonAnLS);
            viewHolder.tvSoLuong = view.findViewById(R.id.tvSoLuongLS);
            viewHolder.tvtrangThai = view.findViewById(R.id.tvTrangThaiLS);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.mImageView.setImageResource(R.drawable.image2);
        viewHolder.tvTen.setText(mList.get(i).getTenMonAn());
        viewHolder.tvdiaChi.setText(mList.get(i).getDiaChi());
        viewHolder.tvGia.setText(mList.get(i).getGia()+"");
        viewHolder.tvloai.setText(mList.get(i).getLoai());
        viewHolder.tvSoLuong.setText(mList.get(i).getSoLuong()+"");
        if(mList.get(i).getTrangThai() == 0){
            viewHolder.tvtrangThai.setText("Đã giao thành công");
        }else if(mList.get(i).getTrangThai() == 1){
            viewHolder.tvtrangThai.setText("Đơn hàng đang giao");
        }else{
            viewHolder.tvtrangThai.setText("Đơn hàng đang được xử lý");
        }
        return view;
    }
}

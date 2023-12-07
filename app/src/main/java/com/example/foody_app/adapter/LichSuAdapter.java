package com.example.foody_app.adapter;

import static com.example.foody_app.activities.ChiTietMonAnActivity.currencyFormat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foody_app.R;
import com.example.foody_app.activities.ChiTietMonAnActivity;
import com.example.foody_app.models.LichSuModel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class LichSuAdapter extends BaseAdapter {

    private Context mContext;
    private List<LichSuModel> mList;
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

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
        return mList.get(i).getIdHoaDon();
    }

    private class ViewHolder{
        TextView tvtrangThai, tvdiaChi, tvMaHoaDon, tvNgayDat, tvTongTien, tvPhuongThucTT;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.lich_su_item_layout, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.tvMaHoaDon = view.findViewById(R.id.tvMaDonHang);
            viewHolder.tvNgayDat = view.findViewById(R.id.tvNgayDat);
            viewHolder.tvTongTien = view.findViewById(R.id.tvTongTien);
            viewHolder.tvdiaChi = view.findViewById(R.id.tvDiaChiLS);
            viewHolder.tvtrangThai = view.findViewById(R.id.tvTrangThaiLS);
            viewHolder.tvPhuongThucTT = view.findViewById(R.id.tvPhuongThucTT);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvdiaChi.setText("Địa chỉ: "+mList.get(i).getDiaChi());
        viewHolder.tvMaHoaDon.setText("Mã hóa đơn: "+mList.get(i).getIdHoaDon()+"");
        viewHolder.tvNgayDat.setText("Thời gian: "+mSimpleDateFormat.format(mList.get(i).getNgayDat()));
        viewHolder.tvTongTien.setText(ChiTietMonAnActivity.currencyFormat(mList.get(i).getTongTienHoaDon()+"")+"đ");
        viewHolder.tvPhuongThucTT.setText(mList.get(i).getPhuongThucTT());
        viewHolder.tvtrangThai.setText(mList.get(i).getTrangThai());
        return view;
    }
}

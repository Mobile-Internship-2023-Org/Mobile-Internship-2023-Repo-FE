package com.example.foody_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.foody_app.R;
import com.example.foody_app.activities.ChiTietMonAnActivity;
import com.example.foody_app.models.MonAnModel;

import java.util.List;

public class MonAnAdapter2 extends BaseAdapter {

    private List<MonAnModel> mList;
    private Context mContext;

    public MonAnAdapter2(List<MonAnModel> list, Context context) {
        mList = list;
        mContext = context;
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
        return 0;
    }

    class ViewHolder{
        TextView tvTen, tvSoLuong, tvGia;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_chi_tiet_layout,viewGroup,false);
            viewHolder = new ViewHolder();
            viewHolder.tvTen = view.findViewById(R.id.tvTenMonAn2);
            viewHolder.tvGia = view.findViewById(R.id.tvGia2);
            viewHolder.tvSoLuong = view.findViewById(R.id.tvsoluong2);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvTen.setText("- "+mList.get(i).getTen());
        viewHolder.tvGia.setText("Gía bán: "+ ChiTietMonAnActivity.currencyFormat(mList.get(i).getGiaBan()+"")+"đ");
        viewHolder.tvSoLuong.setText("Số lượng: "+mList.get(i).getSoLuong());
        return view;
    }
}

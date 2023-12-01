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
import com.example.foody_app.models.FoodModel;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class FoodAdapter extends BaseAdapter{
    private Context mContext;
    private List<FoodModel> mFoodModels;

    public FoodAdapter(Context context, List<FoodModel> foodModels) {
        mContext = context;
        mFoodModels = foodModels;
    }

    @Override
    public int getCount() {
        if(mFoodModels != null){
            return mFoodModels.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return mFoodModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mFoodModels.get(i).getIdMonAn();
    }

    private class ViewHolder{
        ImageView mImageView;
        TextView tvTen, tvGia;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.food_item_layout,viewGroup,false);
            viewHolder = new ViewHolder();
            viewHolder.mImageView = view.findViewById(R.id.imgAnhFood);
            viewHolder.tvTen = view.findViewById(R.id.tvTenMonAn);
            viewHolder.tvGia = view.findViewById(R.id.tvGiaBan);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        Picasso.get().load(mFoodModels.get(i).getAnh()).into(viewHolder.mImageView);
        viewHolder.tvTen.setText(mFoodModels.get(i).getTen());
        viewHolder.tvGia.setText(currencyFormat(mFoodModels.get(i).getGiaBan()+"") +"đ");

        return view;
    }
}

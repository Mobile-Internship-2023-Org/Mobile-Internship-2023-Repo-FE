package com.example.foody_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foody_app.R;
import com.example.foody_app.models.RatingModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RatingAdapter extends BaseAdapter {
    private Context mContext;
    private List<RatingModel> mRatingModels;

    public RatingAdapter(Context context, List<RatingModel> ratingModels) {
        mContext = context;
        mRatingModels = ratingModels;
    }

    @Override
    public int getCount() {
        if(mRatingModels != null){
            return mRatingModels.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return mRatingModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mRatingModels.get(i).getId();
    }

    class ViewHolder {
        ImageView imgAVT;
        TextView tvTen, tvSoSao, tvMoTa;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.rating_item_layout,viewGroup,false);
            viewHolder = new ViewHolder();
            viewHolder.imgAVT = view.findViewById(R.id.imgAVT2);
            viewHolder.tvTen = view.findViewById(R.id.tvTenND2);
            viewHolder.tvSoSao = view.findViewById(R.id.tvSoSao);
            viewHolder.tvMoTa = view.findViewById(R.id.tvMoTa);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Picasso.get().load(mRatingModels.get(i).getAnh()).into(viewHolder.imgAVT);
        if(mRatingModels.get(i).getHoTen() != null){
            viewHolder.tvTen.setText(mRatingModels.get(i).getHoTen());
        }else{
            viewHolder.tvTen.setText(mRatingModels.get(i).getEmail());
        }
        viewHolder.tvMoTa.setText(mRatingModels.get(i).getSoSao()+"/5");
        viewHolder.tvMoTa.setText(mRatingModels.get(i).getMoTa());

        return view;
    }
}

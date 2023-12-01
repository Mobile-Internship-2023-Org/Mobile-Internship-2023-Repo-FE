package com.example.foody_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foody_app.R;
import com.example.foody_app.models.RatingModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ViewHolder> {
    private Context mContext;
    private List<RatingModel> mRatingModels;

    public RatingAdapter(Context context, List<RatingModel> ratingModels) {
        mContext = context;
        mRatingModels = ratingModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rating_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RatingModel ratingModel = mRatingModels.get(position);

        Picasso.get().load(ratingModel.getAnh()).into(holder.imgAVT);
        if (ratingModel.getHoTen() != null) {
            holder.tvTen.setText(ratingModel.getHoTen());
        } else {
            holder.tvTen.setText(ratingModel.getEmail());
        }
        holder.tvSoSao.setText(ratingModel.getSoSao() + "/5");
        holder.tvMoTa.setText(ratingModel.getMoTa());
    }

    @Override
    public int getItemCount() {
        if (mRatingModels != null) {
            return mRatingModels.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAVT;
        TextView tvTen, tvSoSao, tvMoTa;

        public ViewHolder(View itemView) {
            super(itemView);
            imgAVT = itemView.findViewById(R.id.imgAVT2);
            tvTen = itemView.findViewById(R.id.tvTenND2);
            tvSoSao = itemView.findViewById(R.id.tvSoSao);
            tvMoTa = itemView.findViewById(R.id.tvMoTa);
        }
    }
}
package com.example.foody_app.activities;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foody_app.R;
import com.example.foody_app.adapter.FoodAdapter2;
import com.example.foody_app.adapter.RatingAdapter;
import com.example.foody_app.models.FoodModel;
import com.example.foody_app.models.RatingModel;
import com.example.foody_app.models.ShoppingCartModel;
import com.example.foody_app.models.UserModel;
import com.example.foody_app.utils.APIClient;
import com.example.foody_app.utils.APIInterface;
import com.example.foody_app.utils.UserModelHelper;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietMonAnActivity extends AppCompatActivity {

    private List<FoodModel> mFoodModels;
    private FoodAdapter2 mAdapter2;
    private RecyclerView mRecyclerView;
    private ImageView imgEdit;
    private TextView tvTen, tvGiaBan, tvSoLuong, tvGia;
    private ImageView imgMinus, imgPlus, imgFood, imgBack;
    private FoodModel model;
    private androidx.appcompat.widget.Toolbar mToolbar;
    private LinearLayout mLayout;
    private ShoppingCartModel mShoppingCartModel = new ShoppingCartModel();
    private List<RatingModel> mRatingModels = new ArrayList<>();
    private RatingAdapter mRatingAdapter;
    private ListView lvDanhGia;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_mon_an);

        onBindView();

        DangNhapActivity activity = new DangNhapActivity();
        getUserData(activity.readEmailLocally(ChiTietMonAnActivity.this));
        mRatingAdapter = new RatingAdapter(ChiTietMonAnActivity.this,mRatingModels);
        getRating();
        lvDanhGia.setAdapter(mRatingAdapter);
        long id = getIntent().getLongExtra("idFood", -1);
        Integer type = getIntent().getIntExtra("idType",-1);
        tvSoLuong.setText(1+"");
        /**
         * xử lý sự kiện click image (-)
         */
        imgMinus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                tvSoLuong.setText((Integer.parseInt(tvSoLuong.getText().toString()) - 1)+"");
                if(Integer.parseInt(tvSoLuong.getText().toString()) < 1){
                    tvSoLuong.setText(1+"");
                }
                tvGia.setText(currencyFormat(model.getGiaBan()*Integer.parseInt(tvSoLuong.getText().toString())+"")+"đ");
            }
        });
        /**
         * xử lý sự kiện click image (+)
         */
        imgPlus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                tvSoLuong.setText((Integer.parseInt(tvSoLuong.getText().toString()) + 1)+"");
                tvGia.setText(currencyFormat(model.getGiaBan()*Integer.parseInt(tvSoLuong.getText().toString())+"")+"đ");
            }
        });
        mFoodModels = new ArrayList<>();
        getFoodById(Long.toString(id));
        getFoodByType(type, (int) id);
        mAdapter2 = new FoodAdapter2(mFoodModels, new FoodAdapter2.OnItemClickListener() {
            @Override
            public void onItemClick(FoodModel item) {
                getFoodById(item.getIdMonAn().toString());
                //Toast.makeText(getApplicationContext(), ""+item.getIdMonAn(), Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(ChiTietMonAnActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setAdapter(mAdapter2);
        mRecyclerView.setLayoutManager(layoutManager);

        /**
         * xử lý sự kiện back trên toolbar
         */
        mToolbar.setNavigationIcon(R.drawable.back1);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /**
         * xử lý sự kiện click item edit trên layout
         */
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietMonAnActivity.this, SuaMonAnActivity.class);
                startActivity(intent);
            }
        });

        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShoppingCartModel.setSoLuong(Integer.parseInt(tvSoLuong.getText().toString()));
                mShoppingCartModel.setTrangThai(1);
                addToCard(mShoppingCartModel);
            }
        });

    }

    /**
     * ánh xạ view
     */
    private void onBindView(){
        mRecyclerView = findViewById(R.id.rcvCTMA);
        imgEdit = findViewById(R.id.imgEdit);
        tvTen = findViewById(R.id.tvTenMonAnCT);
        tvGiaBan = findViewById(R.id.tvGiaMonAnCT);
        tvSoLuong = findViewById(R.id.tvSoLuongCT);
        imgMinus = findViewById(R.id.imgMinus);
        imgPlus = findViewById(R.id.imgPlus);
        tvGia = findViewById(R.id.tvGia);
        mLayout = findViewById(R.id.layout_cart);
        imgFood = findViewById(R.id.imgFoodCT);
        //imgBack = findViewById(R.id.imgBack);
        mToolbar = findViewById(R.id.toolBarCT);
        lvDanhGia = findViewById(R.id.lvDanhGia);
    }

    /**
     *
     * function laays thoong tin mons awn theo id
     */
    private void getFoodById(String id){
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<FoodModel> call = apiInterface.getFoodById(id);
        call.enqueue(new Callback<FoodModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<FoodModel> call, @NonNull Response<FoodModel> response) {
                if(response.isSuccessful()){
                    model = response.body();
                    assert model != null;
                    tvTen.setText(model.getTen());
                    tvGiaBan.setText(currencyFormat(model.getGiaBan()+"")+"đ/suất");
                    tvGia.setText(currencyFormat(model.getGiaBan()*Integer.parseInt(tvSoLuong.getText().toString())+"")+"đ");
                    mShoppingCartModel.setIdMonAn(model.getIdMonAn());
                    Picasso.get().load(model.getAnh()).into(imgFood);

                }else {
                    Log.e("TAG", "onResponse: "+response.errorBody() );
                }
            }

            @Override
            public void onFailure(@NonNull Call<FoodModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage());
            }
        });
    }

    /**
     *
     * @param type
     * @param id
     * function lấy đồ ăn theo loại
     */
    private void getFoodByType(Integer type, Integer id){
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<List<FoodModel>> call = apiInterface.getFoodByType(type, id);

        call.enqueue(new Callback<List<FoodModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<List<FoodModel>> call, @NonNull Response<List<FoodModel>> response) {
                if(response.isSuccessful()){
                    mFoodModels.clear();
                    assert response.body() != null;
                    mFoodModels.addAll(response.body());
                    mAdapter2.notifyDataSetChanged();
                }else{
                    Log.e("TAG", "onResponse: "+response.errorBody() );
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<FoodModel>> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage() );
            }
        });
    }

    /**
     * function lấy thông tin  người dùng theo email
     * @param email
     */
    private void getUserData(String email){
        UserModelHelper.getInstance().getUserByEmail(email, new Callback<UserModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                if(response.isSuccessful()){
                    UserModel userModel = response.body();
                    assert userModel != null;
                    Log.e("TAG", "onResponse: "+userModel.getHoTen());
                    mShoppingCartModel.setIdNguoiDung(userModel.getIdNguoiDung());
                    if(userModel.getRole().equals("user")){
                        imgEdit.setVisibility(View.GONE);
                    }
                }else{

                }
            }

            @Override
            public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {

            }
        });
    }

    /**
     * thêm món ăn vào giỏ hàng
     * @param shoppingCartModel
     */
    private void addToCard(ShoppingCartModel shoppingCartModel){
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<Void> call = apiInterface.addToCart(shoppingCartModel);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(ChiTietMonAnActivity.this, "Thêm món ăn vào giỏ hàng", Toast.LENGTH_SHORT).show();
                }else{
                    Log.e("TAG", "onResponse: "+response.errorBody() );
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage() );
            }
        });
    }
    /**
     * lấy đánh giá người dùng
     */
    private void getRating(){
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<List<RatingModel>> call = apiInterface.getRating();
        call.enqueue(new Callback<List<RatingModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<RatingModel>> call, @NonNull Response<List<RatingModel>> response) {
                if(response.isSuccessful()){
                    mRatingModels.clear();
                    assert response.body() != null;
                    mRatingModels.addAll(response.body());
                    mRatingAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(ChiTietMonAnActivity.this, "lấy dữ liệu không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<RatingModel>> call, @NonNull Throwable t) {
                Toast.makeText(ChiTietMonAnActivity.this, "lỗi"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0");
        return formatter.format(Double.parseDouble(amount));
    }
}
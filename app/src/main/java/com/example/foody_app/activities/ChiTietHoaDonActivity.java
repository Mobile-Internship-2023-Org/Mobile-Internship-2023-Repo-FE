package com.example.foody_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foody_app.MainActivity;
import com.example.foody_app.R;
import com.example.foody_app.adapter.MonAnAdapter2;
import com.example.foody_app.models.LichSuModel;
import com.example.foody_app.models.MonAnModel;
import com.example.foody_app.models.UserModel;
import com.example.foody_app.utils.APIClient;
import com.example.foody_app.utils.APIInterface;
import com.example.foody_app.utils.UserModelHelper;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietHoaDonActivity extends AppCompatActivity {

    private TextView tvid, tvTen, tvDiaChi, tvNgayDat, tvsdt, tvTongTien;
    private List<MonAnModel> mList = new ArrayList<>();
    private ListView mListView;
    private MonAnAdapter2 mAnAdapter2;
    private LinearLayout layoutAdmin, layoutUser;
    private Button btnHuy1, btnHuy2, btnNhanDon, btnNHanHang;
    private String role, trangThai;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_hoa_don);

        onBinView();

        Long idHoaDon = getIntent().getLongExtra("idDonHang",-1);
        int idGioHang = getIntent().getIntExtra("idGioHang", -1);

        getUserData(getEmail());
        getHoaDonById(idHoaDon.intValue());
        getMonAn(idGioHang);
        mAnAdapter2 = new MonAnAdapter2(mList,this);
        mListView.setAdapter(mAnAdapter2);
        btnNhanDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTrangThai(idHoaDon.intValue(),2);
            }
        });
        btnNHanHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTrangThai(idHoaDon.byteValue(), 3);
            }
        });
        btnHuy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                huyDon(idHoaDon.intValue(), 4);
                Intent intent = new Intent(ChiTietHoaDonActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        btnHuy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(trangThai.equals("Đã hoàn thành")){
                    Toast.makeText(ChiTietHoaDonActivity.this, "Không thể hủy", Toast.LENGTH_SHORT).show();
                }else{
                    huyDon(idHoaDon.intValue(),4);
                    Intent intent = new Intent(ChiTietHoaDonActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void onBinView(){
        tvid = findViewById(R.id.tvIdDonHang);
        tvTen = findViewById(R.id.tvTenKhachHang);
        tvsdt = findViewById(R.id.tvSdt);
        tvDiaChi = findViewById(R.id.tvDiaChiKhachHang);
        tvNgayDat = findViewById(R.id.tvThoiGian);
        tvTongTien = findViewById(R.id.tvTongTien2);
        layoutAdmin = findViewById(R.id.layoutAdmin);
        layoutUser = findViewById(R.id.layoutUser);
        mListView = findViewById(R.id.lvDoAn);
        btnHuy1 = findViewById(R.id.btnHuyDon1);
        btnHuy2 = findViewById(R.id.btnHuyDon2);
        btnNhanDon = findViewById(R.id.btnNhanDon);
        btnNHanHang = findViewById(R.id.btnNhanHang);
    }
    private void getHoaDonById(int id){
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<LichSuModel> call = apiInterface.getHoaDonById(id);
        call.enqueue(new Callback<LichSuModel>() {
            @Override
            public void onResponse(Call<LichSuModel> call, Response<LichSuModel> response) {
                if(response.isSuccessful()){
                    LichSuModel model = response.body();
                    trangThai = model.getTrangThai();
                    tvid.setText("Mã đơn hàng: "+model.getIdHoaDon());
                    tvsdt.setText("Số điện thoại: "+model.getSdt());
                    tvDiaChi.setText("Địa chỉ: "+model.getDiaChi());
                    tvTen.setText("Họ tên: "+model.getHoTen());
                    tvNgayDat.setText("Ngày đặt: "+new SimpleDateFormat("yyyy-MM-dd hh:mm").format(model.getNgayDat()));
                    tvTongTien.setText(ChiTietMonAnActivity.currencyFormat(""+model.getTongTienHoaDon())+"đ");
                    if(trangThai.equals("Đã hoàn thành") || trangThai.equals("Hủy đơn")){
                        btnHuy1.setVisibility(View.GONE);
                        btnHuy2.setVisibility(View.GONE);
                        btnNhanDon.setVisibility(View.GONE);
                        btnNHanHang.setVisibility(View.GONE);
                    }
                    if (trangThai.equals("Đang giao hàng")){
                        btnHuy2.setVisibility(View.GONE);
                        btnNhanDon.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<LichSuModel> call, Throwable t) {

            }
        });
    }

    private void getMonAn(int id){
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<List<MonAnModel>> call = apiInterface.getListMonAn(id);
        call.enqueue(new Callback<List<MonAnModel>>() {
            @Override
            public void onResponse(Call<List<MonAnModel>> call, Response<List<MonAnModel>> response) {
                if(response.isSuccessful()){
                    mList.clear();
                    mList.addAll(response.body());
                    mAnAdapter2.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<MonAnModel>> call, Throwable t) {

            }
        });
    }
    private String getEmail(){
        DangNhapActivity activity = new DangNhapActivity();
        return activity.readEmailLocally(this);
    }
    private void getUserData(String email){
        UserModelHelper.getInstance().getUserByEmail(email, new Callback<UserModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    UserModel userModel = response.body();
                    assert userModel != null;
                    role = userModel.getRole();
                    if(userModel.getRole().equals("user")){
                        layoutAdmin.setVisibility(View.GONE);
                    }
                    if(userModel.getRole().equals("admin")){
                        layoutUser.setVisibility(View.GONE);
                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
    }
    private void updateTrangThai(int idHoaDon, int trangThai){
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<Void> call = apiInterface.updateTrangThaiHoaDon(idHoaDon, trangThai);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    if(role.equals("user")){
                        Intent intent = new Intent(getApplicationContext(), DanhGiaActivity.class);
                        Toast.makeText(ChiTietHoaDonActivity.this, "Nhận hàng thành công", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }else{
                        finish();
                        Toast.makeText(ChiTietHoaDonActivity.this, "chuyển trạng thái đơn hàng", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
    private void huyDon(int idHoaDon, int trangThai){
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<Void> call = apiInterface.updateTrangThaiHoaDon(idHoaDon, trangThai);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(ChiTietHoaDonActivity.this, "Dơn hàng đã được hủy", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
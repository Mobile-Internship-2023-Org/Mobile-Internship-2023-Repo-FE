package com.example.foody_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foody_app.MainActivity;
import com.example.foody_app.R;
import com.example.foody_app.adapter.MonanAdapter;
import com.example.foody_app.dialogs.ChangeDiaChiDialog;
import com.example.foody_app.dialogs.ChangePaymentMethodDialog;
import com.example.foody_app.models.ShoppingCartItem;
import com.example.foody_app.models.UserModel;
import com.example.foody_app.utils.APIClient;
import com.example.foody_app.utils.APIInterface;
import com.example.foody_app.utils.OnDiaChiChangedListener;
import com.example.foody_app.utils.OnThanhToanChangedListener;
import com.example.foody_app.utils.UserModelHelper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class XacNhanDonHangActivity extends AppCompatActivity
        implements OnDiaChiChangedListener, OnThanhToanChangedListener {
    private TextView nameAndPhoneTv, addressTv, deliveryTimeTv,
            totalItemsTv, totaItemsPriceTv, totalPriceTv,
            changeDeliveryTv, changePaymentMethodTv, showMoreTv;
    private Button confirmOrderBtn;

    private MonanAdapter monanAdapter;
    private RecyclerView recyclerViewMonan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_nhan_don_hang);

        nameAndPhoneTv = findViewById(R.id.nameAndPhoneTv);
        addressTv = findViewById(R.id.addressTv);
        deliveryTimeTv = findViewById(R.id.deliveryTimeTv);
        totalItemsTv = findViewById(R.id.totalItemsTv);
        totaItemsPriceTv = findViewById(R.id.totaItemsPriceTv);
        totalPriceTv = findViewById(R.id.totalPriceTv);
        changeDeliveryTv = findViewById(R.id.changeDeliveryTv);
        changePaymentMethodTv = findViewById(R.id.changePaymentMethodTv);
        confirmOrderBtn = findViewById(R.id.confirmOrderBtn);
        recyclerViewMonan = findViewById(R.id.recyclerViewMonan);
        showMoreTv = findViewById(R.id.showMoreTv);

        // Retrieve the saved email
        String savedEmail = getEmailFromDangNhap();

        // Get all info of nguoidung
        getNguoidungInfo(savedEmail);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int idNguoiDung = sharedPreferences.getInt("idNguoiDung", -1);

        // Get id giohang
        getIdGioHang(idNguoiDung);

        // Get list of monan in giohang
        getShoppingCartItems(idNguoiDung);

        // Call API to calculate tongTienHoaDon
        getTongTienHoaDon();
        // Total amount of monan
        calculateAndSaveTotalItems();

        // Click event for changing diaChi
        changeDeliveryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeDiaChiDialog();
            }
        });

        // Click event for changing phuongThucTT
        changePaymentMethodTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangePaymentMethodDialog();
            }
        });

        // Create hoadon
        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createHoaDon();
            }
        });

        // Delay before updating UI
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int tongTien = sharedPreferences.getInt("tongTien", 0);
                // Display amount of monan
                totalItemsTv.setText(String.format("Tổng cộng (%d món)", calculateAndSaveTotalItems()));
                // Display total price for all monan
                totaItemsPriceTv.setText(String.format(Locale.getDefault(), "%,dđ", tongTien - 15000));

                // Display total price for hoadon
                totalPriceTv.setText(String.format(Locale.getDefault(), "%,dđ", tongTien));

                // Get the list of monan from SharedPreferences
                String jsonMonanList = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("shoppingCartItems", "[]");
                List<Map<String, Object>> monanList = new Gson().fromJson(jsonMonanList, new TypeToken<List<Map<String, Object>>>() {}.getType());

                // Display list of monan from giohang
                monanAdapter = new MonanAdapter(XacNhanDonHangActivity.this, monanList);
                recyclerViewMonan.setAdapter(monanAdapter);
                recyclerViewMonan.setLayoutManager(new LinearLayoutManager(XacNhanDonHangActivity.this));
            }
        }, 1000);

        showMoreTv.setOnClickListener(v -> {
            monanAdapter.toggleShowAllItems();
            showMoreTv.setText(monanAdapter.isShowAllItems() ? "Thu gọn" : "Xem thêm");
        });

        Toolbar toolbar = findViewById(R.id.toolbar);

        // Back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // Log everything in Shared Preferences
        logSharedPreferences();
    }

    private String getEmailFromDangNhap() {
        DangNhapActivity dangNhapActivity = new DangNhapActivity();
        return dangNhapActivity.readEmailLocally(getApplicationContext());
    }

    private void getNguoidungInfo(String email) {
        UserModelHelper.getInstance().getUserByEmail(email, new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                UserModel nguoidung = response.body();
                if (nguoidung != null) {
                    // Save nguoidung information to shared preferences
                    saveNguoidungInfoLocally(nguoidung);

                    populateUI(nguoidung);
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
    }

    private void saveNguoidungInfoLocally(UserModel nguoidung) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("idNguoiDung", nguoidung.getIdNguoiDung());
        editor.putString("email", nguoidung.getEmail());
        editor.putString("hoTen", nguoidung.getHoTen());
        editor.putString("sdt", nguoidung.getSdt());
        editor.putString("anh", nguoidung.getAnh());
        editor.putString("diaChi", nguoidung.getDiaChi());
        editor.putString("role", nguoidung.getRole());
        editor.apply();
    }
    private void getIdGioHang(int idNguoiDung) {
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<JsonObject> call = apiInterface.getIdGioHang(idNguoiDung);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();

                    if (jsonObject != null && jsonObject.has("idGioHang")) {
                        JsonElement idGioHangElement = jsonObject.get("idGioHang");

                        if (!idGioHangElement.isJsonNull()) {
                            int idGioHang = idGioHangElement.getAsInt();

                            // Log idGioHang from getIdGioHang API
                            Log.d("IdGioHangResponse", "idGioHang: " + idGioHang);

                            // Save idGioHang locally
                            saveIdGioHangLocally(idGioHang);
                        } else {
                            showEmptyCartAlertDialog();
                        }
                    } else {
                        Toast.makeText(XacNhanDonHangActivity.this, "Phản hồi không hợp lệ từ API getIdGioHang", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(XacNhanDonHangActivity.this, "Không truy xuất được idGioHang từ API getIdGioHang", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(XacNhanDonHangActivity.this, "Lỗi khi gọi API getIdGioHang: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("IdGioHangResponse", "Error calling getIdGioHang API: " + t.getMessage());
            }
        });
    }

    // Helper method to show AlertDialog for empty cart
    private void showEmptyCartAlertDialog() {
        new AlertDialog.Builder(XacNhanDonHangActivity.this)
                .setTitle("Giỏ hàng trống")
                .setMessage("Vui lòng thêm món vào giỏ hàng trước khi xác nhận đơn hàng.")
                .setPositiveButton("OK", (dialog, which) -> {
                    // Move back to MainActivity
                    Intent intent = new Intent(XacNhanDonHangActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                })
                .show();
    }

    private void saveIdGioHangLocally(int idGioHang) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("idGioHang", idGioHang);
        editor.apply();
    }

    private void getShoppingCartItems(Integer idNguoiDung) {
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<List<ShoppingCartItem>> call = apiInterface.getMonanByNguoiDung(idNguoiDung);

        call.enqueue(new Callback<List<ShoppingCartItem>>() {
            @Override
            public void onResponse(Call<List<ShoppingCartItem>> call, Response<List<ShoppingCartItem>> response) {
                if (response.isSuccessful()) {
                    List<ShoppingCartItem> shoppingCartItemList = response.body();

                    // Save shopping cart items to shared preferences
                    saveShoppingCartItemsLocally(shoppingCartItemList);

                    // Log shopping cart items
                    for (ShoppingCartItem item : shoppingCartItemList) {
                        Log.d("ShoppingCartItem", "idMonAn: " + item.getIdMonAn() +
                                ", ten: " + item.getTen() +
                                ", giaBan: " + item.getGiaBan() +
                                ", soLuong: " + item.getSoLuong());
                    }
                } else {
                    Toast.makeText(XacNhanDonHangActivity.this, "Không thể truy xuất các mặt hàng trong giỏ hàng", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<ShoppingCartItem>> call, Throwable t) {
                // Handle failure
                Toast.makeText(XacNhanDonHangActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                // Log the error
                Log.e("ShoppingCartItem", "Error: " + t.getMessage());
            }
        });
    }

    private void saveShoppingCartItemsLocally(List<ShoppingCartItem> shoppingCartItemList) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Convert the shoppingCartItemList to a JSON string and save it
        Gson gson = new Gson();
        String json = gson.toJson(shoppingCartItemList);
        editor.putString("shoppingCartItems", json);
        editor.apply();
    }

    private int calculateAndSaveTotalItems() {
        // Retrieve saved shopping cart items from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("shoppingCartItems", "");

        // Check if there are saved shopping cart items
        if (!json.isEmpty()) {
            // Convert the JSON string back to a List of ShoppingCartItem
            Gson gson = new Gson();
            List<ShoppingCartItem> shoppingCartItemList = gson.fromJson(json, new TypeToken<List<ShoppingCartItem>>(){}.getType());

            // Calculate the total number of items
            int totalItems = 0;
            for (ShoppingCartItem item : shoppingCartItemList) {
                totalItems += item.getSoLuong();
            }

            // Save the total number of items to SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("totalItems", totalItems);
            editor.apply();

            // Output the number
            Log.d("TotalItems", "Total number of items: " + totalItems);

            return totalItems;
        } else {
            // No saved shopping cart items, set totalItems to 0
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("totalItems", 0);
            editor.apply();

            // Output the number
            Log.d("TotalItems", "Total number of items: 0");
        }
        return 0;
    }

    private void getTongTienHoaDon() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int idNguoiDung = sharedPreferences.getInt("idNguoiDung", -1);

        if (idNguoiDung == -1) {
            Log.e("TongTienResponse", "Invalid idNguoiDung in shared preferences");
            return;
        }

        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<JsonObject> call = apiInterface.getTongTienHoaDon(idNguoiDung);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject result = response.body();
                    if (result != null) {
                        int tongTien = result.get("tongTien").getAsInt();
                        Log.d("TongTienResponse", "Tong Tien: " + tongTien);

                        // Save the tongTien to shared preferences
                        saveTongTienLocally(tongTien);
                    } else {
                        Log.e("TongTienResponse", "Empty response body");
                    }
                } else {
                    Log.e("TongTienResponse", "Unsuccessful response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("TongTienResponse", "API call failed: " + t.getMessage());
            }
        });
    }

    private void saveTongTienLocally(int tongTien) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("tongTien", tongTien);
        editor.apply();
    }

    private void populateUI(UserModel nguoidung) {
        // Populate TextView with name and phone
        String nameAndPhone = nguoidung.getHoTen() + " | " + nguoidung.getSdt();
        nameAndPhoneTv.setText(nameAndPhone);

        // Populate TextView with address
        addressTv.setText(nguoidung.getDiaChi());

        // Populate TextView with delivery time
        String deliveryTime = "Dự kiến - " + getDeliveryTimeForDisplay(30);
        deliveryTimeTv.setText(deliveryTime);
    }

    private String getDeliveryTimeForDisplay(int minutesToAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minutesToAdd);

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a, dd/MM", Locale.getDefault());
        String formattedTime = sdf.format(calendar.getTime());
        Log.d("FormattedTime (for display)", formattedTime);
        return sdf.format(calendar.getTime());
    }

    private void logSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("SharedPreferences", entry.getKey() + ": " + entry.getValue().toString());
        }
    }

    private void showChangeDiaChiDialog() {
        ChangeDiaChiDialog dialog = new ChangeDiaChiDialog(this, this);
        dialog.show();
    }

    @Override
    public void onDiaChiChanged(String newDiaChi) {
        if (!newDiaChi.isEmpty()) {
            TextView addressTv = findViewById(R.id.addressTv);
            addressTv.setText(newDiaChi);

            saveDiaChiLocally(newDiaChi);
        }
    }

    private void saveDiaChiLocally(String diaChi) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("diaChi", diaChi);
        editor.apply();
    }

    private void showChangePaymentMethodDialog() {
        ChangePaymentMethodDialog dialog = new ChangePaymentMethodDialog(this, this);
        dialog.show();
    }

    @Override
    public void onPaymentMethodChanged(String newPaymentMethod) {
        TextView paymentMethodTv = findViewById(R.id.paymentMethodTv);
        paymentMethodTv.setText(newPaymentMethod);

        savePaymentMethodLocally(newPaymentMethod);
    }

    private void savePaymentMethodLocally(String paymentMethod) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("paymentMethod", paymentMethod);
        editor.apply();
    }

    private void createHoaDon() {
        // Get other necessary information from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int idNguoiDung = sharedPreferences.getInt("idNguoiDung", -1);
        int idGioHang = sharedPreferences.getInt("idGioHang", -1);
        String diaChi = sharedPreferences.getString("diaChi", "");
        int tongTienHoaDon = sharedPreferences.getInt("tongTien", -1);
        String comment = "";
        String phuongThucTT = sharedPreferences.getString("phuongThucTT", "Thanh toán tiền mặt");

        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<Void> call = apiInterface.createHoaDon(idNguoiDung, diaChi, tongTienHoaDon, comment, idGioHang, phuongThucTT);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    new AlertDialog.Builder(XacNhanDonHangActivity.this)
                            .setMessage("Đơn hàng đã được tạo thành công")
                            .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                                // Call completeGioHang API
                                completeGioHang(idGioHang);

                                // Move to MainActivity
                                Intent intent = new Intent(XacNhanDonHangActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            })
                            .show();
                } else {
                    new AlertDialog.Builder(XacNhanDonHangActivity.this)
                            .setMessage("Có lỗi xảy ra khi tạo đơn hàng. Vui lòng thử lại.")
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("XacNhanDonHang", "API call failed: " + t.getMessage());
                new AlertDialog.Builder(XacNhanDonHangActivity.this)
                        .setMessage("Có lỗi kết nối. Vui lòng thử lại.")
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            }
        });
    }

    private void completeGioHang(int idGioHang) {
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<Void> call = apiInterface.completeGioHang(idGioHang);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("XacNhanDonHang", "completeGioHang: Success");
                } else {
                    Log.e("XacNhanDonHang", "completeGioHang: Unsuccessful response");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("XacNhanDonHang", "completeGioHang: API call failed: " + t.getMessage());
            }
        });
    }

}
package com.example.foody_app.utils;

import com.example.foody_app.models.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserModelHelper {
    private static UserModelHelper instance;
    private APIInterface apiInterface;

    private UserModelHelper() {
        apiInterface = APIClient.getInstance().create(APIInterface.class);
    }

    public static UserModelHelper getInstance() {
        if (instance == null) {
            synchronized (UserModelHelper.class) {
                if (instance == null) {
                    instance = new UserModelHelper();
                }
            }
        }
        return instance;
    }

    public void getUserByEmail(String email, final Callback<UserModel> callback) {
        Call<UserModel> call = apiInterface.getUserByEmail(email);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()) {
                    UserModel userModel = response.body();
                    callback.onResponse(call, Response.success(userModel));
                } else {
                    callback.onFailure(call, new Throwable(response.errorBody().toString()));
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }
}

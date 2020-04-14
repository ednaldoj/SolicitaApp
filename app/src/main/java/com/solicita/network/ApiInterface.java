package com.solicita.network;

import com.solicita.model.Aluno;
import com.solicita.network.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("api/auth/login")
    Call<UserResponse> postLogin(@Field("email") String email,
                                 @Field("password") String password);

    @GET("api/auth/me")
    Call<Aluno> getUser(@Header("Authorization") String token);

    @POST("api/auth/refresh")
    Call<UserResponse> refreshToken(@Header("Authorization") String token);

}

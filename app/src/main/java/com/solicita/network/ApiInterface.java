package com.solicita.network;

import com.solicita.model.User;
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
    @FormUrlEncoded
    @POST("api/users")
    Call<UserResponse> postCadastro(@Field("name") String name, @Field("cpf") String cpf, @Field("vinculo") String vinculo, @Field("unidade") String unidade, @Field("cursos") String cursos, @Field("email") String email, @Field("password") String password);

    @GET("api/auth/me")
    Call<User> getUser(@Header("Authorization") String token);

    @POST("api/auth/refresh")
    Call<UserResponse> refreshToken(@Header("Authorization") String token);

   // @GET("api/cursos")
    @GET("d282026dc9c4448b4488")
    Call<String> getJSONString();
}

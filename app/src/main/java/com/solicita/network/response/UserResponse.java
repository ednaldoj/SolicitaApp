package com.solicita.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solicita.model.User;

public class UserResponse extends DefaultResponse {

    @Expose
    @SerializedName("status")
    String status;
    @Expose
    @SerializedName("token")
    String token;
    @Expose
    @SerializedName("expires_in") int expires_in;
    @Expose
    @SerializedName("user")
    User user;

    public UserResponse(String error, String message) {
        super(error, message);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

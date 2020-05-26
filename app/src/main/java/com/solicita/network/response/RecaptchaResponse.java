package com.solicita.network.response;

import com.google.gson.annotations.SerializedName;

public class RecaptchaResponse {

    @SerializedName("status")
    private String Status;

    @SerializedName("message")
    private String message;

    public String getStatus() {
        return Status;
    }

    public String getMessage() {
        return message;
    }
}

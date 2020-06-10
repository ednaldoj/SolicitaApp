package com.solicita.network.response;

import com.google.gson.annotations.SerializedName;

public class DefaultResponse {

    @SerializedName("error")
    public String error;

    @SerializedName("message")
    public String message;

    public DefaultResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

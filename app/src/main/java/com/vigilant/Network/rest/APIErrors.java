
package com.vigilant.Network.rest;

import com.google.gson.annotations.SerializedName;

public class APIErrors {

    @SerializedName("code")
    private Long Code;
    @SerializedName("message")
    private String Message;
    @SerializedName("status")
    private String Status;

    public Long getCode() {
        return Code;
    }

    public void setCode(Long code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

}

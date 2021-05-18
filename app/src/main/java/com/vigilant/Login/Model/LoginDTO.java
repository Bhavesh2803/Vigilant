package com.vigilant.Login.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginDTO {

@SerializedName("success")
@Expose
private Boolean success;
@SerializedName("token")
@Expose
private String token;
@SerializedName("refreshToken")
@Expose
private String refreshToken;

public Boolean getSuccess() {
return success;
}

public void setSuccess(Boolean success) {
this.success = success;
}

public String getToken() {
return token;
}

public void setToken(String token) {
this.token = token;
}

public String getRefreshToken() {
return refreshToken;
}

public void setRefreshToken(String refreshToken) {
this.refreshToken = refreshToken;
}

}
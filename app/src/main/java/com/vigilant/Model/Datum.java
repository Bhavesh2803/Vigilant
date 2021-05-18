package com.vigilant.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

@SerializedName("images")
@Expose
private List<String> images = null;
@SerializedName("videos")
@Expose
private List<String> videos = null;
@SerializedName("touristPhoto")
@Expose
private String touristPhoto;
@SerializedName("description")
@Expose
private String description;
@SerializedName("address")
@Expose
private String address;
@SerializedName("passportNumber")
@Expose
private String passportNumber;
@SerializedName("tazkeraNumber")
@Expose
private String tazkeraNumber;
@SerializedName("nationality")
@Expose
private String nationality;
@SerializedName("mobile")
@Expose
private String mobile;
@SerializedName("alertMode")
@Expose
private String alertMode;
@SerializedName("id")
@Expose
private String id;

public List<String> getImages() {
return images;
}

public void setImages(List<String> images) {
this.images = images;
}

public List<String> getVideos() {
return videos;
}

public void setVideos(List<String> videos) {
this.videos = videos;
}

public String getTouristPhoto() {
return touristPhoto;
}

public void setTouristPhoto(String touristPhoto) {
this.touristPhoto = touristPhoto;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public String getAddress() {
return address;
}

public void setAddress(String address) {
this.address = address;
}

public String getPassportNumber() {
return passportNumber;
}

public void setPassportNumber(String passportNumber) {
this.passportNumber = passportNumber;
}

public String getTazkeraNumber() {
return tazkeraNumber;
}

public void setTazkeraNumber(String tazkeraNumber) {
this.tazkeraNumber = tazkeraNumber;
}

public String getNationality() {
return nationality;
}

public void setNationality(String nationality) {
this.nationality = nationality;
}

public String getMobile() {
return mobile;
}

public void setMobile(String mobile) {
this.mobile = mobile;
}

public String getAlertMode() {
return alertMode;
}

public void setAlertMode(String alertMode) {
this.alertMode = alertMode;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

}
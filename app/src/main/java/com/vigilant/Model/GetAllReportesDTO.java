package com.vigilant.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GetAllReportesDTO {

@SerializedName("success")
@Expose
private Boolean success;
@SerializedName("data")
@Expose
private List<Datum> data = null;

public Boolean getSuccess() {
return success;
}

public void setSuccess(Boolean success) {
this.success = success;
}

public List<Datum> getData() {
return data;
}

public void setData(List<Datum> data) {
this.data = data;
}

}

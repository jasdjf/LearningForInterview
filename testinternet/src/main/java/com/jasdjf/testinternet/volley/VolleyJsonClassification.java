package com.jasdjf.testinternet.volley;

import java.util.List;

public class VolleyJsonClassification {

    private List<VolleyJsonData> data;
    //@JsonProperty("errorCode")
    private int errorcode;
    //@JsonProperty("errorMsg")
    private String errormsg;


    public void setData(List<VolleyJsonData> data) {
        this.data = data;
    }
    public List<VolleyJsonData> getData() {
        return data;
    }


    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }
    public int getErrorcode() {
        return errorcode;
    }


    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }
    public String getErrormsg() {
        return errormsg;
    }

}

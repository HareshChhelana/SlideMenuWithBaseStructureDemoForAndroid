package com.slidemenubasestructuredemo.retrofit.models;

import java.util.ArrayList;


public class RetrofitSampleSuccess {

    private int success;
    private String message;
    public ArrayList<RetrofitSample> retrofitSampleArrayList;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<RetrofitSample> getRetrofitSampleArrayList() {
        return retrofitSampleArrayList;
    }

    public void setRetrofitSampleArrayList(ArrayList<RetrofitSample> retrofitSampleArrayList) {
        this.retrofitSampleArrayList = retrofitSampleArrayList;
    }
}

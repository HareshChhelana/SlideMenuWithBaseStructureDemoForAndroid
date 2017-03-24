package com.slidemenubasestructuredemo.retrofit.models;


import com.google.gson.annotations.SerializedName;

public class RetrofitSample {

    @SerializedName("id")
    private String sampleId;
    @SerializedName("title")
    private String sampleTitle;

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getSampleTitle() {
        return sampleTitle;
    }

    public void setSampleTitle(String sampleTitle) {
        this.sampleTitle = sampleTitle;
    }
}

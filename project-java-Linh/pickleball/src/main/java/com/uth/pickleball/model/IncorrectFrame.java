package com.uth.pickleball.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IncorrectFrame {
    private Analysis analysis;
    private String image;
    private double time;

    @JsonProperty("analysis")
    public Analysis getAnalysis() { return analysis; }
    @JsonProperty("analysis")
    public void setAnalysis(Analysis value) { this.analysis = value; }

    @JsonProperty("image")
    public String getImage() { return image; }
    @JsonProperty("image")
    public void setImage(String value) { this.image = value; }

    @JsonProperty("time")
    public double getTime() { return time; }
    @JsonProperty("time")
    public void setTime(double value) { this.time = value; }
}

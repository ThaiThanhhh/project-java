package com.uth.pickleball.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Angles {
    private double elbowLeft;
    private double elbowRight;
    private double kneeLeft;
    private double kneeRight;
    private double shoulderHipLeft;
    private double shoulderHipRight;

    @JsonProperty("elbow_left")
    public double getElbowLeft() { return elbowLeft; }
    @JsonProperty("elbow_left")
    public void setElbowLeft(double value) { this.elbowLeft = value; }

    @JsonProperty("elbow_right")
    public double getElbowRight() { return elbowRight; }
    @JsonProperty("elbow_right")
    public void setElbowRight(double value) { this.elbowRight = value; }

    @JsonProperty("knee_left")
    public double getKneeLeft() { return kneeLeft; }
    @JsonProperty("knee_left")
    public void setKneeLeft(double value) { this.kneeLeft = value; }

    @JsonProperty("knee_right")
    public double getKneeRight() { return kneeRight; }
    @JsonProperty("knee_right")
    public void setKneeRight(double value) { this.kneeRight = value; }

    @JsonProperty("shoulder_hip_left")
    public double getShoulderHipLeft() { return shoulderHipLeft; }
    @JsonProperty("shoulder_hip_left")
    public void setShoulderHipLeft(double value) { this.shoulderHipLeft = value; }

    @JsonProperty("shoulder_hip_right")
    public double getShoulderHipRight() { return shoulderHipRight; }
    @JsonProperty("shoulder_hip_right")
    public void setShoulderHipRight(double value) { this.shoulderHipRight = value; }
}
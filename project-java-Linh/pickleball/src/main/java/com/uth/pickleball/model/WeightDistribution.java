package com.uth.pickleball.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeightDistribution {
     private double left;
    private double right;

    @JsonProperty("left")
    public double getLeft() { return left; }
    @JsonProperty("left")
    public void setLeft(double value) { this.left = value; }

    @JsonProperty("right")
    public double getRight() { return right; }
    @JsonProperty("right")
    public void setRight(double value) { this.right = value; }
}

package com.uth.pickleball.model;

import com.fasterxml.jackson.annotation.*;

public class Analysis {
    private Angles angles;
    private double balance;
    private ErrorDetail[] errors;
    private Technique technique;
    private WeightDistribution weightDistribution;

    @JsonProperty("angles")
    public Angles getAngles() { return angles; }
    @JsonProperty("angles")
    public void setAngles(Angles value) { this.angles = value; }

    @JsonProperty("balance")
    public double getBalance() { return balance; }
    @JsonProperty("balance")
    public void setBalance(double value) { this.balance = value; }

    @JsonProperty("errors")
    public ErrorDetail[] getErrors() { return errors; }
    @JsonProperty("errors")
    public void setErrors(ErrorDetail[] value) { this.errors = value; }

    @JsonProperty("technique")
    public Technique getTechnique() { return technique; }
    @JsonProperty("technique")
    public void setTechnique(Technique value) { this.technique = value; }

    @JsonProperty("weight_distribution")
    public WeightDistribution getWeightDistribution() { return weightDistribution; }
    @JsonProperty("weight_distribution")
    public void setWeightDistribution(WeightDistribution value) { this.weightDistribution = value; }
}

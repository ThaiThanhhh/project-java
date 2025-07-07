package com.uth.pickleball.model;

import com.fasterxml.jackson.annotation.*;

public class ErrorSummary {
    private long incorrectWeightDistribution;
    private long straightKnees;

    @JsonProperty("Incorrect weight distribution")
    public long getIncorrectWeightDistribution() { return incorrectWeightDistribution; }
    @JsonProperty("Incorrect weight distribution")
    public void setIncorrectWeightDistribution(long value) { this.incorrectWeightDistribution = value; }

    @JsonProperty("Straight knees")
    public long getStraightKnees() { return straightKnees; }
    @JsonProperty("Straight knees")
    public void setStraightKnees(long value) { this.straightKnees = value; }
}
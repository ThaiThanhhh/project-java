package com.uth.pickleball.model;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum Description {
    INCORRECT_WEIGHT_DISTRIBUTION, STRAIGHT_KNEES;

    @JsonValue
    public String toValue() {
        switch (this) {
            case INCORRECT_WEIGHT_DISTRIBUTION: return "Incorrect weight distribution";
            case STRAIGHT_KNEES: return "Straight knees";
        }
        return null;
    }

    @JsonCreator
    public static Description forValue(String value) throws IOException {
        if (value.equals("Incorrect weight distribution")) return INCORRECT_WEIGHT_DISTRIBUTION;
        if (value.equals("Straight knees")) return STRAIGHT_KNEES;
        throw new IOException("Cannot deserialize Description");
    }
}

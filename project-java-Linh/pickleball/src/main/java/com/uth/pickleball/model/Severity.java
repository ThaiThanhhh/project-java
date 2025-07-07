package com.uth.pickleball.model;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum Severity {
    HIGH, MEDIUM;

    @JsonValue
    public String toValue() {
        switch (this) {
            case HIGH: return "high";
            case MEDIUM: return "medium";
        }
        return null;
    }

    @JsonCreator
    public static Severity forValue(String value) throws IOException {
        if (value.equals("high")) return HIGH;
        if (value.equals("medium")) return MEDIUM;
        throw new IOException("Cannot deserialize Severity");
    }
}
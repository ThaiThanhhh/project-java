package com.uth.pickleball.model;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum VisualCue {
    KNEES_NEARLY_VERTICAL_NO_BEND_ANGLE;

    @JsonValue
    public String toValue() {
        switch (this) {
            case KNEES_NEARLY_VERTICAL_NO_BEND_ANGLE: return "Knees nearly vertical, no bend angle";
        }
        return null;
    }

    @JsonCreator
    public static VisualCue forValue(String value) throws IOException {
        if (value.equals("Knees nearly vertical, no bend angle")) return KNEES_NEARLY_VERTICAL_NO_BEND_ANGLE;
        throw new IOException("Cannot deserialize VisualCue");
    }
}

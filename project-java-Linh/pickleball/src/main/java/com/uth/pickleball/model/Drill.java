package com.uth.pickleball.model;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum Drill {
    PRACTICE_SQUAT_HOLDS_FOR_30_SECONDS, SIDE_STEPS_WITH_BENT_KNEES;

    @JsonValue
    public String toValue() {
        switch (this) {
            case PRACTICE_SQUAT_HOLDS_FOR_30_SECONDS: return "Practice squat holds for 30 seconds";
            case SIDE_STEPS_WITH_BENT_KNEES: return "Side steps with bent knees";
        }
        return null;
    }

    @JsonCreator
    public static Drill forValue(String value) throws IOException {
        if (value.equals("Practice squat holds for 30 seconds")) return PRACTICE_SQUAT_HOLDS_FOR_30_SECONDS;
        if (value.equals("Side steps with bent knees")) return SIDE_STEPS_WITH_BENT_KNEES;
        throw new IOException("Cannot deserialize Drill");
    }
}

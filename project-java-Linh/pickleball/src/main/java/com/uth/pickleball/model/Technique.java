package com.uth.pickleball.model;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum Technique {
    READY_STANCE, VOLLEY;

    @JsonValue
    public String toValue() {
        switch (this) {
            case READY_STANCE: return "ready_stance";
            case VOLLEY: return "volley";
        }
        return null;
    }

    @JsonCreator
    public static Technique forValue(String value) throws IOException {
        if (value.equals("ready_stance")) return READY_STANCE;
        if (value.equals("volley")) return VOLLEY;
        throw new IOException("Cannot deserialize Technique");
    }
}

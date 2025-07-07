package com.uth.pickleball.model;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum Harm {
    REDUCES_MOBILITY_AND_REACTION_ABILITY, REDUCES_MOBILITY_AND_REFLEXES_INCREASES_INJURY_RISK;

    @JsonValue
    public String toValue() {
        switch (this) {
            case REDUCES_MOBILITY_AND_REACTION_ABILITY: return "Reduces mobility and reaction ability";
            case REDUCES_MOBILITY_AND_REFLEXES_INCREASES_INJURY_RISK: return "Reduces mobility and reflexes, increases injury risk";
        }
        return null;
    }

    @JsonCreator
    public static Harm forValue(String value) throws IOException {
        if (value.equals("Reduces mobility and reaction ability")) return REDUCES_MOBILITY_AND_REACTION_ABILITY;
        if (value.equals("Reduces mobility and reflexes, increases injury risk")) return REDUCES_MOBILITY_AND_REFLEXES_INCREASES_INJURY_RISK;
        throw new IOException("Cannot deserialize Harm");
    }
}
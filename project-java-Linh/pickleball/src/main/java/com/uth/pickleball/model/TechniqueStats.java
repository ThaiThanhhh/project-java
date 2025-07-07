package com.uth.pickleball.model;

import com.fasterxml.jackson.annotation.*;

public class TechniqueStats {
    private long backhand;
    private long readyStance;
    private long volley;

    @JsonProperty("backhand")
    public long getBackhand() { return backhand; }
    @JsonProperty("backhand")
    public void setBackhand(long value) { this.backhand = value; }

    @JsonProperty("ready_stance")
    public long getReadyStance() { return readyStance; }
    @JsonProperty("ready_stance")
    public void setReadyStance(long value) { this.readyStance = value; }

    @JsonProperty("volley")
    public long getVolley() { return volley; }
    @JsonProperty("volley")
    public void setVolley(long value) { this.volley = value; }
}

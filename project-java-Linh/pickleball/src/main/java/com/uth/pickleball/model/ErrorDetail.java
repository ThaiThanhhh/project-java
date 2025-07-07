package com.uth.pickleball.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uth.pickleball.model.Description;

public class ErrorDetail {
     private String correction;
    private Description description;
    private Drill[] drills;
    private Harm harm;
    private Severity severity;
    private VisualCue visualCue;

    @JsonProperty("correction")
    public String getCorrection() { return correction; }
    @JsonProperty("correction")
    public void setCorrection(String value) { this.correction = value; }

    @JsonProperty("description")
    public Description getDescription() { return description; }
    @JsonProperty("description")
    public void setDescription(Description value) { this.description = value; }

    @JsonProperty("drills")
    public Drill[] getDrills() { return drills; }
    @JsonProperty("drills")
    public void setDrills(Drill[] value) { this.drills = value; }

    @JsonProperty("harm")
    public Harm getHarm() { return harm; }
    @JsonProperty("harm")
    public void setHarm(Harm value) { this.harm = value; }

    @JsonProperty("severity")
    public Severity getSeverity() { return severity; }
    @JsonProperty("severity")
    public void setSeverity(Severity value) { this.severity = value; }

    @JsonProperty("visual_cue")
    public VisualCue getVisualCue() { return visualCue; }
    @JsonProperty("visual_cue")
    public void setVisualCue(VisualCue value) { this.visualCue = value; }
}

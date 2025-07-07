package com.uth.pickleball.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnalysisResult {
     private double averagePerformance;
    private ErrorSummary errorSummary;
    private IncorrectFrame[] incorrectFrames;
    private long[] performanceOverTime;
    private TechniqueStats techniqueStats;
    private VideoInfo videoInfo;

    @JsonProperty("average_performance")
    public double getAveragePerformance() { return averagePerformance; }
    @JsonProperty("average_performance")
    public void setAveragePerformance(double value) { this.averagePerformance = value; }

    @JsonProperty("error_summary")
    public ErrorSummary getErrorSummary() { return errorSummary; }
    @JsonProperty("error_summary")
    public void setErrorSummary(ErrorSummary value) { this.errorSummary = value; }

    @JsonProperty("incorrect_frames")
    public IncorrectFrame[] getIncorrectFrames() { return incorrectFrames; }
    @JsonProperty("incorrect_frames")
    public void setIncorrectFrames(IncorrectFrame[] value) { this.incorrectFrames = value; }

    @JsonProperty("performance_over_time")
    public long[] getPerformanceOverTime() { return performanceOverTime; }
    @JsonProperty("performance_over_time")
    public void setPerformanceOverTime(long[] value) { this.performanceOverTime = value; }

    @JsonProperty("technique_stats")
    public TechniqueStats getTechniqueStats() { return techniqueStats; }
    @JsonProperty("technique_stats")
    public void setTechniqueStats(TechniqueStats value) { this.techniqueStats = value; }

    @JsonProperty("video_info")
    public VideoInfo getVideoInfo() { return videoInfo; }
    @JsonProperty("video_info")
    public void setVideoInfo(VideoInfo value) { this.videoInfo = value; }
}

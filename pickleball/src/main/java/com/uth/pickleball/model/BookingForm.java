package com.uth.pickleball.model;

import java.util.List;

public class BookingForm {
    private List<Skill> skills;
    private List<Experience> experiences;

    public List<Skill> getSkills() { return skills; }
    public void setSkills(List<Skill> skills) { this.skills = skills; }
    public List<Experience> getExperiences() { return experiences; }
    public void setExperiences(List<Experience> experiences) { this.experiences = experiences; }
}
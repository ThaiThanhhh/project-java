package com.uth.pickleball.service;

import com.uth.pickleball.model.Skill;
import com.uth.pickleball.repositories.ISkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {
    @Autowired
    private ISkillRepository skillRepository;

    public Skill save(Skill skill) {
        return skillRepository.save(skill);
    }

    public List<Skill> getSkillsByCoach(Long coachId) {
        // implement nếu cần
        return null;
    }
}
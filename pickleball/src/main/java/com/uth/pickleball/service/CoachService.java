package com.uth.pickleball.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.uth.pickleball.repositories.ICoachRepository;
import com.uth.pickleball.model.Coach;
import com.uth.pickleball.model.User;

@Service
public class CoachService {
   public final ICoachRepository coachRepository;

    @Autowired
    public CoachService(ICoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }
    public Coach findByUser(User user) {
        return coachRepository.findByUser(user);
    }
}

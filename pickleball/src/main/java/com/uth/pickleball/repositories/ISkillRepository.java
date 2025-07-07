package com.uth.pickleball.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uth.pickleball.model.Booking;
import com.uth.pickleball.model.Skill;
import java.util.List;

@Repository
public interface ISkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findByBooking(Booking booking);

}

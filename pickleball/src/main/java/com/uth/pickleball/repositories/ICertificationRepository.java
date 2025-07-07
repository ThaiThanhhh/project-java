package com.uth.pickleball.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uth.pickleball.model.Certification;
import com.uth.pickleball.model.Coach;
import java.util.List;


@Repository
public interface ICertificationRepository extends JpaRepository<Certification, Long>{
        List<Certification> findByCoach(Coach coach);

}

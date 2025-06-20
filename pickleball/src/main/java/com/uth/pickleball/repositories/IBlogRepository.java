package com.uth.pickleball.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uth.pickleball.model.Blog;

@Repository
public interface IBlogRepository extends JpaRepository<Blog, Long> {



}

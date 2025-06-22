package com.uth.pickleball.repositories;

import com.uth.pickleball.model.VideoLibrary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVideoLibraryRepository extends JpaRepository<VideoLibrary, Long> {
}
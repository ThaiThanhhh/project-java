package com.uth.pickleball.repositories;

import com.uth.pickleball.model.VideoLibrary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IVideoLibraryRepository extends JpaRepository<VideoLibrary, Long> {
}
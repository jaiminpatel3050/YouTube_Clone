package com.program.tech.youtubeclone.repository;

import com.program.tech.youtubeclone.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Video, String>{

}

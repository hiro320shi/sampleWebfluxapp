package com.example.sampleWebfluxapp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
  // Additional query methods can be defined here if needed
  public Post findById(int id);
}

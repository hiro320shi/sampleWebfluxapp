package com.example.sampleWebfluxapp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.sampleWebfluxapp.SampleWebFluxAppApplication;

@RestController
public class SampleRestController {

  @Autowired
  PostRepository repository;

  @RequestMapping("/")
  public String hello() {
    return "Hello Flux!";
  }

  @RequestMapping("/flux")
  public Mono<String> flux() {
    return Mono.just("Hello Flux(Mono).");
  }

  @RequestMapping("/post")
  public Mono<Post> post() {
    Post psot = new Post(0, 0, "dummy", "dummy message...");
    return Mono.just(psot);
  }

  @PostConstruct
  public void init() {
    // Initialize the repository with some data if needed
    Post p1 = new Post(1, 1, "Hello", "Hello Flux!");
    Post p2 = new Post(2, 2, "Sample", "This is a sample post.");
    Post p3 = new Post(3, 3, "ハロー", "これはサンプルです。");
    repository.saveAndFlush(p1);
    repository.saveAndFlush(p2);
    repository.saveAndFlush(p3);
  }

  @RequestMapping(value = "/post{id}")
  public Mono<Post> getPost(@RequestParam int id) {
    Post post = repository.findById(id);
    return Mono.just(post);
  }

  @RequestMapping("/posts")
  public Flux<Object> posts() {
    List<Post> posts = repository.findAll();
    return Flux.fromArray(posts.toArray());
  }
}

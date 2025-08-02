package com.example.sampleWebfluxapp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;

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
  @Transactional
  public void init() {
    saveOrUpdate(1, 1, "Hello", "Hello Flux!");
    saveOrUpdate(2, 2, "Sample", "This is a sample post.");
    saveOrUpdate(3, 3, "ハロー", "これはサンプルです。");
  }

  private void saveOrUpdate(int id, int userId, String title, String body) {
    Post existing = repository.findById(id);
    Post post;

    if (existing == null) {
      // 新規作成: IDはセットせず、DBに任せる
      post = new Post();
      post.userId = userId;
      post.title = title;
      post.body = body;
    } else {
      // 既存データの更新
      post = existing;
      post.title = title;
      post.body = body;
    }

    repository.save(post);
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

  @RequestMapping("/file")
  public Mono<String> file() {
    String result = "";
    try {
      ClassPathResource cr = new ClassPathResource("sample.txt");
      InputStream is = cr.getInputStream();
      InputStreamReader isr = new InputStreamReader(is, "utf-8");
      BufferedReader br = new BufferedReader(isr);
      String line;
      while ((line = br.readLine()) != null) {
        result += line;
      }
    } catch (IOException e) {
      result = e.getMessage();
    }
    return Mono.just(result);
  }
}

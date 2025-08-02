package com.example.sampleWebfluxapp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.persistence.Version;

@Entity
@Table(name = "post")
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  public int id;

  @Column
  public int userId;

  @Column(nullable = false)
  public String title;

  @Column(nullable = false)
  public String body;

  @Version
  private Long version;

  public Post() {
    super();
  }

  public Post(int id, int userId, String title, String body) {
    super();
    this.id = id;
    this.userId = userId;
    this.title = title;
    this.body = body;
  }

  public String toString() {
    return "{id:" + id + ", userId:" + userId + ", title:\"" + title + ", body\"" + body + "\"}";
  }
}

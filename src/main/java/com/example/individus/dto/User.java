package com.example.individus.dto;

public class User {
  private int id;
  private String username;
  private String email;
  private int age;

  public User() {
  }

  public User(int id, String username, String email, int age) {
    this();
    this.id = id;
    this.username = username;
    this.email = email;
    this.age = age;
  }

  public int getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public int getAge() {
    return age;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setAge(int age) {
    this.age = age;
  }
}

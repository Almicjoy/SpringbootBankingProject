package com.demo.springjwt.payload.response;

import java.util.List;

public class JwtResponse {
  private String token;
  private String type = "sender";
  private Long id;
  private String username;
  private String firstname;
  private String lastname;
  private List<String> roles;

  public JwtResponse(String token, Long id, String username, String firstname, String lastname, List<String> roles) {
    this.token = token;
    this.id = id;
    this.username = username;
    this.firstname = firstname;
    this.lastname = lastname;
    this.roles = roles;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getTokenType() {
    return type;
  }

  public void setTokenType(String tokenType) {
    this.type = tokenType;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }
  
  public String getLastname() {
	return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<String> getRoles() {
    return roles;
  }
}

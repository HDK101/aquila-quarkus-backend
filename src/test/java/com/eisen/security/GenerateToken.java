package com.eisen.security;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;

import org.eclipse.microprofile.jwt.Claims;

import io.smallrye.jwt.build.Jwt;

public class GenerateToken {
  /**
   * Generate JWT token
   */
  public static void main(String[] args) {
    String token = Jwt.issuer("https://example.com/issuer")
        .expiresIn(Duration.ofDays(30))
        .upn("jdoe@quarkus.io")
        .groups(new HashSet<>(Arrays.asList("User", "Admin", "walter")))
        .claim("personId", 2)
        .sign();
    System.out.println(token);
  }
}
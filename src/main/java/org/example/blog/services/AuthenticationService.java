package org.example.blog.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    UserDetails authenticate(String username, String password);
    String generateToken(UserDetails userDetails);
    String extractUsername(String token);
    boolean validateToken(String token, UserDetails userDetails);
}

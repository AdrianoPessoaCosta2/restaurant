package br.com.restaurants.login.domain.service;

import br.com.restaurants.database.adapter.UserPersistence;
import br.com.restaurants.login.adpter.request.LoginRequest;
import br.com.restaurants.login.adpter.response.TokenResponse;
import br.com.restaurants.login.configuration.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class LoginService {
    private final UserPersistence userPersistence;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginService(UserPersistence userPersistence, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userPersistence = userPersistence;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public TokenResponse authenticate(LoginRequest request) {
        String password = userPersistence.findPasswordByLogin(request.login())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
        if (!passwordEncoder.matches(request.password(), password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        return new TokenResponse(jwtUtil.generateToken(request.login()));
    }

    public void updatePassword(String id, String password) {
        userPersistence.updatePassword(UUID.fromString(id), password);
    }
}

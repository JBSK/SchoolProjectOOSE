package nl.halewijn.persoonlijkheidstest.application.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHash {

    private static final int hashStrength = 12;

    public String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(hashStrength);
        return passwordEncoder.encode(password);
    }

    public boolean verifyPassword(String password, String passwordHash) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, passwordHash);
    }

}

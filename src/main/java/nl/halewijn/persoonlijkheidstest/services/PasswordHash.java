package nl.halewijn.persoonlijkheidstest.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHash {

    private int HASH_STRENGTH = 12;

    public String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(HASH_STRENGTH);
        return passwordEncoder.encode(password);
    }

    public boolean verifyPassword(String password, String passwordHash) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, passwordHash);
    }

}

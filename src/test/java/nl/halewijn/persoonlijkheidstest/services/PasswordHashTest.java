package nl.halewijn.persoonlijkheidstest.services;

import nl.halewijn.persoonlijkheidstest.Application;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringApplicationConfiguration(Application.class)
public class PasswordHashTest {

    private static final String PASSWORD = "abcdef1234";
    private static final String PASSWORD_HASH_CORRECT = "$2a$12$cNfnOaTHJnC4Co2qXcMBKulr2kUxa4ZSCQxGpiu0cOqmjQZ1x7CSi";
    private static final String PASSWORD_HASH_BROKEN = "$2a$12$BROKENBROKENBROKENBROKENBROKENBROKENBROKENBROKENBROKE";

    private PasswordHash ph = new PasswordHash();

    /*
    @Test
    public void hashPasswordTest(){
        String hash = ph.hashPassword(PASSWORD);
        assertEquals(PASSWORD_HASH_CORRECT, hash); // Impossible test, thanks to built-in random salts in Bcrypt. :)
    }
    */

    @Test
    public void verifyPasswordTest(){
        assertTrue(ph.verifyPassword(PASSWORD, PASSWORD_HASH_CORRECT));
        assertFalse(ph.verifyPassword(PASSWORD, PASSWORD_HASH_BROKEN));
    }

}
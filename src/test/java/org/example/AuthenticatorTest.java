package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticatorTest {
    Authenticator authenticator = new Authenticator();
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void authenticate() {
        boolean ans = authenticator.authenticate("staffdeanoffice@iitrpr.ac.in","office","*+r_4RJ:%qMHE.-K");
        assertEquals(true,ans);
        ans = authenticator.authenticate("staffdeanoffice@iitrpr.ac.in","office","*+r_4%qMHE.-K");
        assertEquals(false,ans);
        ans = authenticator.authenticate("staffdeanoffice@iitrpr.ac.in","faculty","*+r_4%qMHE.-K");
        assertEquals(false,ans);
    }
}
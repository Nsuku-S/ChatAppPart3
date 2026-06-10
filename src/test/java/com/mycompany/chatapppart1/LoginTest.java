package com.mycompany.chatapppart1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Login unit tests (carried over from Part 1 & 2).
 * Student: ST10482781
 */
public class LoginTest {

    private Login login;

    @BeforeEach
    public void setUp() {
        login = new Login();
        login.registerUser("John", "Doe", "jo_hn", "Password1!", "+0123456789");
    }

    @Test
    public void testValidUsername() {
        assertTrue(login.checkUserName("jo_hn"), "Valid username should pass.");
    }

    @Test
    public void testInvalidUsername_noUnderscore() {
        assertFalse(login.checkUserName("johnn"), "Username without underscore should fail.");
    }

    @Test
    public void testInvalidUsername_tooLong() {
        assertFalse(login.checkUserName("jo_hnn"), "Username longer than 5 chars should fail.");
    }

    @Test
    public void testValidPassword() {
        assertTrue(login.checkPasswordComplexity("Password1!"), "Valid password should pass.");
    }

    @Test
    public void testInvalidPassword_tooShort() {
        assertFalse(login.checkPasswordComplexity("Pa1!"), "Short password should fail.");
    }

    @Test
    public void testValidCellNumber() {
        assertTrue(login.checkCellPhoneNumber("+0123456789"), "Valid cell number should pass.");
    }

    @Test
    public void testInvalidCellNumber_noPlus() {
        assertFalse(login.checkCellPhoneNumber("0123456789"), "Cell without + should fail.");
    }

    @Test
    public void testLoginSuccess() {
        assertTrue(login.loginUser("jo_hn", "Password1!"), "Correct credentials should log in.");
    }

    @Test
    public void testLoginFailure() {
        assertFalse(login.loginUser("jo_hn", "wrongpass"), "Wrong password should fail.");
    }

    @Test
    public void testLoginStatusMessage() {
        String status = login.returnLoginStatus("jo_hn", "Password1!");
        assertTrue(status.contains("Welcome"), "Should show welcome message on success.");
    }
}

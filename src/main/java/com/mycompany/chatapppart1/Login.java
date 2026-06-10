package com.mycompany.chatapppart1;

/**
 * Handles user registration and login.
 * Student: ST10482781
 */
public class Login {

    private String registeredUsername = "";
    private String registeredPassword = "";
    private String registeredFirstName = "";
    private String registeredLastName = "";
    private String registeredCellNumber = "";

    // ── Registration ──────────────────────────────────────────────

    /**
     * Checks that the username contains an underscore and is 5 chars or less.
     */
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    /**
     * Password must be at least 8 chars, contain a capital letter,
     * a number, and a special character.
     */
    public boolean checkPasswordComplexity(String password) {
        if (password.length() < 8) return false;
        boolean hasUpper   = false;
        boolean hasDigit   = false;
        boolean hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper   = true;
            if (Character.isDigit(c))     hasDigit   = true;
            if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }
        return hasUpper && hasDigit && hasSpecial;
    }

    /**
     * Cell number must start with + and be at most 10 digits long.
     */
    public boolean checkCellPhoneNumber(String cellNumber) {
        if (!cellNumber.startsWith("+")) return false;
        String digits = cellNumber.substring(1);
        return digits.matches("\\d+") && digits.length() <= 10;
    }

    /** Registers the user and returns a status message. */
    public String registerUser(String firstName, String lastName,
                               String username, String password,
                               String cellNumber) {
        StringBuilder sb = new StringBuilder();

        if (checkUserName(username)) {
            sb.append("Username successfully captured.\n");
        } else {
            sb.append("Username is not correctly formatted, "
                    + "please ensure that your username contains an underscore "
                    + "and is no more than five characters in length.\n");
            return sb.toString();
        }

        if (checkPasswordComplexity(password)) {
            sb.append("Password successfully captured.\n");
        } else {
            sb.append("Password is not correctly formatted; "
                    + "please ensure that the password contains at least "
                    + "eight characters, a capital letter, a number, "
                    + "and a special character.\n");
            return sb.toString();
        }

        if (checkCellPhoneNumber(cellNumber)) {
            sb.append("Cell phone number successfully captured.\n");
        } else {
            sb.append("Cell phone number incorrectly formatted or does not "
                    + "contain international code.\n");
            return sb.toString();
        }

        registeredFirstName  = firstName;
        registeredLastName   = lastName;
        registeredUsername   = username;
        registeredPassword   = password;
        registeredCellNumber = cellNumber;
        return sb.toString();
    }

    // ── Login ─────────────────────────────────────────────────────

    /** Returns true if the username and password match the registered user. */
    public boolean loginUser(String username, String password) {
        return registeredUsername.equals(username)
            && registeredPassword.equals(password);
    }

    /** Returns a welcome or failure message after a login attempt. */
    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            return "Welcome " + registeredFirstName + " "
                 + registeredLastName + ", it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }

    // ── Getters ───────────────────────────────────────────────────

    public String getRegisteredUsername()   { return registeredUsername;   }
    public String getRegisteredFirstName()  { return registeredFirstName;  }
    public String getRegisteredLastName()   { return registeredLastName;   }
    public String getRegisteredCellNumber() { return registeredCellNumber; }
}

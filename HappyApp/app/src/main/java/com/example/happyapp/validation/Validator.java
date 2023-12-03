package com.example.happyapp.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    // Regular expression for a valid username (alphanumeric, underscores, and dashes)
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9_-]{8,16}$";

//This pattern ensures that the username consists of only alphanumeric characters,
// and hyphens, and has a length between 3 and 15 characters.
    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9_-]{3,15}$";

    // Validate username
    public static boolean isValidUsername(String username) {
        Pattern pattern = Pattern.compile(USERNAME_REGEX);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    // Validate password
    public static boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}

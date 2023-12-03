package com.example.happyapp.ui.profile;

public class LoggedUser {
    public static long loggedUserId;

    public static long getLoggedUserId() {
        return loggedUserId;
    }

    public static void setLoggedUserId(long loggedUserId) {
        LoggedUser.loggedUserId = loggedUserId;
    }
}

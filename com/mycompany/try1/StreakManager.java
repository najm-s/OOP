package com.mycompany.try1;

import java.time.LocalDate;

public class StreakManager {

    // A permanent, unchanging value used to reset or start a streak.
    private static final int RESET_STREAK_VALUE = 1;

    // Runs after the user logs in successfully.
    // It checks the user's previous login date and updates the login streak.
    public void updateStreak(User user) {
        LocalDate today = LocalDate.now();
        LocalDate lastLoginDate = user.getLastLoginDate();

        // If the user has never logged in before, start streak from 1
        if (lastLoginDate == null) {
            user.setStreakCount(RESET_STREAK_VALUE);
        }

        // used when the user logs into the same day
        // streak is not incremented
        else if (lastLoginDate.equals(today)) {
            return;
        }

        // If the last login was yesterday, continue the streak
        else if (lastLoginDate.plusDays(1).equals(today)) {
            user.setStreakCount(user.getStreakCount() + 1);
        }

        // If the user missed at least one day, reset the streak
        else {
            user.setStreakCount(RESET_STREAK_VALUE);
        }

        //  update the last login date to today
        user.setLastLoginDate(today);
    }
}

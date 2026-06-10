package com.mycompany.try1;

import java.time.LocalDate;

public class StreakManager {

    // Runs after the user logs in successfully.
    // It checks the user's previous login date and updates the login streak.
    public void updateStreak(User user) {
        LocalDate today = LocalDate.now();
        LocalDate lastLoginDate = user.getLastLoginDate();

        // Basic value used when the streak needs to start or restart.
        int resetStreak = 1;

        // If the user has never logged in before, start streak from 1
        if (lastLoginDate == null) {
            user.setStreakCount(resetStreak);
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
            user.setStreakCount(resetStreak);
        }

        //  update the last login date to today
        user.setLastLoginDate(today);
    }
}

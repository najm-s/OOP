package com.mycompany.try1;

import java.time.LocalDate;

public class StreakManager {
    public void updateStreak(User user) {
        LocalDate today = LocalDate.now();
        LocalDate lastLoginDate = user.getLastLoginDate();

        if (lastLoginDate == null) {
            user.setStreakCount(1);
        } else if (lastLoginDate.equals(today)) {
            return;
        } else if (lastLoginDate.plusDays(1).equals(today)) {
            user.setStreakCount(user.getStreakCount() + 1);
        } else {
            user.setStreakCount(1);
        }

        user.setLastLoginDate(today);
    }
}

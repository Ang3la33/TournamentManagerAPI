package com.tournamentmanager.utility;

import java.time.LocalDate;
import java.time.Period;

public class MemberUtils {

    public static int calculateMembershipDurationInYears(LocalDate joinDate) {
        if (joinDate == null) {
            return 0;
        }
        return Period.between(joinDate, LocalDate.now()).getYears();
    }
}

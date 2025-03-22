package com.tournamentmanager.utility;

import com.tournamentmanager.model.MembershipType;

import java.time.LocalDate;
import java.time.Period;

public class MemberUtils {

    public static int calculateMembershipDurationInYears(LocalDate joinDate) {
        if (joinDate == null) {
            return 0;
        }
        return Period.between(joinDate, LocalDate.now()).getYears();
    }

    public static MembershipType determineMembershipType(int years) {
        if (years < 1) {
            return MembershipType.NEW_MEMBER;
        } else if (years <= 4) {
            return MembershipType.REGULAR_MEMBER;
        } else {
            return MembershipType.GOLD_MEMBER;
        }
    }
}

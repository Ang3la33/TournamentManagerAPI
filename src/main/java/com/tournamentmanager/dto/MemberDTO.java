package com.tournamentmanager.dto;

import com.tournamentmanager.model.MembershipType;
import lombok.Data;

@Data
public class MemberDTO {

    private String name;
    private String email;
    private String phone;
    private String address;

    private int membershipDuration;
    private MembershipType membershipType;
}

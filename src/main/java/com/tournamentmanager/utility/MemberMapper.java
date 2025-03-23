package com.tournamentmanager.utility;

import com.tournamentmanager.model.Member;
import com.tournamentmanager.dto.MemberDTO;

public class MemberMapper {

    public static MemberDTO toDTO(Member member) {
        MemberDTO dto = new MemberDTO();
        dto.setName(member.getName());
        dto.setEmail(member.getEmail());
        dto.setPhone(member.getPhone());
        dto.setAddress(member.getAddress());
        dto.setMembershipDuration(member.getMembershipDuration());
        dto.setMembershipType(member.getMembershipType());
        return dto;
    }
}

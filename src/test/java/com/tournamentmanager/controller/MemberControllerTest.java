package com.tournamentmanager.controller;

import com.tournamentmanager.dto.MemberDTO;
import com.tournamentmanager.model.Member;
import com.tournamentmanager.model.MembershipType;
import com.tournamentmanager.service.MemberService;
import com.tournamentmanager.utility.MemberUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static com.tournamentmanager.utility.MemberUtils.*;
import static com.tournamentmanager.utility.MemberMapper.toDTO;

public class MemberControllerTest {

    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberController memberController;

    private Member member;
    private MemberDTO memberDTO;
    private UUID memberId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        memberId = UUID.randomUUID();

        member = new Member();
        member.setId(memberId);
        member.setName("Angela Smith");
        member.setEmail("angela.smith@gmail.com");
        member.setPhone("123-456-7890");
        member.setAddress("123 Some Street");
        member.setMemberJoinDate(LocalDate.of(2020, 1, 1));

        int years = calculateMembershipDurationInYears(member.getMemberJoinDate());
        member.setMembershipDuration(years);
        member.setMembershipType(determineMembershipType(years));

        memberDTO = toDTO(member);
    }

    @Test
    void testGetMemberById_ReturnsMember() {
        when(memberService.getMemberById(memberId)).thenReturn(Optional.of(member));
        ResponseEntity<MemberDTO> response = memberController.getMemberById(memberId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(memberDTO.getName(), response.getBody().getName());
    }

    @Test
    void testGetMemberById_ReturnsNotFound() {
        when(memberService.getMemberById(memberId)).thenReturn(Optional.empty());

        ResponseEntity<MemberDTO> response = memberController.getMemberById(memberId);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetAllMembers_ReturnsAllMembers() {
        when(memberService.getAllMembers()).thenReturn(List.of(member));

        ResponseEntity<List<MemberDTO>> response = memberController.getAllMembers();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(memberDTO.getName(), response.getBody().get(0).getName());
    }

    @Test
    void testCreateMember_ReturnsCreatedMember() {
        when(memberService.createMember(any(Member.class))).thenReturn(member);
        ResponseEntity<MemberDTO> response = memberController.createMember(member);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(memberDTO.getName(), response.getBody().getName());
    }
}

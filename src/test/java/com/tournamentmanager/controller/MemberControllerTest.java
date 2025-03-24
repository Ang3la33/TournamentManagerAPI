package com.tournamentmanager.controller;

import com.tournamentmanager.model.Member;
import com.tournamentmanager.service.MemberService;
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

public class MemberControllerTest {

    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberController memberController;

    private Member member;
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

    }

    @Test
    void testGetMemberById_ReturnsMember() {
        when(memberService.getMemberById(memberId)).thenReturn(Optional.of(member));
        ResponseEntity<Member> response = memberController.getMemberById(memberId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(member.getName(), response.getBody().getName());
    }

    @Test
    void testGetMemberById_ReturnsNotFound() {
        when(memberService.getMemberById(memberId)).thenReturn(Optional.empty());

        ResponseEntity<Member> response = memberController.getMemberById(memberId);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetAllMembers_ReturnsAllMembers() {
        when(memberService.getAllMembers()).thenReturn(List.of(member));

        ResponseEntity<List<Member>> response = memberController.getAllMembers();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(member.getName(), response.getBody().get(0).getName());
    }

    @Test
    void testCreateMember_ReturnsCreatedMember() {
        when(memberService.createMember(any(Member.class))).thenReturn(member);
        ResponseEntity<Member> response = memberController.createMember(member);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(member.getName(), response.getBody().getName());
    }
}

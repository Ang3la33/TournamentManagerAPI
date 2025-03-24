package com.tournamentmanager.service;

import com.tournamentmanager.model.Member;
import com.tournamentmanager.model.MembershipType;
import com.tournamentmanager.model.Tournament;
import com.tournamentmanager.repository.MemberRepository;
import com.tournamentmanager.repository.TournamentRepository;
import com.tournamentmanager.utility.MemberUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private TournamentRepository tournamentRepository;

    @InjectMocks
    private MemberService memberService;

    private Member member;
    private Tournament tournament;
    private UUID memberId;
    private UUID tournamentId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        memberId = UUID.randomUUID();
        tournamentId = UUID.randomUUID();

        member = new Member();
        member.setId(memberId);
        member.setName("Angie Smith");
        member.setEmail("example@gmail.com");
        member.setPhone("123456789");
        member.setAddress("123 Main St");
        member.setMemberJoinDate(LocalDate.of(2020, 1, 1));

        tournament = new Tournament();
        tournament.setId(tournamentId);
        tournament.setLocation("St.John's");
    }

    @Test
    void testGetAllMembers() {
        when(memberRepository.findAll()).thenReturn(List.of(member));
        List<Member> result = memberService.getAllMembers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Angie Smith", result.get(0).getName());
    }

    @Test
    void testGetMemberById_Found() {
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        Optional<Member> result = memberService.getMemberById(memberId);

        assertTrue(result.isPresent());
        assertEquals(memberId, result.get().getId());
    }

    @Test
    void testGetMemberById_NotFound() {
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        Optional<Member> result = memberService.getMemberById(memberId);

        assertFalse(result.isPresent());
    }

    @Test
    void testCreateMember_SetsDurationAndType() {
        when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Member createdMember = memberService.createMember(member);

        int expectedYears = MemberUtils.calculateMembershipDurationInYears(member.getMemberJoinDate());
        MembershipType expectedType = MemberUtils.determineMembershipType(expectedYears);

        assertNotNull(createdMember);
        assertEquals(expectedYears, createdMember.getMembershipDuration());
        assertEquals(expectedType, createdMember.getMembershipType());
    }

    @Test
    void testUpdateMember_ChangesFieldsNotJoinDate() {
        Member existingMember = new Member();
        existingMember.setId(memberId);
        existingMember.setMemberJoinDate(LocalDate.of(2020, 1, 1));

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(existingMember));
        when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> invocation.getArgument(0));

        member.setName("New Name");
        member.setEmail("updated@gmail.com");
        member.setPhone("987654321");
        member.setAddress("123 New St");

        Member updatedMember = memberService.updateMember(memberId, member);

        assertNotNull(updatedMember);
        assertEquals("New Name", updatedMember.getName());
        assertEquals("updated@gmail.com", updatedMember.getEmail());
        assertEquals("987654321", updatedMember.getPhone());
        assertEquals("123 New St", updatedMember.getAddress());

        assertEquals(LocalDate.of(2020, 1, 1), updatedMember.getMemberJoinDate());
    }

    @Test
    void testAddMemberToTournament() {
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));
        when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Member result = memberService.addMemberToTournament(memberId, tournamentId);

        assertTrue(result.getTournaments().contains(tournament));
        assertTrue(tournament.getMembers().contains(member));
    }

}

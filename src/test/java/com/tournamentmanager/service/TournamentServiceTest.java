package com.tournamentmanager.service;

import com.tournamentmanager.dto.MemberDTO;
import com.tournamentmanager.model.Member;
import com.tournamentmanager.utility.MemberUtils;
import com.tournamentmanager.utility.MemberUtils.*;
import com.tournamentmanager.model.Tournament;
import com.tournamentmanager.repository.TournamentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TournamentServiceTest {

    @Mock
    private TournamentRepository tournamentRepository;

    @InjectMocks
    private TournamentService tournamentService;

    private Tournament tournament;
    private Member member;
    private UUID tournamentId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        tournamentId = UUID.randomUUID();

        tournament = new Tournament();
        tournament.setId(tournamentId);
        tournament.setStartDate(LocalDate.of(2025,6,1));
        tournament.setEndDate(LocalDate.of(2025,6,3));
        tournament.setLocation("St.John's");
        tournament.setEntryFee(new BigDecimal("25.00"));
        tournament.setCashPrize(new BigDecimal("1000.00"));

        member = new Member();
        member.setId(UUID.randomUUID());
        member.setName("Angie Smith");
        member.setEmail("angie@email.com");
        member.setPhone("123456789");
        member.setAddress("123 Main St");
        member.setMemberJoinDate(LocalDate.of(2020,6,1));

        int duration = MemberUtils.calculateMembershipDurationInYears(member.getMemberJoinDate());
        member.setMembershipDuration(duration);
        member.setMembershipType(MemberUtils.determineMembershipType(duration));
    }

    @Test
    void testGetAllTournaments() {
        when(tournamentRepository.findAll()).thenReturn(List.of(tournament));

        List<Tournament> result = tournamentService.getAllTournaments();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("St.John's", result.get(0).getLocation());
    }

    @Test
    void testGetTournamentById_Found() {
        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));

        Optional<Tournament> result = tournamentService.getTournamentById(tournamentId);

        assertTrue(result.isPresent());
        assertEquals(tournamentId, result.get().getId());
    }

    @Test
    void testGetTournamentById_NotFound() {
        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.empty());

        Optional<Tournament> result = tournamentService.getTournamentById(tournamentId);

        assertFalse(result.isPresent());
    }

    @Test
    void testGetTournamentsByStartDate() {
        LocalDate startDate = LocalDate.of(2025,6,1);
        when(tournamentRepository.findByStartDate(startDate)).thenReturn(List.of(tournament));

        List<Tournament> result = tournamentService.getTournamentByStartDate(startDate);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(startDate, result.get(0).getStartDate());
    }

    @Test
    void testGetTournamentsByLocation() {
        when(tournamentRepository.findByLocationContainingIgnoreCase("St.John's")).thenReturn(List.of(tournament));

        List<Tournament> result = tournamentService.getTournamentByLocation("St.John's");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getLocation().contains("St.John's"));
    }

    @Test
    void testGetMembersInTournament() {
        tournament.setMembers(Set.of(member));
        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));

        List<MemberDTO> result = tournamentService.getMembersInTournament(tournamentId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Angie Smith", result.get(0).getName());
    }

}

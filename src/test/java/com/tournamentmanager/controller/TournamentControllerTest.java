package com.tournamentmanager.controller;

import com.tournamentmanager.model.Member;
import com.tournamentmanager.utility.MemberUtils;
import com.tournamentmanager.model.Tournament;
import com.tournamentmanager.service.TournamentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TournamentControllerTest {

    @Mock
    private TournamentService tournamentService;

    @InjectMocks
    private TournamentController tournamentController;

    private Tournament tournament;
    private Member member;
    private UUID tournamentId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        tournamentId = UUID.randomUUID();

        tournament = new Tournament();
        tournament.setId(tournamentId);
        tournament.setStartDate(LocalDate.of(2025, 7, 1));
        tournament.setEndDate(LocalDate.of(2025, 7, 3));
        tournament.setLocation("St.John's");
        tournament.setEntryFee(new BigDecimal("25.00"));
        tournament.setCashPrize(new BigDecimal("1000.00"));
        tournament.setMembers(Set.of());

        member = new Member();
        member.setName("Jane Doe");
        member.setEmail("janedoe@gmail.com");
        member.setPhone("709-123-4567");
        member.setAddress("123 Main St");
        member.setMemberJoinDate(LocalDate.of(2020, 1, 1));
        int years = MemberUtils.calculateMembershipDurationInYears(member.getMemberJoinDate());
        member.setMembershipDuration(years);
        member.setMembershipType(MemberUtils.determineMembershipType(years));

    }

    @Test
    void testGetAllTournaments() {
        when(tournamentService.getAllTournaments()).thenReturn(List.of(tournament));

        ResponseEntity<List<Tournament>> response = tournamentController.getAllTournaments();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(List.of(tournament), response.getBody());
    }

    @Test
    void testGetTournamentById_FoundTournament() {
        when(tournamentService.getTournamentById(tournamentId)).thenReturn(Optional.of(tournament));

        ResponseEntity<Tournament> response = tournamentController.getTournamentById(tournamentId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(tournament, response.getBody());
    }

    @Test
    void testGetTournamentById_NotFoundTournament() {
        when(tournamentService.getTournamentById(tournamentId)).thenReturn(Optional.empty());

        ResponseEntity<Tournament> response = tournamentController.getTournamentById(tournamentId);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreateTournament() {
        when(tournamentService.createTournament(any(Tournament.class))).thenReturn(tournament);

        ResponseEntity<Tournament> response = tournamentController.createTournament(tournament);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetTournamentByLocation() {
        when(tournamentService.getTournamentByLocation("St.John's")).thenReturn(List.of(tournament));

        ResponseEntity<List<Tournament>> response = tournamentController.getTournamentsByLocation("St.John's");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("St.John's", response.getBody().get(0).getLocation());
    }

    @Test
    void testGetTournamentByStartDate() {
        LocalDate queryDate = LocalDate.of(2025, 7, 1);
        when(tournamentService.getTournamentByStartDate(queryDate)).thenReturn(List.of(tournament));

        ResponseEntity<List<Tournament>> response = tournamentController.getTournamentsByStartDate(queryDate);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(queryDate, response.getBody().get(0).getStartDate());
    }

    @Test
    void testGetMembersInTournament() {
        when(tournamentService.getMembersInTournament(tournamentId)).thenReturn(List.of(member));

        ResponseEntity<List<Member>> response = tournamentController.getMembersInTournament(tournamentId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(List.of(member), response.getBody());
    }

}

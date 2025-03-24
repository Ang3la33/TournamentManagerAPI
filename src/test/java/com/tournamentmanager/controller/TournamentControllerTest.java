package com.tournamentmanager.controller;

import com.tournamentmanager.dto.MemberDTO;
import com.tournamentmanager.model.Member;
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
import java.util.Set;
import java.util.UUID;

import static com.tournamentmanager.utility.MemberMapper.toDTO;
import static com.tournamentmanager.utility.MemberUtils.calculateMembershipDurationInYears;
import static com.tournamentmanager.utility.MemberUtils.determineMembershipType;
import static org.mockito.Mockito.when;

public class TournamentControllerTest {

    @Mock
    private TournamentService tournamentService;

    @InjectMocks
    private TournamentController tournamentController;

    private Tournament tournament;
    private UUID tournamentId;
    private Member member;
    private MemberDTO memberDTO;

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
        int years = calculateMembershipDurationInYears(member.getMemberJoinDate());
        member.setMembershipDuration(years);
        member.setMembershipType(determineMembershipType(years));

        memberDTO = toDTO(member);
    }

}

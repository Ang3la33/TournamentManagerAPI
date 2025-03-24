package com.tournamentmanager.controller;

import com.tournamentmanager.dto.MemberDTO;
import com.tournamentmanager.model.Member;
import com.tournamentmanager.model.Tournament;
import com.tournamentmanager.service.TournamentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping
    public ResponseEntity<List<Tournament>> getAllTournaments() {
        return ResponseEntity.ok(tournamentService.getAllTournaments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tournament> getTournamentById(@PathVariable UUID id) {
        return tournamentService.getTournamentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tournament> createTournament(@RequestBody Tournament tournament) {
        Tournament created = tournamentService.createTournament(tournament);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/start-date")
    public ResponseEntity<List<Tournament>> getTournamentsByStartDate(@RequestParam LocalDate startDate) {
        List<Tournament> tournaments = tournamentService.getTournamentByStartDate(startDate);
        return ResponseEntity.ok(tournaments);
    }

    @GetMapping("/location")
    public ResponseEntity<List<Tournament>> getTournamentsByLocation(@RequestParam String query) {
        List<Tournament> tournaments = tournamentService.getTournamentByLocation(query);
        return ResponseEntity.ok(tournaments);
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<List<MemberDTO>> getMembersInTournament(@PathVariable UUID id) {
        List<MemberDTO> members = tournamentService.getMembersInTournament(id);
        return ResponseEntity.ok(members);
    }
}

package com.tournamentmanager.controller;

import com.tournamentmanager.model.Member;
import com.tournamentmanager.model.Tournament;
import com.tournamentmanager.service.TournamentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
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
        return ResponseEntity.ok(tournamentService.createTournament(tournament));
    }

    @GetMapping("/start-date")
    public ResponseEntity<List<Tournament>> getTournamentsByStartDate(@RequestParam LocalDate startDate) {
        return ResponseEntity.ok(tournamentService.getTournamentByStartDate(startDate));
    }

    @GetMapping("/location")
    public ResponseEntity<List<Tournament>> getTournamentsByLocation(@RequestParam String query) {
        return ResponseEntity.ok(tournamentService.getTournamentByLocation(query));
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<List<Member>> getMembersInTournament(@PathVariable UUID id) {
        List<Member> members = tournamentService.getMembersInTournament(id);
        return ResponseEntity.ok(members);
    }
}

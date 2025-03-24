package com.tournamentmanager.controller;

import com.tournamentmanager.dto.MemberDTO;
import com.tournamentmanager.model.Tournament;
import com.tournamentmanager.dto.TournamentDTO;
import com.tournamentmanager.service.TournamentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping
    public ResponseEntity<List<TournamentDTO>> getAllTournaments() {
        List<TournamentDTO> tournaments = tournamentService.getAllTournaments()
                .stream()
                .map(TournamentMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(tournaments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentDTO> getTournamentById(@PathVariable UUID id) {
        return tournamentService.getTournamentById(id)
                .map(TournamentMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TournamentDTO> createTournament(@RequestBody Tournament tournament) {
        Tournament created = tournamentService.createTournament(tournament);
        return ResponseEntity.ok(TournamentMapper.toDTO(created));
    }

    @GetMapping("/start-date")
    public ResponseEntity<List<TournamentDTO>> getTournamentsByStartDate(@RequestParam LocalDate startDate) {
        List<TournamentDTO> tournaments = tournamentService.getTournamentByStartDate(startDate)
                .stream()
                .map(TournamentMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(tournaments);
    }

    @GetMapping("/location")
    public ResponseEntity<List<TournamentDTO>> getTournamentsByLocation(@RequestParam String query) {
        List<TournamentDTO> tournaments = tournamentService.getTournamentByLocation(query)
                .stream()
                .map(TournamentMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(tournaments);
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<List<MemberDTO>> getMembersInTournament(@PathVariable UUID id) {
        List<MemberDTO> members = tournamentService.getMembersInTournament(id);
        return ResponseEntity.ok(members);
    }
}

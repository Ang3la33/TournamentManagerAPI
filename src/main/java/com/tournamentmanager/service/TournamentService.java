package com.tournamentmanager.service;

import com.tournamentmanager.dto.MemberDTO;
import com.tournamentmanager.model.Tournament;
import com.tournamentmanager.repository.TournamentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;

    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    public Optional<Tournament> getTournamentById(UUID id) {
        return tournamentRepository.findById(id);
    }

    public Tournament createTournament(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    public List<Tournament> getTournamentByStartDate(LocalDate startDate) {
        return tournamentRepository.findByStartDate(startDate);
    }

    public List<Tournament> getTournamentByLocation(String location) {
        return tournamentRepository.findByLocationContainingIgnoreCase(location);
    }

    public List<MemberDTO> getMembersInTournament(UUID id) {
        return tournamentRepository.findById(id)
                .map(tournament -> tournament.getMembers().stream()
                        .map(MemberMapper::toDTO)
                        .toList()
                )
                .orElseThrow( () -> new RuntimeException("Tournament not found"));
    }
}

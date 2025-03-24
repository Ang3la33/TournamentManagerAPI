package com.tournamentmanager.utility;

import com.tournamentmanager.dto.TournamentDTO;
import com.tournamentmanager.model.Tournament;

public class TournamentMapper {

    public static TournamentDTO toDTO(Tournament tournament) {
        TournamentDTO dto = new TournamentDTO();
        dto.setStartDate(tournament.getStartDate());
        dto.setEndDate(tournament.getEndDate());
        dto.setLocation(tournament.getLocation());
        dto.setEntryFee(tournament.getEntryFee());
        dto.setCashPrize(tournament.getCashPrize());
        return dto;
    }
}

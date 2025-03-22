package com.tournamentmanager.repository;

import com.tournamentmanager.model.Tournament;
import com.tournamentmanager.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

public interface TournamentRepository extends JpaRepository<Tournament, UUID> {

    List<Tournament> findByStartDate(LocalDate startDate);

    List<Tournament> findByLocationContainingIgnoreCase(String location);

    @Query("SELECT t.members FROM Tournament t WHERE t.id = :tournamentId")
    List<Member> findByMembersInTournament(@Param("tournamentId") UUID tournamentId);
}

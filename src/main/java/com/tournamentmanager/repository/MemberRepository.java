package com.tournamentmanager.repository;

import com.tournamentmanager.model.MembershipType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tournamentmanager.model.Member;

import java.util.List;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {

    List<Member> findByNameContainingIgnoreCase(String name);

    List<Member> findByPhone(String phone);

    List<Member> findByMembershipDuration(int membershipDuration);

    List<Member> findByMembershipType(MembershipType membershipType);

    @Query("SELECT m FROM Member m JOIN m.tournaments t WHERE t.startDate = :startDate")
    List<Member> findByTournamentStartDate(@Param("startDate") java.time.LocalDate startDate);
}

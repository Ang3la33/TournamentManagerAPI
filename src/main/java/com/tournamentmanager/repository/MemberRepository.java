package com.tournamentmanager.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tournamentmanager.model.Member;

import java.util.List;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {

    List<Member> findByNameContainingIgnoreCase(String name);

    List<Member> findByPhone(String phone);

    @Query("SELECT m FROM Member m WHERE m.membershipDurationInMonths = :duration")
    List<Member> findByMembershipDuration(@Param("duration") int duration);

    @Query("SELECT m FROM Member m JOIN m.tournaments t WHERE t.startDate = :startDate")
    List<Member> findByTournamentStartDate(@Param("startDate") String startDate);
}

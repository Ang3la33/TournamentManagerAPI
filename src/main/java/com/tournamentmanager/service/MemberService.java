package com.tournamentmanager.service;

import com.tournamentmanager.dto.MemberDTO;
import com.tournamentmanager.model.Member;
import com.tournamentmanager.model.MembershipType;
import com.tournamentmanager.model.Tournament;
import com.tournamentmanager.repository.MemberRepository;
import com.tournamentmanager.repository.TournamentRepository;
import com.tournamentmanager.utility.MemberMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.tournamentmanager.utility.MemberUtils.*;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final TournamentRepository tournamentRepository;

    public MemberService(MemberRepository memberRepository, TournamentRepository tournamentRepository) {
        this.memberRepository = memberRepository;
        this.tournamentRepository = tournamentRepository;
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> getMemberById(UUID id) {
        return memberRepository.findById(id);
    }

    public Member createMember(Member member) {
        int years = calculateMembershipDurationInYears(member.getMemberJoinDate());
        member.setMembershipDuration(years);
        member.setMembershipType(determineMembershipType(years));
        return memberRepository.save(member);
    }

    public Member updateMember(UUID id, Member updatedMember) {
        Member existing = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        existing.setName(updatedMember.getName());
        existing.setEmail(updatedMember.getEmail());
        existing.setPhone(updatedMember.getPhone());
        existing.setAddress(updatedMember.getAddress());

        return memberRepository.save(existing);
    }

    public List<MemberDTO> getAllMemberDTOs() {
        return memberRepository.findAll()
                .stream()
                .map(MemberMapper::toDTO)
                .toList();
    }

    public List<MemberDTO> getMembersByDuration(int years) {
        return memberRepository.findByMembershipDuration(years)
                .stream()
                .map(MemberMapper::toDTO)
                .toList();
    }

    public List<MemberDTO> getMembersByType(MembershipType type) {
        return memberRepository.findByMembershipType(type)
                .stream()
                .map(MemberMapper::toDTO)
                .toList();
    }

    public Member addMemberToTournament(UUID memberId, UUID tournamentId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournament not found"));

        member.getTournaments().add(tournament);
        tournament.getMembers().add(member);

        return memberRepository.save(member);
    }
}

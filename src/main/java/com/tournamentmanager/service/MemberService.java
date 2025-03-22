package com.tournamentmanager.service;

import com.tournamentmanager.model.Member;
import com.tournamentmanager.repository.MemberRepository;
import com.tournamentmanager.repository.TournamentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.tournamentmanager.utility.MemberUtils.calculateMembershipDurationInYears;

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
        int years = calculateMembershipDurationInYears(member.getMemberJoinDate())
    }

}

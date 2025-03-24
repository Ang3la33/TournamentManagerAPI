package com.tournamentmanager.controller;

import com.tournamentmanager.dto.MemberDTO;
import com.tournamentmanager.model.Member;
import com.tournamentmanager.model.MembershipType;
import com.tournamentmanager.service.MemberService;
import com.tournamentmanager.utility.MemberMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
        List<MemberDTO> members = memberService.getAllMembers().stream()
                .map(MemberMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(members);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable UUID id) {
        return memberService.getMemberById(id)
                .map(MemberMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MemberDTO> createMember(@RequestBody Member member) {
        Member created = memberService.createMember(member);
        return ResponseEntity.ok().body(MemberMapper.toDTO(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberDTO> updateMember(@PathVariable UUID id, @RequestBody Member updatedMember) {
        Member updated = memberService.updateMember(id, updatedMember);
        return ResponseEntity.ok().body(MemberMapper.toDTO(updated));
    }

    @GetMapping("/type")
    public ResponseEntity<List<MemberDTO>> getByMembershipType(@RequestParam MembershipType type) {
        List<MemberDTO> members = memberService.getMembersByType(type);
        return ResponseEntity.ok(members);
    }

    @GetMapping("/duration")
    public ResponseEntity<List<MemberDTO>> getByMembershipDuration(@RequestParam int years) {
        List<MemberDTO> members = memberService.getMembersByDuration(years);
        return ResponseEntity.ok(members);
    }

    @PostMapping("/{memberId}/join/{tournamentId}")
    public ResponseEntity<MemberDTO> joinTournament(
            @PathVariable UUID memberId,
            @PathVariable UUID tournamentId) {
        Member updated = memberService.addMemberToTournament(memberId, tournamentId);
        return ResponseEntity.ok(MemberMapper.toDTO(updated));
    }

}

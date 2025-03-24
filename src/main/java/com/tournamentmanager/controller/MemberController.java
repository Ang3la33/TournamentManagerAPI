package com.tournamentmanager.controller;

import com.tournamentmanager.model.Member;
import com.tournamentmanager.model.MembershipType;
import com.tournamentmanager.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable UUID id) {
        return memberService.getMemberById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        return ResponseEntity.ok(memberService.createMember(member));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable UUID id, @RequestBody Member updatedMember) {
        return ResponseEntity.ok(memberService.updateMember(id, updatedMember));
    }

    @GetMapping("/type")
    public ResponseEntity<List<Member>> getByMembershipType(@RequestParam MembershipType type) {
        return ResponseEntity.ok(memberService.getMembersByType(type));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Member>> searchMembersByName(@RequestParam String name) {
        List<Member> members = memberService.getMembersByName(name);
        return ResponseEntity.ok(members);
    }

    @GetMapping("/duration")
    public ResponseEntity<List<Member>> getByMembershipDuration(@RequestParam int years) {
        return ResponseEntity.ok(memberService.getMembersByDuration(years));
    }

    @PostMapping("/{memberId}/join/{tournamentId}")
    public ResponseEntity<Member> joinTournament(
            @PathVariable UUID memberId,
            @PathVariable UUID tournamentId) {
        return ResponseEntity.ok(memberService.addMemberToTournament(memberId, tournamentId));
    }

}

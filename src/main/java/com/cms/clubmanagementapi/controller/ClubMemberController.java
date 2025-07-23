package com.cms.clubmanagementapi.controller;

import com.cms.clubmanagementapi.model.ClubMember;
import com.cms.clubmanagementapi.service.ClubMemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class ClubMemberController {

    private final ClubMemberService clubMemberService;

    public ClubMemberController(ClubMemberService clubMemberService) {
        this.clubMemberService = clubMemberService;
    }

    // get all members
    @GetMapping
    public List<ClubMember> findAllMembers(){
        return clubMemberService.findAllMembers();
    }

    // create member
    @PostMapping
    public ClubMember createMember(@RequestBody ClubMember clubMember){
        return clubMemberService.createMember(clubMember);
    }

    // delete member
    @DeleteMapping("/{id}")
    public String deleteMember(@PathVariable Long id){
        clubMemberService.deleteMember(id);

        return (id + " deleted.");
    }
}

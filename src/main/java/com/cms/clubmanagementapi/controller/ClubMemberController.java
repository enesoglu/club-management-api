package com.cms.clubmanagementapi.controller;

import com.cms.clubmanagementapi.dto.request.UpdateClubMemberRequest;
import com.cms.clubmanagementapi.dto.response.ClubMemberDTO;
import com.cms.clubmanagementapi.dto.request.CreateClubMemberRequest;
import com.cms.clubmanagementapi.service.ClubMemberService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

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
    public List<ClubMemberDTO> findAllMembers(){
        return clubMemberService.findAllMembers();
    }

    // get member by id
    @GetMapping("/findById/{id}")
    public ResponseEntity<ClubMemberDTO> findMemberById(@PathVariable Long id){
        ClubMemberDTO memberDTO = clubMemberService.findMemberById(id);
        return ResponseEntity.ok(memberDTO);
    }

    // get member by id
    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<ClubMemberDTO> findMemberByEmail(@PathVariable String email){
        ClubMemberDTO memberDTO = clubMemberService.findMemberByEmail(email);
        return ResponseEntity.ok(memberDTO);
    }

    // create member
    @PostMapping
    public ClubMemberDTO createMember(@Valid @RequestBody CreateClubMemberRequest clubMember){
        return clubMemberService.createMember(clubMember);
    }

    // delete member
    @DeleteMapping("/{id}")
    public String deleteMember(@PathVariable Long id){
        clubMemberService.deleteMember(id);
        return (id + " deleted.");
    }

    // update member
    @PutMapping("/{id}")
    public ResponseEntity<ClubMemberDTO> updateMember(@PathVariable long id, @Valid @RequestBody UpdateClubMemberRequest clubMember){
        ClubMemberDTO updatedMember = clubMemberService.updateMember(id, clubMember);
        return ResponseEntity.ok(updatedMember);
    }

    // create members via csv file
    @PostMapping("/import-csv")
    public ResponseEntity<String> importMembersFromCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a \".csv\" file!");
        }
        try {
            long memberCount = clubMemberService.saveMembersFromCsv(file);
            return ResponseEntity.ok().body(memberCount + " members imported successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while parsing file" + e.getMessage());
        }
    }
}

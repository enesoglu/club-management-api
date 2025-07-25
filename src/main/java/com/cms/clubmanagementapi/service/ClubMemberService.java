package com.cms.clubmanagementapi.service;

import com.cms.clubmanagementapi.model.ClubMember;
import com.cms.clubmanagementapi.repo.ClubMemberRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class ClubMemberService {

    private final ClubMemberRepo clubMemberRepo;

    public ClubMemberService(ClubMemberRepo clubMemberRepo) {
        this.clubMemberRepo = clubMemberRepo;
    }

    // get all members
    public List<ClubMember> findAllMembers(){
        return clubMemberRepo.findAll();
    }

    // create new member
    public ClubMember createMember(ClubMember member){
        return clubMemberRepo.save(member);
    }

    // delete a member
    public void deleteMember(@PathVariable Long id) { clubMemberRepo.deleteById(id);}

}

package com.cms.clubmanagementapi.repo;

import com.cms.clubmanagementapi.model.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubMemberRepo extends JpaRepository<ClubMember, Long>{

}
package com.cms.clubmanagementapi.repository;

import com.cms.clubmanagementapi.model.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubMemberRepository extends JpaRepository<ClubMember, Long>{

}
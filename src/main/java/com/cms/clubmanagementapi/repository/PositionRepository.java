package com.cms.clubmanagementapi.repository;

import com.cms.clubmanagementapi.model.ClubMember;
import com.cms.clubmanagementapi.model.role.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    // find a member's active position
    List<Position> findByMemberAndIsActiveTrue(ClubMember member);

}
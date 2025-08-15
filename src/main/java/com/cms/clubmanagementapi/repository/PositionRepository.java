package com.cms.clubmanagementapi.repository;

import com.cms.clubmanagementapi.model.ClubMember;
import com.cms.clubmanagementapi.model.role.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    // find a member's all positions
    @Query("SELECT p FROM Position p LEFT JOIN FETCH p.term WHERE p.member.id = :memberId")
    List<Position> findAllByMemberIdWithTerm(@Param("memberId") Long memberId);

    // find a member's active position
    @Query("SELECT p FROM Position p LEFT JOIN FETCH p.term WHERE p.member.id = :memberId AND p.isActive = true")
    Position findActiveByMemberIdWithTerm(@Param("memberId") Long memberId);
}
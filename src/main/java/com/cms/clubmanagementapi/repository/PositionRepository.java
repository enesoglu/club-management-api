package com.cms.clubmanagementapi.repository;

import com.cms.clubmanagementapi.model.role.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    // how mony positions does the member have?
    long countByMemberId(@Param("memberId") Long memberId);

    // find a member's all positions (with term table joined)
    @Query("SELECT p FROM Position p LEFT JOIN FETCH p.term WHERE p.member.id = :memberId")
    List<Position> findAllByMemberIdWithTerm(@Param("memberId") Long memberId);

    // find a member's active position (with term table joined)
    @Query("SELECT p FROM Position p LEFT JOIN FETCH p.term WHERE p.member.id = :memberId AND p.isActive = true")
    Position findActiveByMemberIdWithTerm(@Param("memberId") Long memberId);

    @Query( "SELECT p FROM Position p " +
            "WHERE p.member.id =(" +
            "   SELECT p_inner.member.id FROM Position p_inner" +
            "   WHERE p_inner.id = :positionId) " +
            "ORDER BY p.id DESC " +
            "OFFSET 1 ROW " +
            "FETCH NEXT 1 ROW ONLY")
    Optional<Position> findLastPositionByPositionId(@Param("positionId") Long positionId);

}
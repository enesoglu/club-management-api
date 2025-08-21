package com.cms.clubmanagementapi.repository;

import com.cms.clubmanagementapi.model.role.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    // find a member's all positions
    @Query("SELECT p FROM Position p LEFT JOIN FETCH p.term WHERE p.member.id = :memberId")
    List<Position> findAllByMemberIdWithTerm(@Param("memberId") Long memberId);

    // find a member's active position
    @Query("SELECT p FROM Position p LEFT JOIN FETCH p.term WHERE p.member.id = :memberId AND p.isActive = true")
    Position findActiveByMemberIdWithTerm(@Param("memberId") Long memberId);

    @Query( "SELECT p FROM Position p " +
            "WHERE p.member.id =(" +
            "   SELECT p_inner.member.id FROM Position p_inner" +
            "   WHERE p_inner.id = :positionId) " +
            "ORDER BY p.id DESC " +
            "OFFSET 1 ROW " +
            "FETCH NEXT 1 ROW ONLY")
    Position findLastPositionByPositionId(@Param("positionId") Long positionId);

    @Query( "SELECT p FROM Position p " +
            "WHERE p.member.id =(" +
            "   SELECT p_inner.member.id FROM Position p_inner" +
            "   WHERE p_inner.id = :positionId) " +
            "ORDER BY p.id DESC ")
    List<Position> findAllPositionsByPositionId(@Param("positionId") Long positionId);

    Position findById(long id);
}
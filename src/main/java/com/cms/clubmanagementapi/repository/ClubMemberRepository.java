package com.cms.clubmanagementapi.repository;

import com.cms.clubmanagementapi.model.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubMemberRepository extends JpaRepository<ClubMember, Long>{

    @Query("SELECT DISTINCT cm FROM ClubMember cm " +
            "LEFT JOIN FETCH cm.positions p LEFT JOIN FETCH p.term " +
            "WHERE cm.id = :id")
    Optional<ClubMember> findByIdWithPositionsAndTerms(@Param("id") Long id);

    @Query("SELECT DISTINCT cm FROM ClubMember cm " +
            "LEFT JOIN FETCH cm.positions p LEFT JOIN FETCH p.term")
    List<ClubMember> findAllWithPositionsAndTerms();

}
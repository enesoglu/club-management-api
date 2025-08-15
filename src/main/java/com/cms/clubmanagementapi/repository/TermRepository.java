package com.cms.clubmanagementapi.repository;

import com.cms.clubmanagementapi.model.role.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TermRepository extends JpaRepository<Term, Long> {

    // finds the term with "isActive = true"
    Optional<Term> findByIsActiveTrue();

}
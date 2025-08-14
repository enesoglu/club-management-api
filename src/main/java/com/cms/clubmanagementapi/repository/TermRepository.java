package com.cms.clubmanagementapi.repository;

import com.cms.clubmanagementapi.model.role.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermRepository extends JpaRepository<Term, Long> {
}
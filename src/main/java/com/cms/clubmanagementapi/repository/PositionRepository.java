package com.cms.clubmanagementapi.repository;

import com.cms.clubmanagementapi.model.role.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
}
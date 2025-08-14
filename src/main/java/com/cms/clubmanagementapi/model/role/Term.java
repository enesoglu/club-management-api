package com.cms.clubmanagementapi.model.role;

import jakarta.persistence.*;
import lombok.Data;

/**
 *   the table "terms" contains active years of the club.
 *   term values used for determine which term (e.g 2024-2025) the position in.
 *   see: model/role/Position.java
 */

@Data
@Entity
@Table(name = "terms")
public class Term {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;                    // e.g: "2024-2025"

    @Column(name = "is_active")
    private boolean isActive = false;       // is it the current term
}
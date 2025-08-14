package com.cms.clubmanagementapi.model.role;

import com.cms.clubmanagementapi.model.ClubMember;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * the entitiy "position" represents every one of member's every title.
 * the answer of "who, in which team, in which term, had this title?"
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "member")
@Entity
@Table(name = "positions")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // member id for matching the position to its owner
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private ClubMember member;

    // team id of the position (member)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    // which term the position is in
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "term_id", nullable = false)
    private Term term;

    // --- position details ---

    // is the position active?
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    // start and end dates of the position
    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    // --- sub role info (nullable) ---

    @Enumerated(EnumType.STRING)
    @Column(name = "executive_title")            // if team == executive
    private ExecutiveTitle executiveTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "crew_committee")             // if team == crew
    private CrewCommittee crewCommittee;
}
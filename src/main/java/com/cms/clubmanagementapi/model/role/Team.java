package com.cms.clubmanagementapi.model.role;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "teams")
public class Team {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // e.g: "Executive Board", "Organization Committee"
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    // e.g: "Ececutive Board -> EXECUTIVE", Organization Comittee -> CREW"
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private MemberRole type;
}
package com.cms.clubmanagementapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "club_members")

// update veritabanından yapılmalı

public class ClubMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",           nullable = false                )
    private String name;

    @Column(name = "email",          nullable = false, unique = true )
    private String email;

    @Column(name = "phone",          nullable = false, unique = true )
    private String phone;

    @Column(name = "school_no",      nullable = false, unique = true )
    private String schoolNo;

    @Column(name = "national_id",    nullable = false, unique = true )
    private String nationalId;

    @Column(name = "year_of_study",  nullable = false                )
    private String yearOfStudy;

    @Column(name = "faculty")
    private String faculty;

    @Column(name = "department")
    private String department;

    @CreationTimestamp
    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private MemberRole role = MemberRole.MEMBER;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private MemberStatus membershipStatus = MemberStatus.ACTIVE;

    @Column(name = "password")
    private String password;
}

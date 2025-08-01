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

    @Column(name = "member_no", nullable = false)
    private int memberNo;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "member_id", unique = true)
    private String memberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private MemberRole role;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "tel_number", unique = true)
    private String phoneNumber;

    @Column(name = "year_of_study")
    private String yearOfStudy;

    @Column(name = "faculty")
    private String faculty;

    @Column(name = "department")
    private String department;

    @CreationTimestamp
    @Column(name = "registration_date", updatable = false)
    private LocalDate registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "membership_status")
    private MemberStatus membershipStatus;

    /*

    // programda planlanan yapı

    @Column(name = "member_id", unique = true, nullable = false)
    private String memberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private MemberRole role;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "tel_number", unique = true, nullable = false)
    private String telNumber;

    @Column(name = "year_of_study", nullable = false)
    private int yearOfStudy;

    @Column(name = "faculty", nullable = false)
    private String faculty;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "membership_status", nullable = false)
    private MemberStatus membershipStatus;
    */

}

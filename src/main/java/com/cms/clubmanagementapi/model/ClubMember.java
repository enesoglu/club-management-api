package com.cms.clubmanagementapi.model;

import com.cms.clubmanagementapi.model.role.Position;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = "positions")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "club_members")
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

    @Enumerated(EnumType.STRING)
    @Column(name = "year_of_study",  nullable = false                )
    private YearOfStudy yearOfStudy;

    @Column(name = "faculty")
    private String faculty;

    @Column(name = "department")
    private String department;

    @CreationTimestamp
    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private MemberStatus membershipStatus = MemberStatus.ACTIVE;

    @Column(name = "password")
    private String password;

    @JsonManagedReference
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Position> positions = new ArrayList<>();

}

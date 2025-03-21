package com.tournamentmanager.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Formula;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(unique = true, nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(nullable = false)
    private LocalDate memberJoinDate;

    @Column(nullable = false)
    private int membershipDurationInMonths;

    @ManyToMany(mappedBy = "members")
    private Set<Tournament> tournaments = new HashSet<>();

    @Formula("(CASE " +
            "WHEN membership_duration_in_months < 12 THEN 'New Member' " +
            "WHEN membership_duration_in_months BETWEEN 12 AND 48 THEN 'Regular Member' " +
            "ELSE 'Gold Member' END)")
    private String membershipType;

    @Formula("(membership_duration_in_months / 12)")
    private int membershipDuration;
}

package com.tournamentmanager.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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

    public String getMembershipType() {
        if (membershipDurationInMonths < 12) {
            return "New Member";
        } else if (membershipDurationInMonths >= 12 && membershipDurationInMonths <=48) {
            return "Regular Member";
        } else {
            return "Gold Member";
        }
    }

    // Convert the membership duration to years
    public int getMembershipInYears() {
        return membershipDurationInMonths / 12;
    }
}

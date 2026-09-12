package com.ricardoemm.user_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "surname", length = 100)
    private String surname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "address", columnDefinition = "text")
    private String address;

    @Column(name = "alerting", nullable = false)
    private boolean alerting;

    @Column(name = "energy_alerting_threshold", nullable = false)
    private double energyAlertingThreshold;
}

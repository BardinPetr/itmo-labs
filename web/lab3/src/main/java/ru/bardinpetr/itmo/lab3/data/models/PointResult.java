package ru.bardinpetr.itmo.lab3.data.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "point_result")
public class PointResult implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(optional = false, fetch = FetchType.LAZY)
//    @JoinColumn(name = "owner_id", nullable = false)
//    private User owner;

    @Embedded
    private Point point;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    private AreaConfig area;

    @Column(nullable = false)
    private Boolean isInside;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private Duration executionTime;
}

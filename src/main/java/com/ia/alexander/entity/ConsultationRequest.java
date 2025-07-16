package com.ia.alexander.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class ConsultationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long consultationRequestId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserSec user;

    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(columnDefinition = "TEXT")
    private String aiResponse;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "consultation_request_id") // Foreign Key en la tabla Question
    private List<Question> questions;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "consultation_request_id") // Foreign Key en la tabla Image
    private List<Image> images;
}

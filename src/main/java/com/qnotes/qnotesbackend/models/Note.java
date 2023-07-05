package com.qnotes.qnotesbackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Note {
    public static final int INITIAL_ORDER_GAP = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer order;
}

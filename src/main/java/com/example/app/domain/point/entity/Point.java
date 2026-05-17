package com.example.app.domain.point.entity;

import com.example.app.domain.user.entity.User;
import com.example.app.global.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "point")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Point extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer amount;

    @Column(length = 255)
    private String reason;
}

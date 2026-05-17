package com.example.app.domain.user.entity;

import com.example.app.domain.mission.entity.UserMission;
import com.example.app.domain.notification.entity.Notification;
import com.example.app.domain.point.entity.Point;
import com.example.app.domain.review.entity.Review;
import com.example.app.global.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 100)
    private String nickname;

    @Column(length = 255)
    private String profileImage;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<UserPreference> preferences = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Notification> notifications = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Point> points = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<UserMission> userMissions = new ArrayList<>();

    public void updateProfile(String nickname, String profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}

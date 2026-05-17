package com.example.umc10th.domain.member.entity;

import com.example.umc10th.domain.member.enums.Gender;
import com.example.umc10th.domain.member.enums.MemberStatus;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    private String address;

    private String phone;

    private Long point;

    @ElementCollection
    @CollectionTable(name = "member_favorite_food", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "food_name")
    private List<String> favoriteFood = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    protected Member() {
    }

    public Member(String name, String email, String password, Gender gender, LocalDate birthDate,
                  String address, List<String> favoriteFood) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.birthDate = birthDate;
        this.address = address;
        this.favoriteFood = favoriteFood == null ? new ArrayList<>() : new ArrayList<>(favoriteFood);
        this.point = 0L;
        this.status = MemberStatus.ACTIVE;
    }

    public void updateTestProfile(String phone, Long point) {
        this.phone = phone;
        this.point = point;
    }

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Long getPoint() {
        return point;
    }

    public String getAddress() {
        return address;
    }
}

package com.example.umc10th.domain.review.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "review_reply")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_reply_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @Column(nullable = false)
    private String content;

    protected Reply() {
    }

    public Reply(Review review, String content) {
        this.review = review;
        this.content = content;
    }
}

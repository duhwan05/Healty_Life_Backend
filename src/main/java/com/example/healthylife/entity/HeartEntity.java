package com.example.healthylife.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "heart")
public class HeartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "heart_sq", unique = true, nullable = false)
    private Long heartSq;

    @ManyToOne
    @JoinColumn(name = "user_sq", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "today_sq", nullable = false)
    private TodayEntity today;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();


    // 상태를 토글하는 메서드
    public void toggleStatus() {
        this.status = !this.status;
    }

}

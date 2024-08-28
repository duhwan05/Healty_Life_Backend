package com.example.healthylife.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Setter
@Getter
@Entity
@Table(name = "heart")
@NoArgsConstructor
public class HeartEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long heartSq;

    @ManyToOne
    @JoinColumn(name = "user_sq", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "today_sq", nullable = false)
    private TodayEntity today;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public HeartEntity(TodayEntity today,UserEntity user) {
        this.today = today;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }


}

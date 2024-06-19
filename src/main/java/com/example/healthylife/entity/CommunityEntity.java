package com.example.healthylife.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@ToString
@Entity
@Getter
@Table(name = "community")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "title_sq", unique = true,nullable = false)
    // 글 제목
    private long titleSq;
}

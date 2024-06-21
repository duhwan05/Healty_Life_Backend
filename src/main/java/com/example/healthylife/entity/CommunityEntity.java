package com.example.healthylife.entity;

import lombok.*;

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
    @Column(name = "community_sq", unique = true,nullable = false)
    // 글 제목 시퀀스
    private long communitySq;

    //글제목
    @Column(name = "community_title",nullable = false,length = 100)
    private String communityTitle;

    //글내용
    @Column(name = "community_body",length = 500)
    private String communityBody;


    //작성자
    @ManyToOne
    @JoinColumn(name = "user_sq")
    private UserEntity user;

    //builder
    @Builder(toBuilder = true)
    public CommunityEntity(long communitySq, String communityTitle, String communityBody, UserEntity user){
        this.communitySq= communitySq;
        this.communityTitle = communityTitle;
        this.communityBody = communityBody;
        this.user = user;
    }




}

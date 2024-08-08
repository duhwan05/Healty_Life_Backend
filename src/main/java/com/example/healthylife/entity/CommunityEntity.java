package com.example.healthylife.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ToString
@Entity
@Getter
@Setter
@Table(name = "community")
@NoArgsConstructor
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
    @Column(name = "community_contents",length = 500)
    private String communityContents;

    //커뮤니티 게시글 작성일
    //communityCreated
    @Column(name = "community_created", length = 150)
    private Date communityCreated;

    //커뮤니티 글 조회수
    //communityCheck
    @Column(name = "community_check", length = 200)
    private long communityCheck;

    //커뮤니티 글 추천수
    //communityRecommend
    @Column(name = "community_recommend",length = 200)
    private long communityRecommend;

    //작성자
    @ManyToOne
    @JoinColumn(name = "user_sq")
    private UserEntity user;

    //댓글
    @JsonManagedReference
    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityCommentsEntity> comments;

    //builder
    @Builder(toBuilder = true)
    public CommunityEntity(long communitySq, String communityTitle, String communityContents, Date communityCreated, long communityCheck, long communityRecommend, UserEntity user){
        this.communitySq= communitySq;
        this.communityTitle = communityTitle;
        this.communityContents = communityContents;
        this.communityCreated = communityCreated;
        this.communityCheck = communityCheck;
        this.communityRecommend = communityRecommend;
        this.user = user;
    }




}

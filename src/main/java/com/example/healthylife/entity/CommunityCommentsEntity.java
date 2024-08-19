package com.example.healthylife.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@ToString
@Entity
@Getter
@Setter
@Table(name = "community_comments")
@NoArgsConstructor
public class CommunityCommentsEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "community_comments_sq", unique = true,nullable = false)
    // 댓글 넘버 시퀀스
    private long communityCommentsSq;

    //커뮤니티 댓글 내용
    @Column(name = "communtiy_comments_contents", length = 100)
    private String communityCommentsContents;

    //커뮤니티 댓글 작성날짜
    @Column(name = "community_comments_created", length = 100)
    private Date communityCommentsCreated;

    //community_comments_created

    //게시글 넘버 시퀀스(foreign key)
    @ManyToOne
    @JoinColumn(name = "community_sq")
    @JsonBackReference
    private CommunityEntity community;


    //작성자
    @ManyToOne
    @JoinColumn(name = "user_sq")
    private UserEntity user;

    //builder
    @Builder(toBuilder = true)
    public CommunityCommentsEntity(long communityCommentsSq, String communityCommentsContents, Date communityCommentsCreated,CommunityEntity community,
                                    UserEntity user){
        this.communityCommentsSq=communityCommentsSq;
        this.communityCommentsContents = communityCommentsContents;
        this.communityCommentsCreated = communityCommentsCreated;
        this.community = community;
        this.user = user;
    }

}

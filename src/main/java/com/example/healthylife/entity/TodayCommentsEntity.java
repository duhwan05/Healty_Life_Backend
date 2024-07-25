package com.example.healthylife.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@ToString
@Entity
@Getter
@Table(name = "today_comments")
@NoArgsConstructor
public class TodayCommentsEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "today_comments_sq", unique = true,nullable = false)
    // 오운완 댓글 넘버 시퀀스
    private long todayCommentsSq;

    //댓글
    @Column(name = "today_comments_contents", length = 100)
    private String todayCommentsContents;

    @Column(name = "today_comments_created", length = 150)
    private Date todayCommentsCreated;

    //게시글 넘버 시퀀스(foreign key)
    @ManyToOne
    @JoinColumn(name = "today_sq")
    private TodayEntity todayEntity;

    //작성자
    @ManyToOne
    @JoinColumn(name = "user_sq")
    private UserEntity user;




    //builder
    @Builder(toBuilder = true)
    public TodayCommentsEntity(long todayCommentsSq, String todayCommentsContents, Date todayCommentsCreated,
                               TodayEntity todayEntity,UserEntity user){
        this.todayCommentsSq = todayCommentsSq;
        this.todayCommentsContents = todayCommentsContents;
        this.todayCommentsCreated = todayCommentsCreated;
        this.todayEntity = todayEntity;
        this.user = user;
    }


    //
    //예시 데이터 여러개 넣기(반복으로 아무거나)
    //테이블 만들기
    //controller에 crud
    //자유게시판 형식
    //글제목,작성자,작성날짜,글내용,댓글
    //글전체조회,키워드검색





}

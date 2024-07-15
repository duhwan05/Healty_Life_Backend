package com.example.healthylife.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
@ToString
@Entity
@Getter
@Table(name = "todo_exercise")
@NoArgsConstructor
public class TodayExerciseEntity implements Serializable {

    //회원 아이디(유저 참조)
    //오운완 글제목
    //오운완 글내용

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "todo_sq", unique = true,nullable = false)
        //오운완 시퀀스
        private long todoSq;

        //오운완 게시물 제목
        @Column(name = "todo_title",nullable = false,length = 100)
        private String todoTitle;

        //오운완 게시물 내용
        @Column(name = "todo_body",length = 500)
        private String todoBody;

//        //오운완 사진 파일 이름
//        @Column(name = "todo_photofile", length = 300)
//        private String todoPhotoFile;
//
//        //오운완 사진 파일 경로
//        @Column(name = "todo_photofilepath", length = 400)
//        private String todoPhotoFilePath;


        //작성자
        @ManyToOne
        @JoinColumn(name = "user_sq")
        private UserEntity user;

        //builder
        @Builder(toBuilder = true)
        public TodayExerciseEntity(long todoSq, String todoTitle, String todoBody,
                                  // String todoPhotoFile,String todoPhotoFilePath,
                                   UserEntity user){
            this.todoSq = todoSq;
            this.todoTitle = todoTitle;
            this.todoBody = todoBody;
           // this.todoPhotoFile = todoPhotoFile;
           // this.todoPhotoFilePath = todoPhotoFilePath;
            this.user = user;
        }
}

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
@Table(name = "today")
@NoArgsConstructor
public class TodayEntity implements Serializable {


        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "today_sq", unique = true,nullable = false)
        //오운완 시퀀스
        private long todaySq;

        //오운완 게시물 내용
        @Column(name = "today_contents",length = 500)
        private String todayContents;

        //오운완 좋아요
    //today_heart
        @Column(name = "today_hearts", length = 150)
        private long todayHearts;

    //오운완 게시물 작성 날짜
    //today_created
        @Column(name = "today_created",length = 150)
        @Temporal(TemporalType.TIMESTAMP)
        private Date todayCreated;


        //작성자
        @ManyToOne
        @JoinColumn(name = "user_sq")
        private UserEntity user;

        //댓글
        @JsonManagedReference
        @OneToMany(mappedBy = "todayEntity", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<TodayCommentsEntity> comments;

        @Column(name = "image_url")
        private String imageurl;

       // builder
        @Builder(toBuilder = true)
        public TodayEntity(long todaySq, String todayContents,
                                  long todayHearts, Date todayCreated,
                                   UserEntity user, String imageurl){
            this.todaySq = todaySq;
            this.todayContents = todayContents;
            this.todayHearts = todayHearts;
            this.todayCreated = todayCreated;
            this.user = user;
            this.imageurl = imageurl;
        }


}

package com.example.healthylife.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "today")
@NoArgsConstructor
public class TodayEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "today_sq", unique = true, nullable = false)
    private long todaySq;

    @Column(name = "today_contents", length = 500)
    private String todayContents;

    @Column(name = "today_hearts", length = 150)
    private long todayHearts;

    @Column(name = "today_created", length = 150)
    @Temporal(TemporalType.TIMESTAMP)
    private Date todayCreated;

    @ManyToOne
    @JoinColumn(name = "user_sq")
    private UserEntity user;

    @JsonManagedReference
    @OneToMany(mappedBy = "todayEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TodayCommentsEntity> comments;

    @Column(name = "image_url")
    private String imageurl;

    @Builder(toBuilder = true)
    public TodayEntity(long todaySq, String todayContents, long todayHearts, Date todayCreated, UserEntity user, String imageurl) {
        this.todaySq = todaySq;
        this.todayContents = todayContents;
        this.todayHearts = todayHearts;
        this.todayCreated = todayCreated;
        this.user = user;
        this.imageurl = imageurl;
    }

    public void incrementLikeCount() {
        this.todayHearts++;
    }

    public void decrementLikeCount() {
        if (this.todayHearts > 0) {
            this.todayHearts--;
        }
    }


}

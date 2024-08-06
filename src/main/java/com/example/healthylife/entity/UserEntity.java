package com.example.healthylife.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@ToString
@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_sq", unique = true, nullable = false)
    private Long userSq;

    @Column(name = "user_id", unique = true, nullable = false, length = 100)
    private String userId;

    @Column(name = "user_pw", length = 100)
    private String userPw;

    @Column(name = "user_name", length = 200)
    private String userName;

    @Column(name = "user_email", length = 300)
    private String userEmail;

    @Column(name = "user_address", length = 400)
    private String userAddress;

    @Column(name = "user_age")
    private Long userAge;

    @Column(name = "user_phone", length = 100)
    private String userPhone;

    // builder
    @Builder(toBuilder = true)
    public UserEntity(long userSq,String userId, String userPw,String userName,String userEmail,String userAddress, long userAge,
                      String userPhone){
        this.userSq = userSq;
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;

        this.userEmail = userEmail;
        this.userAddress = userAddress;
        this.userAge = userAge;
        this.userPhone = userPhone;
    }

    //비밀번호 암호화
    public UserEntity withUserPw(String userPw) {
        return this.toBuilder().userPw(userPw).build();
    }
}

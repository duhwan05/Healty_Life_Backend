package com.example.healthylife.entity;

import lombok.*;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;

@ToString
@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="user_sq", unique = true, nullable = false)
    // user sequence
    private Long userSq;

    // user id
    @Column(name ="user_id", unique = true, nullable = false, length = 100)
    private String userId;

    // user password
    @LastModifiedDate
    @Column(name ="user_pw", length = 100)
    private String userPw;

    // user name
    @Column(name = "user_name", length = 200)
    private String userName;


    // user email
    @Column(name = "user_email",length = 300)
    private String userEmail;

    // user address
    @Column(name = "user_address", length = 400)
    private String userAddress;

    // user age
    @Column(name = "user_age", length = 50)
    private Long userAge;

    // user phone number
    @Column(name = "user_phone", length = 100)
    private Long userPhone;

    // builder
    @Builder(toBuilder = true)
    public UserEntity(long userSq,String userId, String userPw,String userName,String userEmail,String userAddress, long userAge,
                      long userPhone){
        this.userSq = userSq;
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;

        this.userEmail = userEmail;
        this.userAddress = userAddress;
        this.userAge = userAge;
        this.userPhone = userPhone;
    }


}
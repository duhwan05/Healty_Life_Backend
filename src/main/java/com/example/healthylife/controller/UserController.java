package com.example.healthylife.controller;

import com.example.healthylife.entity.UserEntity;
import com.example.healthylife.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/user")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    //회원 전체 조회
    @PostMapping("/users")
    public ResponseEntity<Object> selectUserList(){
        List<UserEntity> userEntityList = userService.userList();
        return new ResponseEntity<>(userEntityList, HttpStatus.OK);
    }

    //회원 등록
    @PostMapping("/user")
    public ResponseEntity<Object> userRegister(@RequestBody UserEntity userEntity){
        UserEntity result = userService.registerUser(userEntity);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //회원 수정
    @PutMapping("/user")
    public ResponseEntity<Object> updateUser(@RequestBody UserEntity userEntity){
        UserEntity result = userService.updateUser(userEntity);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    //회원 삭제
    @DeleteMapping("/user")
    public ResponseEntity<Object> deleteUser(@RequestParam long userSq){
        userService.deleteUserBySq(userSq);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

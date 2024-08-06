package com.example.healthylife.controller;

import com.example.healthylife.entity.UserEntity;
import com.example.healthylife.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;


    public UserController(UserService userService){
        this.userService = userService;
    }


    //회원 단일조회
    @ApiOperation(value = "회원 단일 조회")
    @GetMapping("/one")
    public  Optional<UserEntity> findUserById(@RequestParam String userId){

        return userService.findUserById(userId);
    }



    //회원 전체 조회
    @ApiOperation(value = "회원 전체 조회")
    @GetMapping("/all")
    public ResponseEntity<Object> selectUserList(){
        List<UserEntity> userEntityList = userService.userList();
        return new ResponseEntity<>(userEntityList, HttpStatus.OK);
    }

    //회원 등록

        @ApiOperation(value = "회원 가입")
        @PostMapping("/signup")
        public ResponseEntity<Object> signUpUser (@RequestBody UserEntity userEntity){
            UserEntity result = userService.signUpUser(userEntity);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        //회원 수정
        @ApiOperation(value = "회원 수정")
        @PostMapping("/update")
        public ResponseEntity<Object> updateUser(@RequestBody UserEntity userEntity){
            UserEntity result = userService.updateUser(userEntity);
            return new ResponseEntity<>(result,HttpStatus.OK);
        }

        //회원 삭제
        @ApiOperation(value = "회원 삭제")
        @PostMapping("/delete")
        public ResponseEntity<Object> deleteUser(@RequestParam long userSq){
            userService.deleteUserBySq(userSq);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

package com.example.healthylife.controller;

import com.example.healthylife.entity.TodayEntity;
import com.example.healthylife.entity.UserEntity;
import com.example.healthylife.service.HeartService;
import com.example.healthylife.service.TodayService;
import com.example.healthylife.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/hearts")
public class HeartController {

    @Autowired
    private HeartService heartService;

    @Autowired
    private UserService userService;

    @Autowired
    private TodayService todayService;

    @ApiOperation("하트")
    @PostMapping("/heart/{todaySq}")
    public void like(@PathVariable("todaySq") long todaySq, @RequestParam String userId) {
        Optional<UserEntity> user = userService.findUserById(userId);
        Optional<TodayEntity> today = todayService.findbytodaysq(todaySq);

        if (user.isPresent() && today.isPresent()) {
            heartService.Like(user.get(), today.get());
        } else {
            // Handle the case when user or today entity is not found
            throw new RuntimeException("User or Today entity not found");
        }
    }

    @ApiOperation("하트 수")
    @GetMapping("/count/{todaySq}")
    public Long heartCount(@PathVariable("todaySq") long todaySq) {
        Optional<TodayEntity> today = todayService.findbytodaysq(todaySq);
        return heartService.HeartCount(today.orElse(null));
    }
}

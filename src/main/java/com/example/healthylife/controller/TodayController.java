package com.example.healthylife.controller;

import com.example.healthylife.entity.TodayEntity;
import com.example.healthylife.service.TodayService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/today")
@RestController
@RequiredArgsConstructor
public class TodayController {
    private final TodayService todayService;

    @ApiOperation(value = "오운완 전체조회")
    @GetMapping("/all")
    public List<TodayEntity> todayList(){
        List<TodayEntity> todayEntities = todayService.todayList();
        return todayEntities;
    }

    @ApiOperation(value = "오운완 글작성")
    @PostMapping("/register")
    public TodayEntity register (@RequestBody TodayEntity todayEntity){
        return todayService.registerToday(todayEntity);

    }

    @ApiOperation(value = "오운완 글수정")
    @PostMapping("/update")
    public  TodayEntity update(@RequestBody TodayEntity todayEntity){
        return todayService.updateEntity(todayEntity);
    }

    @ApiOperation(value = "오운완 글삭제")
    @PostMapping("/delete")
    public Boolean delete(@RequestParam long todaySq){
        todayService.deleteByTodaySq(todaySq);
        return true;
    }
}

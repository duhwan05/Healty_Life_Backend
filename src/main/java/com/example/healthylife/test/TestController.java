package com.example.healthylife.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public TestModel test(@RequestParam String input1) {
        return TestModel.builder()
                .data1("testDate:" + input1)
                .data2(12345)
                .build();
    }
}

package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.model.CommonResult;
import org.example.service.ShardingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/sharding")
public class ShardingController {

    @Autowired
    private ShardingService shardingService;

    @GetMapping("/course")
    public CommonResult listCourse(@RequestParam("limit") Integer limit, @RequestParam("offset") Integer offset) {
        return new CommonResult(200, "success", shardingService.listCourse(limit, offset));
    }

    @GetMapping("/order")
    public CommonResult listOrder(@RequestParam("limit") Integer limit, @RequestParam("offset") Integer offset) {
        return new CommonResult(200, "success", shardingService.listOrder(limit, offset));
    }
}

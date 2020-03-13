package com.single.yourme.controller;

import com.single.yourme.core.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/robot")
public class RobotController {

    @RequestMapping("/test")
    public Result test() {

        return Result.builder().success().build();
    }
}

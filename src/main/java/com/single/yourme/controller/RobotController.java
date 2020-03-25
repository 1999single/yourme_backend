package com.single.yourme.controller;

import com.single.yourme.core.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("/robot")
public class RobotController {

    @RequestMapping("/test")
    @ResponseBody
    public Result test() {

        return Result.builder().success().build();
    }

    @RequestMapping("/index")
    public String index() {

        return "index";
    }
}

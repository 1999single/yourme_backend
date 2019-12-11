package com.single.yourme.controller;

import com.single.yourme.entity.Test;
import com.single.yourme.service.ITestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 1999single
 */
@Controller
@RequestMapping("/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private ITestService service;

    @RequestMapping("/main")
    public ModelAndView test() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index2");
        return modelAndView;
    }

    @RequestMapping("/db")
    @ResponseBody
    public Test db() {
        Test test = service.getById("1234");

        logger.info("进入test方法");
        logger.debug("进入test方法");
        logger.warn("进入test方法");
        logger.error("进入test方法");

        return test;
    }


}

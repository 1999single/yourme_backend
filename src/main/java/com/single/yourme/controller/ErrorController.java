package com.single.yourme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 *
 * </p>
 *
 * @author 1999single
 * @since 2019-11-30
 */
@Controller
@RequestMapping("/")
public class ErrorController {

    @RequestMapping("/401")
    public String error401() {
        return "401";
    }
}

package com.ohgiraffers.exceptionhandler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    /* main페이지로 forward 시킴*/

    @GetMapping(value = { "/", "/main"} )
    public String main() { return "main"; }
}
package com.koreait.restapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @RequestMapping(value = {"/login", "/join", "/"})
    public String forwardReactRoutes() {
        return "forward:/index.html";
    }
}

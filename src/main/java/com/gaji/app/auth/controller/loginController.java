package com.gaji.app.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loginController {

    @GetMapping("/")
    public String index() { return "redirect:/login"; }

    @GetMapping("login")
    public String login() {
        return "login/login";
    }



}

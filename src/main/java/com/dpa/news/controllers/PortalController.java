/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dpa.news.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author David Perez
 */
@Controller
@RequestMapping("/")
public class PortalController { // localhost:8080
    @GetMapping("/")
    public String Index() {  // localhost:8080
        return "portal/index.html";
    }
    
    @GetMapping("/signup")
    public String signup() {
        return "signup.html";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login.html";
    }
}

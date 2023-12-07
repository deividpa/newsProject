/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dpa.news.controllers;

import com.dpa.news.exceptions.MyException;
import com.dpa.news.services.UsernameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author David Perez
 */
@Controller
@RequestMapping("/")
public class PortalController { // localhost:8080
    
    @Autowired
    private UsernameService usernameService;
    
    @GetMapping("/")
    public String Index() {  // localhost:8080
        return "portal/index.html";
    }
    
    @GetMapping("/signup")
    public String signup() {
        return "signup.html";
    }
    
    @PostMapping("/signup")
    public String signup(@RequestParam String name, @RequestParam String email, @RequestParam String password
            , @RequestParam String password2, ModelMap model) throws MyException {
        try {
            usernameService.signup(name, email, password, password2);
            model.put("success", "User registered successfully!");
            
            return "portal/index.html";
        } catch(MyException MyEx) {
            model.put("error", MyEx.getMessage());
            model.put("name", name);
            model.put("email", email);
            return "signup.html";
        }    
    }
    
    @GetMapping("/login")
    public String login() {
        return "login.html";
    }
}

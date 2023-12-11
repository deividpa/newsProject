package com.dpa.news.controllers;

import com.dpa.news.entities.Username;
import com.dpa.news.exceptions.MyException;
import com.dpa.news.services.UsernameService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public String login(@RequestParam(required=false) String error, HttpServletRequest request, ModelMap model) {
        /*if(error!=null) {
            model.put("error", "The email or password is wrong");
        }
        return "login.html";*/
        HttpSession session = request.getSession(false);
        if (session != null) {
            String logoutMessage = (String) session.getAttribute("logoutMessage");
            if (logoutMessage != null) {
                model.put("logoutMessage", logoutMessage);
                session.removeAttribute("logoutMessage"); // Remove the session after the model was created
            }
        }

        if (error != null) {
            model.put("error", "El email o la contraseña son incorrectos");
        }
        return "login.html";
    }
    
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/home")
    public String home(HttpSession session) {
        
        Username loggedUser = (Username) session.getAttribute("usernameSession");
        
        if(loggedUser.getRole().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }
                
        return "home.html";
    }
}

package com.dpa.news.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author David Perez
 */
@Controller
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;
    
    @GetMapping("/create")
    public String create() {
        return "news/create.html";
    }
}

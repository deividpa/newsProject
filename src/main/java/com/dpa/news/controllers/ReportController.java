package com.dpa.news.controllers;

import com.dpa.news.exceptions.MyException;
import com.dpa.news.services.ReportService;
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
@RequestMapping("/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;
    
    @GetMapping("/create")
    public String index() {
        return "reports/create.html";
    }
    
    @PostMapping("/create")
    public String create(@RequestParam String title, @RequestParam String description, ModelMap model) {
        try {
            reportService.createReport(title, description);
            model.put("succes", "Report was created successfully!");
        } catch (MyException MyEx) {
            model.put("error", "There was an error creating the report: " + MyEx.getMessage());
            return "reports/create.html";
        }
        
        return "reports/index.html";
    }
}

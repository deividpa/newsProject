package com.dpa.news.controllers;

import com.dpa.news.entities.Report;
import com.dpa.news.exceptions.MyException;
import com.dpa.news.services.ReportService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
            model.put("success", "Report was created successfully!");
        } catch (MyException MyEx) {
            model.put("error", "There was an error creating the report: " + MyEx.getMessage());
            return "reports/create.html";
        }
        
        return "reports/index.html";
    }
    
    @GetMapping("/index")
    public String list(ModelMap model) {
        List<Report> reports = reportService.listReports();
        model.addAttribute("reports", reports);
        
        return "reports/index.html";
    }
    
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, ModelMap model) {
        Report report = reportService.getOne(id);
        model.put("report", report);
        return "reports/edit.html";
    }
    
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, String title, String description, ModelMap model) {
        try {
            List<Report> reports = reportService.listReports();
            reportService.updateReport(id, title, description);
            
            model.put("success", "Report was edited successfully!");  
            model.addAttribute("report", reports);
        } catch(MyException MyEx) {
            model.put("error", MyEx.getMessage());
            return "reports/edit.html";
        }
        //return "redirect:../index";
        return "reports/index.html";
    }
}

package com.dpa.news.services;

import com.dpa.news.entities.Report;
import com.dpa.news.exceptions.MyException;
import com.dpa.news.repositories.ReportRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author David Perez
 */
@Service
public class ReportService {
    
    @Autowired
    private ReportRepository reportRepository;
    
    @Transactional
    public void createReport(String title, String description) throws MyException {
        if(title==null || title.isEmpty()) {
            throw new MyException("The title can't be empty nor null");
        }
        
        Report report = new Report();
        report.setTitle(title);
        report.setDescription(description);
        reportRepository.save(report);
    }
    
    public List<Report> listReports() {
        List<Report> reports = new ArrayList();
        reports = reportRepository.findAll();
        
        return reports;
    }
    
    public void updateReport(Long id, String title, String description) throws MyException {
        if(id==null || title==null || description == null) {
            throw new MyException("Error: Parameter not found.");
        }
        
        Optional<Report> responseReport = reportRepository.findById(id);
        
        if(responseReport.isPresent()) {
            Report updatedReport = responseReport.get();
            updatedReport.setTitle(title);
            updatedReport.setDescription(description);
            reportRepository.save(updatedReport);
        }
    }
    
    public Report getOne(Long id) {
        return reportRepository.getOne(id);
        
    }
}

package com.dpa.news.repositories;

import com.dpa.news.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author David Perez
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query("SELECT r FROM Report r WHERE r.title = :title")
    public Report findByTitle(@Param("title") String title);
     
    // ToDo: Create a new repository method for searching by Categories or Authors
}

package com.dpa.news.repositories;

import com.dpa.news.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author David Perez
 */

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
    
}

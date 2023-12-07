package com.dpa.news.repositories;

import com.dpa.news.entities.Username;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsernameRepository extends JpaRepository<Username, String> {

    @Query("SELECT u FROM Username u WHERE u.email = :email")
    public Username findByEmail(@Param("email") String email);
}
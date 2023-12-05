package com.dpa.news.repositories;

import com.dpa.news.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author David Perez
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
    @Query("SELECT u FROM User u WHERE u.email = :email")
    public User findByEmail(@Param("email") String email);
}

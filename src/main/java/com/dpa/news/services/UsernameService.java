package com.dpa.news.services;


import com.dpa.news.entities.Username;
import com.dpa.news.enums.Role;
import com.dpa.news.exceptions.MyException;
import com.dpa.news.repositories.UsernameRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author David Perez
 */
@Service
public class UsernameService implements UserDetailsService {
    
    @Autowired
    private UsernameRepository usernameRepository;
    
    @Transactional
    public void signup(String name, String email, String password, String password2) throws MyException {
        
        validate(name, email, password, password2);
        
        Username username = new Username();
        
        username.setName(name);
        username.setEmail(email);
        username.setPassword(password);
        username.setRole(Role.USER);
        
        usernameRepository.save(username);
        
    }
    
    private void validate(String name, String email, String password, String password2) throws MyException {

        if (name.isEmpty()) {
            throw new MyException("The name field cannot be null nor empty");
        }
        if (email.isEmpty()) {
            throw new MyException("The email field cannot be null nor empty");
        }
        if (password.isEmpty() || password.length() <= 5) {
            throw new MyException("The password field cannot be null nor empty, and must be at least 5 characters");
        }

        if (!password.equals(password2)) {
            throw new MyException("The passwords must be the same");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Username username = usernameRepository.findByEmail(email);
        
        if(username != null) {
            
            List<GrantedAuthority> permissions = new ArrayList();
            
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + username.getRole().toString());
            
            permissions.add(p);
            
            return new User(username.getEmail(), username.getPassword(), permissions);
            
        } else {
            return null;
        }
       
    }
}

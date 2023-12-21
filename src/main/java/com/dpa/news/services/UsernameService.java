package com.dpa.news.services;


import com.dpa.news.entities.Image;
import com.dpa.news.entities.Username;
import com.dpa.news.enums.Role;
import com.dpa.news.exceptions.MyException;
import com.dpa.news.repositories.UsernameRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author David Perez
 */
@Service
public class UsernameService implements UserDetailsService {
    
    @Autowired
    private UsernameRepository usernameRepository;
    
    @Autowired
    private ImageService imageService;
    
    @Transactional
    public void signup(MultipartFile file, String name, String email, String password, String password2) throws MyException, IOException {
        
        validate(name, email, password, password2);
        
        Username username = new Username();
        
        username.setName(name);
        username.setEmail(email);
        username.setPassword(new BCryptPasswordEncoder().encode(password));
        username.setRole(Role.USER);
        
        Image image = imageService.save(file);
        
        username.setImage(image);
        
        usernameRepository.save(username);
        
    }
    
    @Transactional
    public void update(MultipartFile file, String userID, String name, String email, String password, String password2) throws MyException, IOException {

        validate(name, email, password, password2);

        Optional<Username> response = usernameRepository.findById(userID);
        if (response.isPresent()) {

            Username username = response.get();
            username.setName(name);
            username.setEmail(email);

            username.setPassword(new BCryptPasswordEncoder().encode(password));

            username.setRole(Role.USER);
            
            String imageID = null;
            
            if (username.getImage() != null) {
                imageID = username.getImage().getId();
            }
            
            Image image = imageService.update(file, imageID);
            
            username.setImage(image);
            
            usernameRepository.save(username);
        }

    }
    
    public Username getOne(String id) {
        return usernameRepository.getOne(id);
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException  {
        Username username = usernameRepository.findByEmail(email);
    
        if (username != null) {
            List<GrantedAuthority> permissions = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + username.getRole().toString());
            permissions.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usernameSession", username);

            return new User(username.getEmail(), username.getPassword(), permissions); 
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

    }
}

package com.dpa.news.controllers;

import com.dpa.news.entities.Username;
import com.dpa.news.services.UsernameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author David Perez
 */

@Controller
@RequestMapping("/image")
public class ImageController {
    
    @Autowired
    UsernameService usernameService;
    
    @GetMapping("/profile/{id}")
    public ResponseEntity<byte[]> userImage (@PathVariable String id) {
        Username username = usernameService.getOne(id);
        
         if (username == null || username.getImage() == null || username.getImage().getContent() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        byte[] image = username.getImage().getContent();
        
        String mimeType = username.getImage().getMime();
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(mimeType));
        
        return new ResponseEntity<> (image, headers, HttpStatus.OK);
    }
}

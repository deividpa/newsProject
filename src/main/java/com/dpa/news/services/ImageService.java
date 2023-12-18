package com.dpa.news.services;


import com.dpa.news.entities.Image;
import com.dpa.news.exceptions.MyException;
import com.dpa.news.repositories.ImageRepository;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author David Perez
 */

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;
    
    public Image save(MultipartFile file) throws  MyException {
        
        if(file!=null) {
            try {
                
                Image image = new Image();
                
                image.setMime(file.getContentType());
                image.setName(file.getName());            
                image.setContent(file.getBytes());
                
                return imageRepository.save(image);
                
            } catch(IOException e) {
                System.err.println("Error creating the image: " + e.getMessage());
            }
        }
        return null;
    }
    
    public Image update(MultipartFile file, String id) throws IOException {
        if(file!=null) {
            try {
                
                Image image = new Image();
                
                if(id != null) {
                    Optional<Image> response = imageRepository.findById(id);
                    
                    if(response.isPresent()) {
                        image  = response.get();
                    }
                }
                
                image.setMime(file.getContentType());
                image.setName(file.getName());
                image.setContent(file.getBytes());
                
                return imageRepository.save(image);
                
            } catch(IOException e) {
                System.err.println("Error creating the image: " + e.getMessage());
            }
        }
        
        return null;
    }
}

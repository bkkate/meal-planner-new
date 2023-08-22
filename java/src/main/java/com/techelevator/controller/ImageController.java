package com.techelevator.controller;


import com.techelevator.dao.JdbcImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@CrossOrigin
@RestController
public class ImageController {
    @Autowired
    private JdbcImageDao jdbcImageDao;
    @Autowired
    JdbcTemplate jdbcTemplate;

    // receiving & storing image file
    @PutMapping("/image-upload/{recipeId}")
    public ResponseEntity<String> uploadImage(@PathVariable int recipeId,
                                              @RequestParam("image")MultipartFile file, HttpServletRequest request) {
        // save the image to the database
        try {
            byte[] imageBytes = file.getBytes();

            //content type field value (e.g. image/png)
            // HttpServletRequest object : you can access various details of the incoming HTTP request
            String contentType = request.getParameter("contentType");
            jdbcImageDao.uploadImageToRecipe(imageBytes, contentType, recipeId);
            return ResponseEntity.ok("Image was uploaded successfully");

        } catch(IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }
}

package com.magicmoments.backendapi.controllers;

import com.magicmoments.backendapi.service.srv.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/upload")
public class UploadController {
    @Autowired
    UploadService uploadService;

    @PostMapping("/overlayImage")
    public ResponseEntity<?> overlayImages(@RequestParam("file") MultipartFile uploadedFile,
                                           @RequestParam("file2") MultipartFile uploadedFile2) {

        if (uploadedFile.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File not found");
        }

        try {
            // Define the path where the files will be saved
            String tempDir = System.getProperty("java.io.tmpdir");

            // Save image1
            File file1 = new File(tempDir, uploadedFile.getOriginalFilename());
            uploadedFile.transferTo(file1);

            // Save image2
            File file2 = new File(tempDir, uploadedFile2.getOriginalFilename());
            uploadedFile2.transferTo(file2);

            // Uploaded file
            Image fgImage = ImageIO.read(file1);

            // The item image
            Image bgImage = ImageIO.read(file2);

            BufferedImage finalImage = uploadService.overlayImages(fgImage, bgImage);

            // Convertir la imagen combinada a un array de bytes
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(finalImage, "png", baos);
            byte[] imageBytes = baos.toByteArray();

            // Preparar los headers y el cuerpo de la respuesta
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentLength(imageBytes.length);

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}

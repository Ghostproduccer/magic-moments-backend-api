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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/upload")
public class UploadController {
    @Autowired
    UploadService uploadService;

    @PostMapping("/overlayImage")
    public ResponseEntity<?> overlayImages(@RequestParam("file") MultipartFile uploadedFile,
                                           @RequestParam("base64Image") String base64Image) {

        if (uploadedFile.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File not found");
        }

        try {
            // Uploaded file
            File fileImageFG = File.createTempFile("upload-", uploadedFile.getOriginalFilename());
            uploadedFile.transferTo(fileImageFG);
            Image fgImage = ImageIO.read(fileImageFG);

            // Base64 image (The item image)
            byte[] base64ImageBytes = Base64.getDecoder().decode(base64Image);
            File base64ImageFile = new File("uploads/base64_image.png");
            try (FileOutputStream fos = new FileOutputStream(base64ImageFile)) {
                fos.write(base64ImageBytes);
            }
            Image bgImage = ImageIO.read(base64ImageFile);

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

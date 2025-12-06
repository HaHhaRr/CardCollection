package ru.hahharr.cardcollection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hahharr.cardcollection.s3.S3Service;

import java.io.IOException;

@RestController
@RequestMapping("/creator")
public class AppCreatorController {

    @Autowired
    private S3Service s3Service;

    @GetMapping("/getImage")
    public ResponseEntity<byte[]> getImage(@RequestParam String key) throws IOException {
        return s3Service.downloadImage(key);
    }
}

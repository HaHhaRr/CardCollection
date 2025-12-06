package ru.hahharr.cardcollection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.hahharr.cardcollection.repository.services.CardRepoService;
import ru.hahharr.cardcollection.repository.services.CollectionRepoService;
import ru.hahharr.cardcollection.repository.services.PackRepoService;
import ru.hahharr.cardcollection.s3.S3Service;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CollectionRepoService collectionRepoService;

    @Autowired
    private CardRepoService cardRepoService;

    @Autowired
    private PackRepoService packRepoService;

    @Autowired
    private S3Service s3Service;

    @PostMapping("/addCollection")
    public ResponseEntity<HttpStatus> addCollection(@RequestParam String collectionName) {
        return collectionRepoService.saveNewCollection(collectionName);
    }

    @PostMapping("/addCard")
    public ResponseEntity<HttpStatus> addCard(@RequestParam String cardName,
                                              @RequestParam long collectionId,
                                              @RequestParam int rarity,
                                              @RequestParam MultipartFile image) throws IOException {

        String url = s3Service.uploadImage(image);
        return cardRepoService.saveNewCard(cardName, collectionId,
                rarity, url + "/" + image.getOriginalFilename());
    }

    @PostMapping("/addPack")
    public ResponseEntity<HttpStatus> addPack(@RequestParam String packName,
                                              @RequestParam long collectionId,
                                              @RequestParam int cost,
                                              @RequestParam double epicDropChance,
                                              @RequestParam double rareDropChance,
                                              @RequestBody List<Long> listIds) {
        return packRepoService.saveNewPack(packName, collectionId, cost, epicDropChance, rareDropChance, listIds);
    }
}

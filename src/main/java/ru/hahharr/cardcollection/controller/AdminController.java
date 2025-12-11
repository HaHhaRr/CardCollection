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
import ru.hahharr.cardcollection.models.primitives.id.CollectionId;
import ru.hahharr.cardcollection.models.primitives.rarity.Rarity;
import ru.hahharr.cardcollection.repository.services.CardRepoService;
import ru.hahharr.cardcollection.repository.services.CollectionRepoService;
import ru.hahharr.cardcollection.repository.services.PackRepoService;
import ru.hahharr.cardcollection.s3.S3Service;
import ru.hahharr.cardcollection.utils.dto.AddPackDto;

import java.io.IOException;

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
                                              @RequestParam CollectionId collectionId,
                                              @RequestParam Rarity rarity,
                                              @RequestParam MultipartFile image) throws IOException {
        long cardId = cardRepoService.countAllRows() + 1;
        String url = s3Service.uploadImage(cardId, image);
        return cardRepoService.saveNewCard(cardName, collectionId,
                rarity, url);
    }

    @PostMapping("/addPack")
    public ResponseEntity<HttpStatus> addPack(@RequestBody AddPackDto addPackDto) throws IOException {
        return packRepoService.saveNewPack(addPackDto);
    }
}

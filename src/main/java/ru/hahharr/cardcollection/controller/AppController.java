package ru.hahharr.cardcollection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hahharr.cardcollection.models.dto.CardListResponseDto;
import ru.hahharr.cardcollection.models.dto.CollectionViewResponseDto;
import ru.hahharr.cardcollection.models.dto.PackViewResponseDto;
import ru.hahharr.cardcollection.models.entity.Card;
import ru.hahharr.cardcollection.models.entity.UserCoinState;
import ru.hahharr.cardcollection.models.primitives.id.CardId;
import ru.hahharr.cardcollection.models.primitives.id.CollectionId;
import ru.hahharr.cardcollection.models.primitives.id.PackId;
import ru.hahharr.cardcollection.repository.services.CardRepoService;
import ru.hahharr.cardcollection.repository.services.CollectionRepoService;
import ru.hahharr.cardcollection.repository.services.PackRepoService;
import ru.hahharr.cardcollection.repository.services.UserCoinStateRepoService;
import ru.hahharr.cardcollection.repository.services.UserCollectionRepoService;
import ru.hahharr.cardcollection.security.user.details.CustomUserDetails;

@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired
    private UserCoinStateRepoService userCoinStateRepoService;

    @Autowired
    private PackRepoService packRepoService;

    @Autowired
    private CardRepoService cardRepoService;

    @Autowired
    private CollectionRepoService collectionRepoService;

    @Autowired
    private UserCollectionRepoService userCollectionRepoService;

    @GetMapping("/userState")
    public ResponseEntity<UserCoinState> getUserState(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userCoinStateRepoService.getById(userDetails.getUser().getId());
    }

    @PutMapping("/actions/get_free_coins")
    public ResponseEntity<HttpStatus> getFreeCoins(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userCoinStateRepoService.addFreeCoins(userDetails.getUser().getId());
    }

    @GetMapping("/packs")
    public ResponseEntity<PackViewResponseDto> getAllPacks(@RequestParam("page") int page,
                                                           @RequestParam("size") int size) {
        return packRepoService.getPagePackView(page, size);
    }

    @GetMapping("/pack/{id}")
    public ResponseEntity<CardListResponseDto> getCardsFromPack(@PathVariable("id") PackId packId,
                                                                @RequestParam("page") int page,
                                                                @RequestParam("size") int size) {
        return packRepoService.getCardsFromPack(packId, page, size);
    }

    @GetMapping("/cards/{id}")
    public ResponseEntity<Card> getCardById(@PathVariable("id") CardId cardId) {
        try {
            return new ResponseEntity<>(cardRepoService.getCard(cardId), HttpStatus.OK);
        } catch (NullPointerException nullPointerException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/collections/{id}")
    public ResponseEntity<CardListResponseDto> getCardsFromCollection(@PathVariable("id") CollectionId collectionId,
                                                                      @RequestParam("page") int page,
                                                                      @RequestParam("size") int size) {
        return collectionRepoService.getCardsFromCollection(collectionId, page, size);
    }

    @GetMapping("/packs/{id}")
    public ResponseEntity<PackViewResponseDto> getPacksFromCollection(@PathVariable("id") CollectionId collectionId,
                                                                      @RequestParam("page") int page,
                                                                      @RequestParam("size") int size) {
        return collectionRepoService.getPacksFromCollection(collectionId, page, size);
    }

    @GetMapping("/collections")
    public ResponseEntity<CollectionViewResponseDto> getAllCollections(@RequestParam("page") int page,
                                                                       @RequestParam("size") int size) {
        return collectionRepoService.getPageCollectionView(page, size);
    }

    @GetMapping("/user_collection")
    public ResponseEntity<CardListResponseDto> getUserCollection(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        return userCollectionRepoService.getUserCards(userDetails.getUser().getId(), page, size);
    }
}

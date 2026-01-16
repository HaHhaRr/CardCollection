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
import ru.hahharr.cardcollection.actions.CoinsService;
import ru.hahharr.cardcollection.models.dto.response.CardListResponseDto;
import ru.hahharr.cardcollection.models.dto.response.CollectionViewResponseDto;
import ru.hahharr.cardcollection.models.dto.response.OpenPackResponseDto;
import ru.hahharr.cardcollection.models.dto.response.PackViewResponseDto;
import ru.hahharr.cardcollection.models.entity.Card;
import ru.hahharr.cardcollection.models.entity.UserCoinState;
import ru.hahharr.cardcollection.models.primitives.id.CardId;
import ru.hahharr.cardcollection.models.primitives.id.CollectionId;
import ru.hahharr.cardcollection.models.primitives.id.PackId;
import ru.hahharr.cardcollection.actions.OpenPackService;
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

    @Autowired
    private OpenPackService openPackService;

    @Autowired
    private CoinsService coinsService;

    @GetMapping("/userState")
    public ResponseEntity<UserCoinState> getUserState(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userCoinStateRepoService.getUserState(userDetails.getUser().getId());
    }

    @PutMapping("/actions/get_free_coins")
    public ResponseEntity<HttpStatus> getFreeCoins(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return coinsService.addFreeCoins(userDetails.getUser().getId());
    }

    @PutMapping("/actions/open_pack/{id}")
    public ResponseEntity<OpenPackResponseDto> openPack(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                        @PathVariable("id") PackId packId) {
        return openPackService.openPack(userDetails, packId);
    }

    @GetMapping("/packs")
    public ResponseEntity<PackViewResponseDto> getAllPacks(@RequestParam("offset") int offset,
                                                           @RequestParam("limit") int limit) {
        return packRepoService.getAllPacks(offset, limit);
    }

    @GetMapping("/pack/{id}")
    public ResponseEntity<CardListResponseDto> getCardsFromPack(@PathVariable("id") PackId packId,
                                                                @RequestParam("offset") int offset,
                                                                @RequestParam("limit") int limit) {
        return packRepoService.getCardsFromPack(packId, offset, limit);
    }

    @GetMapping("/cards/{id}")
    public ResponseEntity<Card> getCardById(@PathVariable("id") CardId cardId) {
        return cardRepoService.getCardById(cardId);
    }

    @GetMapping("/collections/{id}")
    public ResponseEntity<CardListResponseDto> getCardsFromCollection(@PathVariable("id") CollectionId collectionId,
                                                                      @RequestParam("offset") int offset,
                                                                      @RequestParam("limit") int limit) {
        return collectionRepoService.getCardsFromCollection(collectionId, offset, limit);
    }

    @GetMapping("/packs/{id}")
    public ResponseEntity<PackViewResponseDto> getPacksFromCollection(@PathVariable("id") CollectionId collectionId,
                                                                      @RequestParam("offset") int offset,
                                                                      @RequestParam("limit") int limit) {
        return collectionRepoService.getPacksFromCollection(collectionId, offset, limit);
    }

    @GetMapping("/collections")
    public ResponseEntity<CollectionViewResponseDto> getAllCollections(@RequestParam("offset") int offset,
                                                                       @RequestParam("limit") int limit) {
        return collectionRepoService.getAllCollections(offset, limit);
    }

    @GetMapping("/user_collection")
    public ResponseEntity<CardListResponseDto> getUserCollection(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam("offset") int offset,
            @RequestParam("limit") int limit,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "direction", required = false) String direction) {
        return userCollectionRepoService.getUserCollection(userDetails.getUser().getId(), offset, limit,
                sortBy, direction);
    }
}

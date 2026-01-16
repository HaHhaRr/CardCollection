package ru.hahharr.cardcollection.actions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hahharr.cardcollection.models.dto.response.OpenPackResponseDto;
import ru.hahharr.cardcollection.models.entity.Card;
import ru.hahharr.cardcollection.models.entity.DropChance;
import ru.hahharr.cardcollection.models.orm.DropChanceOrm;
import ru.hahharr.cardcollection.models.orm.PackOrm;
import ru.hahharr.cardcollection.models.orm.UserCoinStateOrm;
import ru.hahharr.cardcollection.models.orm.UserCollectionOrm;
import ru.hahharr.cardcollection.models.primitives.id.CardId;
import ru.hahharr.cardcollection.models.primitives.id.PackId;
import ru.hahharr.cardcollection.models.primitives.id.UserId;
import ru.hahharr.cardcollection.models.primitives.rarity.Rarity;
import ru.hahharr.cardcollection.repository.services.PackRepoService;
import ru.hahharr.cardcollection.repository.services.UserCoinStateRepoService;
import ru.hahharr.cardcollection.repository.services.UserCollectionRepoService;
import ru.hahharr.cardcollection.security.user.details.CustomUserDetails;
import ru.hahharr.cardcollection.utils.mapper.EntityFromOrmMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OpenPackService {

    private static final int TOTAL_CARDS_FROM_PACK = 5;

    @Autowired
    private PackRepoService packRepoService;

    @Autowired
    private UserCoinStateRepoService userCoinStateRepoService;

    @Autowired
    private UserCollectionRepoService userCollectionRepoService;

    public ResponseEntity<OpenPackResponseDto> openPack(CustomUserDetails userDetails, PackId packId) {
        UserId userId = userDetails.getUser().getId();
        Optional<UserCoinStateOrm> userCoinStateOrmOptional = userCoinStateRepoService.getUserCoinState(userId);
        Optional<UserCollectionOrm> userCollectionOrmOptional = userCollectionRepoService.getUserCollection(userId);
        Optional<PackOrm> packOrmOptional = packRepoService.getPack(packId);

        if (userCoinStateOrmOptional.isEmpty()
                || userCollectionOrmOptional.isEmpty()
                || packOrmOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserCoinStateOrm userCoinStateOrm = userCoinStateOrmOptional.get();
        UserCollectionOrm userCollectionOrm = userCollectionOrmOptional.get();
        PackOrm packOrm = packOrmOptional.get();

        if (userCoinStateOrm.getTotalCoins() < packOrm.getCost()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (userCoinStateRepoService.subtractCoins(userId, packOrm.getCost()) == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            int totalCoins = userCoinStateOrm.getTotalCoins() - packOrm.getCost();

            List<Card> cardDropList = randomCardDrop(packOrm.getDropChanceOrm(), packOrm.getId());
            List<CardId> userCollection = userCollectionOrm.getCards();
            userCollection.addAll(cardDropList.stream().map(Card::getId).toList());
            userCollectionOrm.setCards(userCollection);

            userCollectionRepoService.save(userCollectionOrm);

            return new ResponseEntity<>(new OpenPackResponseDto(totalCoins, cardDropList), HttpStatus.OK);
        } catch (RuntimeException runtimeException) {
            log.error("RuntimeException: ", runtimeException);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<Card> randomCardDrop(DropChanceOrm dropChanceOrm, PackId packId) throws NullPointerException {
        DropChance dropChance = EntityFromOrmMapper.mapDropChance(dropChanceOrm);

        List<Card> allCardsFromPack = packRepoService.getCardsFromPack(packId, 0, Integer.MAX_VALUE)
                .getBody()
                .getCardList();

        Map<Rarity, List<Card>> rarityListMap = allCardsFromPack.stream()
                .collect(Collectors.groupingBy(Card::getRarity));

        return generateCardDropList(dropChance, rarityListMap);
    }

    private List<Card> generateCardDropList(DropChance dropChance, Map<Rarity, List<Card>> rarityListMap) {
        Random random = new Random();
        List<Card> totalDropCardList = new ArrayList<>();

        List<Card> commonCardList = rarityListMap.get(Rarity.COMMON);
        List<Card> rareCardList = rarityListMap.get(Rarity.RARE);
        List<Card> epicCardList = rarityListMap.get(Rarity.EPIC);

        for (int i = 0; i < TOTAL_CARDS_FROM_PACK; i++) {
            double randomValue = random.nextDouble(1);

            if (randomValue < dropChance.getEpicDropChance()) {
                totalDropCardList.add(
                        epicCardList.get(
                                random.nextInt(epicCardList.size())));
            } else if (randomValue < dropChance.getRareDropChance()) {
                totalDropCardList.add(
                        rareCardList.get(
                                random.nextInt(rareCardList.size())));
            } else {
                totalDropCardList.add(
                        commonCardList.get(
                                random.nextInt(commonCardList.size())));
            }
        }
        return totalDropCardList;
    }
}

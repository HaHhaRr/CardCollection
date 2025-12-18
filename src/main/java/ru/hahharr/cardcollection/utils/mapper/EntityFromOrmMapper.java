package ru.hahharr.cardcollection.utils.mapper;

import org.springframework.stereotype.Service;
import ru.hahharr.cardcollection.models.entity.Card;
import ru.hahharr.cardcollection.models.entity.Collection;
import ru.hahharr.cardcollection.models.entity.DropChance;
import ru.hahharr.cardcollection.models.entity.Pack;
import ru.hahharr.cardcollection.models.entity.User;
import ru.hahharr.cardcollection.models.entity.UserCoinState;
import ru.hahharr.cardcollection.models.entity.UserCollection;
import ru.hahharr.cardcollection.models.orm.CardOrm;
import ru.hahharr.cardcollection.models.orm.CollectionOrm;
import ru.hahharr.cardcollection.models.orm.DropChanceOrm;
import ru.hahharr.cardcollection.models.orm.PackOrm;
import ru.hahharr.cardcollection.models.orm.UserCoinStateOrm;
import ru.hahharr.cardcollection.models.orm.UserCollectionOrm;
import ru.hahharr.cardcollection.models.orm.UserOrm;
import ru.hahharr.cardcollection.models.primitives.id.CardId;
import ru.hahharr.cardcollection.models.primitives.id.PackId;

import java.util.List;

@Service
public class EntityFromOrmMapper {

    public static Card mapCard(CardOrm cardOrm) {
        return new Card(cardOrm.getId(),
                cardOrm.getName(),
                cardOrm.getImageUrl(),
                cardOrm.getRarity(),
                cardOrm.getCollectionOrm().getId());
    }

    public static Collection mapCollection(CollectionOrm collectionOrm) {
        List<CardId> cardIdList = collectionOrm.getCardOrms()
                .stream()
                .map(CardOrm::getId)
                .toList();

        List<PackId> packIdList = collectionOrm.getPackOrms()
                .stream()
                .map(PackOrm::getId)
                .toList();

        return new Collection(collectionOrm.getId(),
                collectionOrm.getName(),
                cardIdList,
                packIdList);
    }

    public static DropChance mapDropChance(DropChanceOrm dropChanceOrm) {
        return new DropChance(dropChanceOrm.getId(),
                dropChanceOrm.getCommonDropChance(),
                dropChanceOrm.getRareDropChance(),
                dropChanceOrm.getEpicDropChance());
    }

    public static Pack mapPack(PackOrm packOrm) {
        return new Pack(packOrm.getId(),
                packOrm.getName(),
                packOrm.getCost(),
                packOrm.getCards(),
                packOrm.getCollectionOrm().getId());
    }

    public static User mapUser(UserOrm userOrm) {
        return new User(userOrm.getId(),
                userOrm.getUsername(),
                userOrm.getPassword(),
                userOrm.getRole());
    }

    public static UserCoinState mapUserCoinState(UserCoinStateOrm userCoinStateOrm) {
        return new UserCoinState(userCoinStateOrm.getId(),
                userCoinStateOrm.getTotalCoins(),
                userCoinStateOrm.isAvailableFree(),
                userCoinStateOrm.getLastReceived());
    }

    public static UserCollection mapUserCollection(UserCollectionOrm userCollectionOrm) {
        return new UserCollection(userCollectionOrm.getId(),
                userCollectionOrm.getCards());
    }
}

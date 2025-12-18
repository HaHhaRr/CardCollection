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

import java.util.List;

@Service
public class EntityToOrmMapper {

    public static CardOrm mapCard(Card card, CollectionOrm collectionOrm) {
        return new CardOrm(card.getId(),
                card.getName(),
                card.getImageUrl(),
                card.getRarity(),
                collectionOrm);
    }

    public static CollectionOrm mapCollection(Collection collection, List<CardOrm> cardOrm, List<PackOrm> packOrm) {
        return new CollectionOrm(collection.getId(),
                collection.getName(),
                cardOrm,
                packOrm);
    }

    public static DropChanceOrm mapDropChance(DropChance dropChance, PackOrm packOrm) {
        return new DropChanceOrm(dropChance.getId(),
                (int) (dropChance.getCommonDropChance() * 100),
                (int) (dropChance.getRareDropChance() * 100),
                (int) (dropChance.getEpicDropChance() * 100),
                packOrm);
    }

    public static PackOrm mapPack(Pack pack, CollectionOrm collectionOrm, DropChanceOrm dropChanceOrm) {
        return new PackOrm(pack.getId(),
                pack.getName(),
                pack.getCost(),
                pack.getCardIdList(),
                collectionOrm,
                dropChanceOrm);
    }

    public static UserOrm mapUser(User user, UserCollectionOrm userCollectionOrm, UserCoinStateOrm userCoinStateOrm) {
        return new UserOrm(user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole(),
                userCollectionOrm,
                userCoinStateOrm);
    }

    public static UserCoinStateOrm mapUserCoinState(UserCoinState userCoinState, UserOrm userOrm) {
        return new UserCoinStateOrm(userCoinState.getId(),
                userCoinState.getTotalCoins(),
                userCoinState.isAvailableFree(),
                userCoinState.getLastReceived(),
                userOrm);
    }

    public static UserCollectionOrm mapUserCollection(UserCollection userCollection, UserOrm userOrm) {
        return new UserCollectionOrm(userCollection.getId(),
                userOrm,
                userCollection.getCards());
    }
}

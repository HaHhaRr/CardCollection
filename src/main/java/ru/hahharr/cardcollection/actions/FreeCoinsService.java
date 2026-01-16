package ru.hahharr.cardcollection.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hahharr.cardcollection.models.entity.UserCoinState;
import ru.hahharr.cardcollection.models.orm.UserCoinStateOrm;
import ru.hahharr.cardcollection.models.primitives.id.UserId;
import ru.hahharr.cardcollection.repository.services.UserCoinStateRepoService;
import ru.hahharr.cardcollection.utils.mapper.EntityFromOrmMapper;
import ru.hahharr.cardcollection.utils.provider.LocalDateTimeProvider;

import java.util.Optional;

@Service
public class FreeCoinsService {

    private static final int FREE_COINS_VALUE = 3000;

    @Autowired
    private UserCoinStateRepoService userCoinStateRepoService;

    public ResponseEntity<HttpStatus> addFreeCoins(UserId userId) {
        Optional<UserCoinStateOrm> userCoinStateOrmOptional = userCoinStateRepoService.getUserCoinState(userId);

        if (userCoinStateOrmOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserCoinStateOrm userCoinStateOrm = userCoinStateOrmOptional.get();
        UserCoinState userCoinState = EntityFromOrmMapper.mapUserCoinState(userCoinStateOrm);

        if (userCoinState.isAvailableFree()) {
            userCoinStateOrm.setTotalCoins(userCoinState.getTotalCoins() + FREE_COINS_VALUE);
            userCoinStateOrm.setLastReceived(LocalDateTimeProvider.moscow());

            try {
                userCoinStateRepoService.save(userCoinStateOrm);
            } catch (RuntimeException runtimeException) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

package ru.hahharr.cardcollection.repository.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hahharr.cardcollection.models.entity.UserCoinState;
import ru.hahharr.cardcollection.models.orm.UserCoinStateOrm;
import ru.hahharr.cardcollection.models.primitives.id.UserId;
import ru.hahharr.cardcollection.repository.interfaces.UserCoinStateRepository;
import ru.hahharr.cardcollection.utils.mapper.EntityFromOrmMapper;
import ru.hahharr.cardcollection.utils.provider.LocalDateTimeProvider;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class UserCoinStateRepoService {

    private static final int FREE_COINS_VALUE = 3000;

    @Autowired
    private UserCoinStateRepository userCoinStateRepository;

    public ResponseEntity<UserCoinState> getById(UserId userId) {
        Optional<UserCoinStateOrm> userCoinStateOrmOptional = userCoinStateRepository.findById(userId);
        return userCoinStateOrmOptional
                .map(coinStateOrm ->
                        new ResponseEntity<>(EntityFromOrmMapper.mapUserCoinState(coinStateOrm), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<HttpStatus> addFreeCoins(UserId userId) {
        Optional<UserCoinStateOrm> userCoinStateOrmOptional = userCoinStateRepository.findById(userId);

        if (userCoinStateOrmOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserCoinStateOrm userCoinStateOrm = userCoinStateOrmOptional.get();
        UserCoinState userCoinState = EntityFromOrmMapper.mapUserCoinState(userCoinStateOrm);

        if (userCoinState.isAvailableFree()) {
            userCoinStateOrm.setTotalCoins(userCoinState.getTotalCoins() + FREE_COINS_VALUE);
            userCoinStateOrm.setLastReceived(LocalDateTimeProvider.moscow());
            userCoinStateRepository.save(userCoinStateOrm);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

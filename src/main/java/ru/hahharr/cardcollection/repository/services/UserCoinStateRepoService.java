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

import java.util.Optional;

@Service
public class UserCoinStateRepoService {

    @Autowired
    private UserCoinStateRepository userCoinStateRepository;

    public ResponseEntity<UserCoinState> getUserState(UserId userId) {
        Optional<UserCoinStateOrm> userCoinStateOrmOptional = userCoinStateRepository.findById(userId);
        return userCoinStateOrmOptional
                .map(coinStateOrm ->
                        new ResponseEntity<>(EntityFromOrmMapper.mapUserCoinState(coinStateOrm), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public void save(UserCoinStateOrm userCoinStateOrm) {
        userCoinStateRepository.save(userCoinStateOrm);
    }

    public Optional<UserCoinStateOrm> getUserCoinState(UserId userId) {
        return userCoinStateRepository.findById(userId);
    }
}

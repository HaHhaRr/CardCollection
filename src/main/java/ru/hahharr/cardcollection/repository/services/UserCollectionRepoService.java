package ru.hahharr.cardcollection.repository.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hahharr.cardcollection.models.dto.response.CardListResponseDto;
import ru.hahharr.cardcollection.models.orm.CardOrm;
import ru.hahharr.cardcollection.models.orm.UserCollectionOrm;
import ru.hahharr.cardcollection.models.primitives.id.UserId;
import ru.hahharr.cardcollection.repository.interfaces.CardRepository;
import ru.hahharr.cardcollection.repository.interfaces.UserCollectionRepository;
import ru.hahharr.cardcollection.utils.OffsetLimitPage;
import ru.hahharr.cardcollection.utils.mapper.EntityFromOrmMapper;

import java.util.Optional;

@Service
public class UserCollectionRepoService {

    @Autowired
    private UserCollectionRepository userCollectionRepository;

    @Autowired
    private CardRepository cardRepository;

    public ResponseEntity<CardListResponseDto> getUserCollection(UserId userId, int offset, int limit) {
        Optional<UserCollectionOrm> userCollectionOrm = getUserCollection(userId);
        if (userCollectionOrm.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Page<CardOrm> cardOrmPage = cardRepository.findByIdIn(userCollectionOrm.get().getCards(),
                OffsetLimitPage.of(offset, limit));

        CardListResponseDto cardListResponseDto = new CardListResponseDto(
                cardOrmPage.stream()
                        .map(EntityFromOrmMapper::mapCard)
                        .toList(), cardOrmPage.getTotalPages());

        return new ResponseEntity<>(cardListResponseDto, HttpStatus.OK);
    }

    public void save(UserCollectionOrm userCollectionOrm) {
        userCollectionRepository.save(userCollectionOrm);
    }

    public Optional<UserCollectionOrm> getUserCollection(UserId userId) {
        return userCollectionRepository.findById(userId);
    }
}

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
import ru.hahharr.cardcollection.utils.mapper.EntityFromOrmMapper;
import ru.hahharr.cardcollection.utils.resolvers.PageableResolver;

import java.util.Optional;

@Service
public class UserCollectionRepoService {

    @Autowired
    private UserCollectionRepository userCollectionRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private PageableResolver pageableResolver;

    public ResponseEntity<CardListResponseDto> getUserCollection(UserId userId, int offset, int limit,
                                                                 String sortBy, String direction) {
        Optional<UserCollectionOrm> userCollectionOrm = getUserCollection(userId);
        if (userCollectionOrm.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            Page<CardOrm> cardOrmPage = cardRepository.findByIdIn(userCollectionOrm.get().getCards(),
                    pageableResolver.resolveSortBy(offset, limit, sortBy, direction));

            CardListResponseDto cardListResponseDto = new CardListResponseDto(
                    cardOrmPage.stream()
                            .map(EntityFromOrmMapper::mapCard)
                            .toList(), cardOrmPage.getTotalPages());

            return new ResponseEntity<>(cardListResponseDto, HttpStatus.OK);
        } catch (NullPointerException nullPointerException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public void save(UserCollectionOrm userCollectionOrm) {
        userCollectionRepository.save(userCollectionOrm);
    }

    public Optional<UserCollectionOrm> getUserCollection(UserId userId) {
        return userCollectionRepository.findById(userId);
    }
}

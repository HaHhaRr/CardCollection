package ru.hahharr.cardcollection.repository.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hahharr.cardcollection.models.dto.UserCardCollectionDto;
import ru.hahharr.cardcollection.models.dto.response.UserCardListResponseDto;
import ru.hahharr.cardcollection.models.orm.CardOrm;
import ru.hahharr.cardcollection.models.orm.UserCollectionOrm;
import ru.hahharr.cardcollection.models.primitives.id.CardId;
import ru.hahharr.cardcollection.models.primitives.id.UserId;
import ru.hahharr.cardcollection.repository.interfaces.CardRepository;
import ru.hahharr.cardcollection.repository.interfaces.UserCollectionRepository;
import ru.hahharr.cardcollection.utils.mapper.EntityFromOrmMapper;
import ru.hahharr.cardcollection.utils.resolvers.PageableResolver;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserCollectionRepoService {

    @Autowired
    private UserCollectionRepository userCollectionRepository;

    @Autowired
    private CardRepository cardRepository;

    public ResponseEntity<UserCardListResponseDto> getUserCollection(UserId userId, int offset, int limit,
                                                                     String sortBy, String direction) {
        Optional<UserCollectionOrm> userCollectionOrm = getUserCollection(userId);
        if (userCollectionOrm.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<CardId> cardIdList = userCollectionOrm.get().getCards();
        Map<CardId, Long> cardIdIntegerMap = cardIdList.stream()
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

        Page<CardOrm> cardOrmPage = cardRepository.findByIdIn(cardIdList,
                PageableResolver.resolveSortBy(offset, limit, sortBy, direction));

        boolean hasNext = cardOrmPage.getTotalElements() > offset + limit;

        UserCardListResponseDto cardListResponseDto = new UserCardListResponseDto(
                cardOrmPage.stream()
                        .map(EntityFromOrmMapper::mapCard)
                        .map(card -> new UserCardCollectionDto(card,
                                cardIdIntegerMap.get(card.getId())))
                        .toList(), hasNext);

        return new ResponseEntity<>(cardListResponseDto, HttpStatus.OK);
    }

    public void save(UserCollectionOrm userCollectionOrm) {
        userCollectionRepository.save(userCollectionOrm);
    }

    public Optional<UserCollectionOrm> getUserCollection(UserId userId) {
        return userCollectionRepository.findById(userId);
    }
}

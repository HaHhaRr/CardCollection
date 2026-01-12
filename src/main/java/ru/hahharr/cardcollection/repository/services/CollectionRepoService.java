package ru.hahharr.cardcollection.repository.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hahharr.cardcollection.models.dto.CardListResponseDto;
import ru.hahharr.cardcollection.models.dto.CollectionViewResponseDto;
import ru.hahharr.cardcollection.models.dto.PackViewResponseDto;
import ru.hahharr.cardcollection.models.entity.Collection;
import ru.hahharr.cardcollection.models.orm.CardOrm;
import ru.hahharr.cardcollection.models.orm.CollectionOrm;
import ru.hahharr.cardcollection.models.orm.PackOrm;
import ru.hahharr.cardcollection.models.primitives.id.CollectionId;
import ru.hahharr.cardcollection.repository.interfaces.CardRepository;
import ru.hahharr.cardcollection.repository.interfaces.CollectionRepository;
import ru.hahharr.cardcollection.repository.interfaces.PackRepository;
import ru.hahharr.cardcollection.utils.mapper.EntityFromOrmMapper;
import ru.hahharr.cardcollection.utils.mapper.EntityToViewMapper;

import java.util.Optional;

@Service
public class CollectionRepoService {

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private PackRepository packRepository;

    public ResponseEntity<HttpStatus> saveNewCollection(String collectionName) {
        if (collectionRepository.existsByName(collectionName)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CollectionOrm newCollectionOrm = new CollectionOrm();
        newCollectionOrm.setName(collectionName);
        collectionRepository.save(newCollectionOrm);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<CollectionViewResponseDto> getPageCollectionView(int page, int size) {
        Page<CollectionOrm> collectionOrmPage = collectionRepository.findAll(PageRequest.of(page, size));
        CollectionViewResponseDto collectionViewResponseDto = new CollectionViewResponseDto(
                collectionOrmPage
                        .map(EntityFromOrmMapper::mapCollection)
                        .map(EntityToViewMapper::mapCollection)
                        .stream()
                        .toList(), collectionOrmPage.getTotalPages());
        return new ResponseEntity<>(collectionViewResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<PackViewResponseDto> getPacksFromCollection(CollectionId collectionId, int page, int size) {
        try {
            Collection collection = getCollection(collectionId);
            Page<PackOrm> packOrmPage = packRepository.findByIdIn(collection.getPackIdList(),
                    PageRequest.of(page, size));

            PackViewResponseDto packViewResponseDto = new PackViewResponseDto(
              packOrmPage.stream()
                      .map(EntityFromOrmMapper::mapPack)
                      .map(EntityToViewMapper::mapPack)
                      .toList(), packOrmPage.getTotalPages());

            return new ResponseEntity<>(packViewResponseDto, HttpStatus.OK);
        } catch (NullPointerException nullPointerException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<CardListResponseDto> getCardsFromCollection(CollectionId collectionId, int page, int size) {
        try {
            Collection collection = getCollection(collectionId);
            Page<CardOrm> cardOrmPage = cardRepository.findByIdIn(collection.getCardIdList(),
                    PageRequest.of(page, size));

            CardListResponseDto cardListResponseDto = new CardListResponseDto(
                    cardOrmPage.stream()
                            .map(EntityFromOrmMapper::mapCard)
                            .toList(), cardOrmPage.getTotalPages());

            return new ResponseEntity<>(cardListResponseDto, HttpStatus.OK);
        } catch (NullPointerException nullPointerException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public Collection getCollection(CollectionId collectionId) throws NullPointerException {
        Optional<CollectionOrm> collectionOrm = collectionRepository.findById(collectionId);
        return collectionOrm.map(EntityFromOrmMapper::mapCollection)
                .orElseThrow(NullPointerException::new);
    }
}

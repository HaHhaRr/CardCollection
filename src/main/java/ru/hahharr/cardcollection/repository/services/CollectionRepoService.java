package ru.hahharr.cardcollection.repository.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hahharr.cardcollection.models.orm.CollectionOrm;
import ru.hahharr.cardcollection.repository.interfaces.CollectionRepository;

@Service
public class CollectionRepoService {

    @Autowired
    private CollectionRepository collectionRepository;

    public ResponseEntity<HttpStatus> saveNewCollection(String collectionName) {
        if (collectionRepository.existsByName(collectionName)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CollectionOrm newCollectionOrm = new CollectionOrm();
        newCollectionOrm.setName(collectionName);
        collectionRepository.save(newCollectionOrm);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

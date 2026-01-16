package ru.hahharr.cardcollection.utils.resolvers;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.hahharr.cardcollection.utils.OffsetLimitPage;

import java.util.Objects;

@Component
public class PageableResolver {

    public Pageable resolveSortBy(int offset, int limit, String sortBy, String direction) throws NullPointerException {
        if (Objects.equals(sortBy, "rarity") && direction == null) {
            return OffsetLimitPage.of(offset, limit, Sort.by(sortBy));
        } else if (Objects.equals(sortBy, "rarity") && direction.equals("descending")) {
            return OffsetLimitPage.of(offset, limit, Sort.by(sortBy).descending());
        } else if (Objects.equals(sortBy, "collectionId") && direction == null) {
            return OffsetLimitPage.of(offset, limit, Sort.by("collectionOrm.id"));
        } else if (Objects.equals(sortBy, "collectionId") && direction.equals("descending")) {
            return OffsetLimitPage.of(offset, limit, Sort.by("collectionOrm.id").descending());
        } else {
            throw new NullPointerException();
        }
    }
}

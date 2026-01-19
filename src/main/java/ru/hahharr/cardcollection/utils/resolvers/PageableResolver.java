package ru.hahharr.cardcollection.utils.resolvers;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.hahharr.cardcollection.utils.OffsetLimitPage;

import java.util.Optional;

public class PageableResolver {

    private static final String ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE = "Wrong sortBy value";

    public static Pageable resolveSortBy(int offset, int limit, String sortBy, String direction) {
        if (sortBy == null) {
            return OffsetLimitPage.of(offset, limit);
        }
        Optional<Sort> sort = switch (sortBy) {
            case "rarity" -> Optional.of(Sort.by(sortBy));
            case "collection" -> Optional.of(Sort.by("collectionOrm.id"));
            default -> throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        };

        if ("descending".equals(direction)) {
            sort = sort.map(Sort::descending);
        }

        return OffsetLimitPage.of(offset, limit, sort.get());
    }
}

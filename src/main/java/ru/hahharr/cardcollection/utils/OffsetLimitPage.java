package ru.hahharr.cardcollection.utils;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serial;
import java.io.Serializable;

public class OffsetLimitPage implements Pageable, Serializable {
    @Serial
    private static final long serialVersionUID = -7776775672753856449L;

    private static final String WRONG_OFFSET_VALUE_MESSAGE = "Offset index must not be less than zero";
    private static final String WRONG_LIMIT_VALUE_MESSAGE = "Limit must not be less than one";

    private final int limit;
    private final int offset;
    private final Sort sort;

    private OffsetLimitPage(int offset, int limit, Sort sort) {
        if (offset < 0) {
            throw new IllegalArgumentException(WRONG_OFFSET_VALUE_MESSAGE);
        }

        if (limit < 1) {
            throw new IllegalArgumentException(WRONG_LIMIT_VALUE_MESSAGE);
        }
        this.limit = limit;
        this.offset = offset;
        this.sort = sort;
    }

    public static OffsetLimitPage of(int offset, int limit, Sort sort) {
        return new OffsetLimitPage(offset, limit, sort);
    }

    public static OffsetLimitPage of(int offset, int limit) {
        return OffsetLimitPage.of(offset, limit, Sort.unsorted());
    }

    @Override
    public int getPageNumber() {
        return offset / limit;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new OffsetLimitPage((int) getOffset() + getPageSize(), getPageSize(), getSort());
    }

    private OffsetLimitPage previous() {
        return hasPrevious() ?
                new OffsetLimitPage((int) getOffset() - getPageSize(), getPageSize(), getSort()) :
                this;
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public Pageable first() {
        return new OffsetLimitPage(0, getPageSize(), getSort());
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return new OffsetLimitPage(pageNumber * getPageSize(), getPageSize(), getSort());
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }
}

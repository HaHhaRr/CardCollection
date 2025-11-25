package ru.hahharr.cardcollection.models.primitives;

import ru.hahharr.cardcollection.models.entity.Card;
import ru.hahharr.cardcollection.models.entity.Collection;
import ru.hahharr.cardcollection.models.entity.Pack;
import ru.hahharr.cardcollection.models.entity.User;
import ru.hahharr.cardcollection.models.primitives.id.CardId;
import ru.hahharr.cardcollection.models.primitives.id.CollectionId;
import ru.hahharr.cardcollection.models.primitives.id.PackId;
import ru.hahharr.cardcollection.models.primitives.id.UserId;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.Serial;

public class IdGenerator extends SequenceStyleGenerator {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return switch (object) {
            case User user -> {
                Long id = jdbcTemplate.queryForObject("select count (*) from users", Long.class);
                yield new UserId(id + 1);
            }
            case Card card -> {
                Long id = jdbcTemplate.queryForObject("select count (*) from card", Long.class);
                yield new CardId(id + 1);
            }
            case Collection collection -> {
                Long id = jdbcTemplate.queryForObject("select count (*) from collection", Long.class);
                yield new CollectionId(id + 1);
            }
            case Pack pack -> {
                Long id = jdbcTemplate.queryForObject("select count (*) from pack", Long.class);
                yield new PackId(id + 1);
            }
            default -> new IllegalStateException("Unknown object type " + object.getClass());
        };

    }
}

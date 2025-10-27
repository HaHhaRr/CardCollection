package ru.hahharr.cardcollection.models.primitives;

import ru.hahharr.cardcollection.models.entity.Card;
import ru.hahharr.cardcollection.models.entity.Collection;
import ru.hahharr.cardcollection.models.entity.Pack;
import ru.hahharr.cardcollection.models.entity.User;
import ru.hahharr.cardcollection.models.entity.UserCoinState;
import ru.hahharr.cardcollection.models.entity.UserCollection;
import ru.hahharr.cardcollection.models.primitives.id.CardId;
import ru.hahharr.cardcollection.models.primitives.id.CollectionId;
import ru.hahharr.cardcollection.models.primitives.id.PackId;
import ru.hahharr.cardcollection.models.primitives.id.UserCoinStateId;
import ru.hahharr.cardcollection.models.primitives.id.UserCollectionId;
import ru.hahharr.cardcollection.models.primitives.id.UserId;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class IdGenerator extends SequenceStyleGenerator {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return switch (object) {
            case User user -> {
                Long id = jdbcTemplate.queryForObject("select count (*) from testuser", Long.class);
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
            case UserCoinState userCoinState -> {
                Long id = jdbcTemplate.queryForObject("select count (*) from user_coin_state", Long.class);
                yield new UserCoinStateId(id + 1);
            }
            case Pack pack -> {
                Long id = jdbcTemplate.queryForObject("select count (*) from pack", Long.class);
                yield new PackId(id + 1);
            }
            case UserCollection userCollection -> {
                Long id = jdbcTemplate.queryForObject("select count (*) from user_collection", Long.class);
                yield new UserCollectionId(id + 1);
            }
            default -> new IllegalStateException("Unknown object type " + object.getClass());
        };

    }
}

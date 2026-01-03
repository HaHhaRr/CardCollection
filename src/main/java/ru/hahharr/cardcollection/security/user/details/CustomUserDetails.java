package ru.hahharr.cardcollection.security.user.details;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.hahharr.cardcollection.models.entity.User;
import ru.hahharr.cardcollection.models.orm.UserOrm;
import ru.hahharr.cardcollection.utils.mapper.EntityFromOrm;

import java.io.Serial;
import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    @Serial
    private static final long serialVersionUID = 2233981166496183197L;

    private User user;

    public CustomUserDetails(UserOrm userOrm) {
        this.user = EntityFromOrm.mapUser(userOrm);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().toString()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
}

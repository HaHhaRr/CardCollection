package ru.hahharr.cardcollection.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.hahharr.cardcollection.models.primitives.id.UserId;
import ru.hahharr.cardcollection.security.Role;

@Data
@AllArgsConstructor
public class User {

    private UserId id;

    private String username;

    private String password;

    private Role role;
}

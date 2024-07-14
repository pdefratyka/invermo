package com.invermo.business.mapper;

import com.invermo.business.domain.User;
import com.invermo.persistence.entity.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMapper {

    public static User mapUserEntityToUser(final UserEntity userEntity) {
        return new User(userEntity.id(), userEntity.username(), userEntity.userEmail());
    }
}

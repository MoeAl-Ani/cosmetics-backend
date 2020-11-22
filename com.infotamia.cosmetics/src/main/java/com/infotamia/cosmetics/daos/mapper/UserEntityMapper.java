package com.infotamia.cosmetics.daos.mapper;

import com.infotamia.exception.BaseErrorCode;
import com.infotamia.exception.ItemNotFoundException;
import com.infotamia.pojos.entities.UserEntity;
import javax.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * General user entity mapper.
 *
 * @author Mohammed Al-Ani
 * @throws ItemNotFoundException
 */
@ApplicationScoped
public class UserEntityMapper {

    public List<UserEntity> applyUserMapper(List<Object[]> resultSet) throws ItemNotFoundException {
        Map<Integer, UserEntity> userEntityMap = new HashMap<>();
        for (Object[] o : resultSet) {
            UserEntity userEntity = null;

            for (Object result : o) {
                if (result instanceof UserEntity) {
                    userEntity = (UserEntity) result;
                }
            }
            if (userEntity == null) {
                throw new ItemNotFoundException("user not found", BaseErrorCode.USER_NOT_FOUND);
            }
            Integer userId = userEntity.getId();
            userEntity = userEntityMap.get(userId) == null ? userEntity : userEntityMap.get(userId);
            userEntityMap.put(userEntity.getId(), userEntity);
        }
        return new ArrayList<>(userEntityMap.values());
    }
}

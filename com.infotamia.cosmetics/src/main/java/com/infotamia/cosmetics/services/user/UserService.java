package com.infotamia.cosmetics.services.user;

import com.infotamia.cosmetics.daos.UserDao;
import com.infotamia.exception.BaseErrorCode;
import com.infotamia.exception.DataCorruptedException;
import com.infotamia.exception.ItemNotFoundException;
import com.infotamia.pojos.entities.UserEntity;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import java.util.Optional;

/**
 * User service for general CRUD for {@link UserEntity}
 *
 * @author Mohammed Al-Ani
 */
@RequestScoped
public class UserService {
    @Inject
    private UserDao userDao;

    /**
     * Fetch single user entity by email.
     *
     * @param email
     * @return Optional<UserEntity>
     */
    public Optional<UserEntity> findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    /**
     * Fetch single user entity by user id.
     *
     * @param id
     * @return UserEntity
     * @throws ItemNotFoundException
     */
    public UserEntity findUserById(Integer id) throws ItemNotFoundException {
        return userDao.findUserById(id)
                .orElseThrow(() -> new ItemNotFoundException("driver not found", BaseErrorCode.USER_NOT_FOUND));
    }

    /**
     * Create single user entity.
     *
     * @param user
     * @return UserEntity
     * @throws DataCorruptedException
     */
    public UserEntity insertUser(@NotNull(message = "user was null") UserEntity user)
            throws DataCorruptedException {
        UserEntity userEntity = userDao.findUserByEmail(user.getEmail()).orElse(null);
        if (userEntity == null) {
            userEntity = userDao.insertUser(user);
        }
        return userEntity;
    }

    /**
     * Update single user entity.
     *
     * @param entity
     * @return UserEntity
     */
    public UserEntity update(UserEntity entity) {
        return userDao.update(entity);
    }

    /**
     * Delete single user entity by user id.
     *
     * @param id
     */
    public void delete(Integer id) {
        userDao.delete(id);
    }
}

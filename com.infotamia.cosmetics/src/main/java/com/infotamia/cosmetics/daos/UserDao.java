package com.infotamia.cosmetics.daos;

import com.infotamia.cosmetics.daos.mapper.UserEntityMapper;
import com.infotamia.pojos.entities.UserEntity;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

/**
 * @author Mohammed Al-Ani
 */
@RequestScoped
public class UserDao {

    @Inject
    private Session session;
    @Inject
    private UserEntityMapper userEntityMapper;

    /**
     * Create single user entity.
     *
     * @param userEntity
     * @return created UserEntity
     */
    public UserEntity insertUser(UserEntity userEntity) {
        session.persist(userEntity);
        return userEntity;
    }

    /**
     * Update single user entity.
     *
     * @param entity
     * @return updated UserEntity
     */
    public UserEntity update(UserEntity entity) {
        return (UserEntity) session.merge(entity);
    }

    public void delete(Integer id) {
        findUserById(id).ifPresent(session::delete);
    }

    /**
     * Fetch user entity by id.
     *
     * @param id
     * @return Optional<UserEntity>
     */
    public Optional<UserEntity> findUserById(Integer id) {
        Query query = session.createSQLQuery("select {u.*} " +
                "from user u " +
                "where u.id = :userId ")
                .addEntity("u", UserEntity.class)
                .setParameter("userId", id);
        List<UserEntity> resultList = query.getResultList();
        return resultList.stream().findFirst();
    }

    /**
     * Fetch user entity by email
     *
     * @param email
     * @return Optional<UserEntity>
     */
    public Optional<UserEntity> findUserByEmail(String email) {
        Query query = session.createSQLQuery("select {u.*} " +
                "from user u " +
                "where u.email like :email ")
                .addEntity("u", UserEntity.class)
                .setParameter("email", email);
        List<UserEntity> resultList = query.getResultList();
        return resultList.stream().findFirst();
    }
}
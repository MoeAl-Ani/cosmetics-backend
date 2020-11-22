package com.infotamia.cosmetics.daos;

import com.infotamia.pojos.entities.AddressEntity;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.hibernate.Session;

import java.time.LocalDateTime;

/**
 * @author Mohammed Al-Ani
 **/
@RequestScoped
public class AddressDao {
    public AddressDao() {
        //
    }

    @Inject
    private Session session;

    public AddressEntity insert(AddressEntity addressEntity) {
        addressEntity.setId(null);
        addressEntity.setCreatedAt(LocalDateTime.now());
        session.persist(addressEntity);
        return addressEntity;
    }
}

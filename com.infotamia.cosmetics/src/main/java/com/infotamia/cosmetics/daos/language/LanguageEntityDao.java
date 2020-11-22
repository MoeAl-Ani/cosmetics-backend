package com.infotamia.cosmetics.daos.language;

import com.infotamia.pojos.entities.LanguageEntity;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Optional;

/**
 * @author Mohammed Al-Ani
 */
@Dependent
public class LanguageEntityDao {

    @Inject
    private Session session;

    /**
     * @param languageId
     * @return Optional of queried language.
     */
    public Optional<LanguageEntity> findOneByLanguageId(Integer languageId) {
        Query query = session.createSQLQuery("select {l.*} from language l where l.id = :languageId")
                .addEntity("l", LanguageEntity.class)
                .setParameter("languageId", languageId);
        try {
            return Optional.of((LanguageEntity) query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}

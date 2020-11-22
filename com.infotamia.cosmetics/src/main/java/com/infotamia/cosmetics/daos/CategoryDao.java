package com.infotamia.cosmetics.daos;

import com.infotamia.pojos.entities.CategoryEntity;
import com.infotamia.pojos.entities.CategoryTranslationEntity;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.hibernate.Session;

import java.util.*;

/**
 * @author Mohammed Al-Ani
 **/
@SuppressWarnings("unchecked")
@RequestScoped
public class CategoryDao {
    public CategoryDao() {
        //
    }

    @Inject
    private Session session;

    public Optional<List<CategoryEntity>> fetchCategoriesByIds(Collection<Integer> ids) {
        List<Object[]> resultList = session.createSQLQuery("select {c.*}, {ct.*} from category c " +
                "left join category_translation ct on c.id = ct.category_id " +
                "where c.id in (:ids)")
                .setParameter("ids", ids)
                .addEntity("c", CategoryEntity.class)
                .addEntity("ct", CategoryTranslationEntity.class)
                .getResultList();

        if (resultList.isEmpty()) return Optional.empty();
        Set<CategoryEntity> finalResult = new HashSet<>();
        for (Object[] objects : resultList) {
            CategoryEntity categoryEntity = null;
            CategoryTranslationEntity categoryTranslationEntity = null;

            for (Object o : objects) {
                if (o instanceof CategoryEntity)
                    categoryEntity = (CategoryEntity) o;
                if (o instanceof CategoryTranslationEntity)
                    categoryTranslationEntity = (CategoryTranslationEntity) o;
            }

            if (categoryEntity != null) {
                finalResult.add(categoryEntity);
                if (categoryTranslationEntity != null)
                    categoryEntity.getCategoryTranslations().add(categoryTranslationEntity);
            }
        }
        return Optional.of(new ArrayList<>(finalResult));
    }
}

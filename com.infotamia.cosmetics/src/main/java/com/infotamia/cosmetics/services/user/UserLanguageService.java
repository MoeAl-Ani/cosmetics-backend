package com.infotamia.cosmetics.services.user;

import com.infotamia.access.AbstractPrincipal;
import com.infotamia.cosmetics.daos.UserDao;
import com.infotamia.cosmetics.daos.language.LanguageEntityDao;
import com.infotamia.cosmetics.services.user.validation.LanguageEntityValidator;
import com.infotamia.exception.BaseErrorCode;
import com.infotamia.exception.IncorrectParameterException;
import com.infotamia.exception.RestCoreException;
import com.infotamia.logger.LoggerFactory;
import com.infotamia.pojos.entities.LanguageEntity;
import com.infotamia.pojos.entities.UserEntity;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

/**
 * User language service for updating user lang preference.
 *
 * @author Mohammed Al-Ani
 */
@RequestScoped
public class UserLanguageService {

    private final Logger logger = LoggerFactory.getLogger(UserLanguageService.class);
    @Inject
    private AbstractPrincipal user;
    @Inject
    private LanguageEntityDao languageEntityDao;
    @Inject
    private UserDao userDao;


    @Inject
    private LanguageEntityValidator validator;

    /**
     * update user language.
     *
     * @param language
     * @throws IncorrectParameterException
     */
    public void updateUserLanguage(LanguageEntity language) throws IncorrectParameterException {
        validator.validate(language);

        LanguageEntity languageEntity = languageEntityDao.findOneByLanguageId(language.getId())
                .orElseThrow(() -> new RestCoreException(
                        400, BaseErrorCode.LANGUAGE_NOT_SUPPORTED, "Given language is not supported"));

        updateLanguage(languageEntity);
    }


    /**
     * @param languageEntity
     */
    private void updateLanguage(LanguageEntity languageEntity) {
        UserEntity userEntity = userDao.findUserById(this.user.getId())
                .orElseThrow(() -> new RestCoreException(400, BaseErrorCode.USER_NOT_FOUND, "User not found"));
        logger.log(Level.TRACE, "Updating user {} language to {}", userEntity.getId(), languageEntity.getLanguageCode());
        userEntity.setLanguageId(languageEntity.getId());
        userDao.update(userEntity);
    }
}

package com.infotamia.cosmetics.services.user.validation;

import com.infotamia.exception.BaseErrorCode;
import com.infotamia.exception.IncorrectParameterException;
import com.infotamia.pojos.entities.LanguageEntity;
import javax.enterprise.context.RequestScoped;

/**
 * @author Mohammed Al-Ani
 */
@RequestScoped
public class LanguageEntityValidatorImpl implements LanguageEntityValidator {
    @Override
    public void validate(LanguageEntity language) throws IncorrectParameterException {
        if (language == null) {
            throw new IncorrectParameterException("language entity was null", BaseErrorCode.LANGUAGE_ENTITY_WAS_NULL);
        }
        if (language.getId() == null) {
            throw new IncorrectParameterException("language identifier was null", BaseErrorCode.LANGUAGE_IDENTIFIER_WAS_NULL);
        }
    }
}

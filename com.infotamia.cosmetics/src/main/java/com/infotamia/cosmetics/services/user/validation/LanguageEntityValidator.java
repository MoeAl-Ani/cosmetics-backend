package com.infotamia.cosmetics.services.user.validation;

import com.infotamia.exception.IncorrectParameterException;
import com.infotamia.pojos.entities.LanguageEntity;

/**
 * @author Mohammed Al-Ani
 */
public interface LanguageEntityValidator {
    void validate(LanguageEntity language) throws IncorrectParameterException;
}

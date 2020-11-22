package com.infotamia.cosmetics.services.language;

import com.infotamia.cosmetics.daos.language.LanguageEntityDao;
import com.infotamia.exception.BaseErrorCode;
import com.infotamia.exception.ItemNotFoundException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

/**
 * @author Mohammed Al-Ani
 */
@RequestScoped
public class LanguageValidator {

    @Inject
    LanguageEntityDao languageEntityDao;

    /**
     * validate a given language id existence.
     *
     * @param languageId
     * @throws ItemNotFoundException
     */
    public void validateLanguage(@NotNull(message = "language id was null") Integer languageId) throws ItemNotFoundException {

        languageEntityDao.findOneByLanguageId(languageId)
                .orElseThrow(() ->
                        new ItemNotFoundException("given language is not implemented", BaseErrorCode.LANGUAGE_NOT_SUPPORTED));
    }
}

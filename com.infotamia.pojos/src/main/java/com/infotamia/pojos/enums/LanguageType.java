package com.infotamia.pojos.enums;

/**
 * @author Mohammed Al-Ani
 */
public enum LanguageType {
    ENGLISH(1), ARABIC(2), KURDISH(3), FINNISH(4);

    private Integer shortName;

    LanguageType(Integer shortName) {
        this.shortName = shortName;
    }

    public static LanguageType getValue(Integer lang) {
        LanguageType languageType;
        switch (lang) {
            case 1:
                languageType = ENGLISH;
                break;
            case 2:
                languageType = ARABIC;
                break;
            case 3:
                languageType = KURDISH;
                break;
            case 4:
                languageType = FINNISH;
                break;
            default:
                throw new RuntimeException("no enum constant found");
        }
        return languageType;
    }

    public static String getLangStringValue(Integer lang) {
        switch (lang) {
            case 1:
                return "en";
            case 2:
                return "ar";
            case 3:
                return "ku";
            case 4:
                return "fi";
            default:
                throw new RuntimeException("no enum constant found");
        }
    }

    public Integer getValue() {
        return shortName;
    }

    public String getLangStringValue() {
        return getLangStringValue(shortName);
    }

}

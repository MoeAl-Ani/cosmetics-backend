package com.infotamia.cosmetics.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

/**
 * @author Mohammed Al-Ani
 **/
public class PhoneNumberUtils {

    private PhoneNumberUtils() {

    }

    public static String format(String number, String regionCode) {
        PhoneNumberUtil instance = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber phoneNumber = instance.parse(number, regionCode);
            return instance.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
                    .replaceAll("\\s", "");
        } catch (NumberParseException e) {
            throw new RuntimeException("phone number cant be parsed");
        }
    }

    public static boolean isValid(String number, String regionCode) {
        PhoneNumberUtil instance = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber phoneNumber = instance.parse(number, regionCode);
            return instance.isValidNumberForRegion(phoneNumber, regionCode);
        } catch (NumberParseException e) {
            return false;
        }
    }
}

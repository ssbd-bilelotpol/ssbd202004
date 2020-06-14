import * as i18nISOCountries from 'i18n-iso-countries';

export const getCountryOptions = (t) => {
    return Object.keys(i18nISOCountries.getAlpha2Codes()).map((code) => ({
        key: code,
        value: code.toUpperCase(),
        text: t(code),
    }));
};

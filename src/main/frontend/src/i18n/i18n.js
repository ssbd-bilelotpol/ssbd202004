import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import LanguageDetector from 'i18next-browser-languagedetector';
import plDateLocale from 'date-fns/locale/pl';
import { registerLocale } from 'react-datepicker';
import pl from './pl';

const resources = {
    ...pl,
};

i18n.use(initReactI18next) // passes i18n down to react-i18next
    .use(LanguageDetector)
    .init({
        resources,
        keySeparator: false, // we do not use keys in form messages.welcome
        interpolation: {
            escapeValue: false, // react already safes from xss
        },
    });

registerLocale('pl-PL', plDateLocale);

export default i18n;

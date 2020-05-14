import { errors, roles } from '../constants';
import translations from './pl/resource.json';

export default {
    pl: {
        translation: {
            [roles.admin]: 'Administrator',
            [roles.manager]: 'Menedżer',
            [roles.customerService]: 'Obsługa klienta',
            [roles.client]: 'Klient',
            [errors.api.connection]: 'Błąd połączenia z serwerem',
            'error.rest.actionFailed': 'Akcja zakończona niepowodzeniem',
            'error.auth.incorrectLoginOrPassword': 'Nieprawidłowy login lub hasło',
            'error.auth.accountBlocked':
                'Twoje konto zostało zablokowane. Skontaktuj się z administratorem',
            'error.rest.validation': 'Podano dane w niewłaściwym formacie.',
            'error.database.optimisticLock': 'Dane zostały zmodyfikowane.',
            'error.database.operation':
                'Wystąpił błąd podczas wykonywania operacji w bazie danych.',
            'error.account.loginExists': 'Podany login już istnieje.',
            'error.account.emailExists': 'Podany adres e-mail jest już wykorzystany.',
            'error.account.register.invalidToken': 'Link został już wykorzystany.',
            'error.account.register.alreadyConfirmed': 'Konto zostało już potwierdzone.',
            'error.account.passwordsDontMatch': 'Niepoprawne hasło',
            'error.account.passwordIsTheSame': 'Hasło jest takie samo jak poprzednie',
            'error.account.notActive': 'Konto jest nieaktywne',
            'error.token.mailFailure': 'Nie udało wysłać się e-maila z żetonem.',
            'error.token.expired': 'Token wygasł',
            'error.mail.failure': 'Błąd serwera email.',
            'error.account.notFound': 'Nie znaleziono żadnego pasującego konta',
            'rest.processingError': 'Błąd poczas przetwarzania żądania',
            ...translations,
        },
    },
};

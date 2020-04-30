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
            'error.auth.incorrectLoginOrPassword': 'Nieprawidłowy login lub hasło',
            'error.rest.validation': 'Podano dane w niewłaściwym formacie.',
            'error.database.optimisticLock': 'Dane zostały zmodyfikowane.',
            'error.database.operation':
                'Wystąpił błąd podczas wykonywania operacji w bazie danych.',
            'error.account.loginExists': 'Podany login już istnieje.',
            'error.account.emailExists': 'Podany adres e-mail jest już wykorzystany.',
            'error.account.register.invalidToken': 'Link został już wykorzystany.',
            'error.account.register.alreadyConfirmed': 'Konto zostało już potwierdzone.',
            'error.account.register.mailFailure': 'Nie udało wysłać się e-maila potwierdzającego.',
            'error.account.notActive': 'Konto jest nieaktywne',
            'error.token.expired': 'Token wygasł',
            'No such person': 'Nie ma takiej osoby',
            'There are no results matching criteria.': 'Żadne konto nie spełnia podanych wymagań.',
            Search: 'Wyszukaj użytkownika',
            ...translations,
        },
    },
};

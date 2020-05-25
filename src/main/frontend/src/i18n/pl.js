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
            'error.request.etagWrongValue':
                'Modyfikowane dane były nieaktualne. Odśwież stronę i spróbuj jeszcze raz',
            'error.auth.accountBlocked':
                'Twoje konto zostało zablokowane. Skontaktuj się z administratorem',
            'error.rest.validation': 'Podano dane w niewłaściwym formacie.',
            'error.database.optimisticLock':
                'Modyfikowane dane były nieaktualne. Odśwież stronę i spróbuj jeszcze raz.',
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
            'error.rest.processingError': 'Wystąpił błąd, spróbuj ponownie.',
            'error.rest.unauthorized': 'Brak uprawnień do wykonania operacji',
            'error.rest.resourceNotFound': 'Nie znaleziono zasobu',
            'error.airplaneSchema.notFound':
                'Schemat samolotu nie został znaleziony, mógł zostać usunięty.',
            'error.airplaneSchema.inUse':
                'Schemat samolotu jest używany przez co najmniej jeden lot',
            'error.seatClass.notFound':
                'Klasa siedzeń nie została znaleziona, mogła zostać usunięta',
            'error.seatClass.inUse': 'Klasa siedzeń jest używana przez co najmniej jedno siedzenie',
            'error.seatClass.nameTaken': 'Klasa siedzeń o takiej nazwie już istnieje',
            'error.seatClass.benefitExists': 'Dodatek o takiej nazwie już istnieje',
            'error.airport.codeNotUnique': 'Kod lotniska nie jest unikatowy',
            'error.airport.notFound': 'Nie znaleziono lotniska',
            'error.airport.inUse': 'Lotnisko jest używane przez co najmniej jedno połączenie',
            'error.connection.exists': 'Takie połączenie już istnieje',
            'error.connection.targetNotFound': 'Lotnisko docelowe nie istnieje',
            'error.connection.sourceNotFound': 'Lotnisko startowe nie istnieje',
            'error.connection.inUse': 'W ramach tego połączenia są zaplanowane loty',
            'error.flight.exists': 'Taki lot już istnieje',
            'error.captcha.invalid':
                'Wygląda na to, że jesteś robotem. Spróbuj ponownie za kilka minut.',
            'error.captcha.format.invalid':
                'Wygląda na to, że jesteś robotem. Spróbuj ponownie za kilka minut.',
            'error.captcha.cantConnect':
                'Wystąpił niespodziewany błąd na serwerze, spróbuj ponownie za kilka minut.',
            'error.ticket.notFound': 'Nie znaleziono biletu',
            'error.ticket.flightNotFound': 'Nie znaleziono lotu',
            'error.ticket.seatTaken': 'Wybrane miejsca są już zajęte',
            'error.ticket.returnTimeExpired': 'Minął czas na zwrot biletu',
            ...translations,
        },
    },
};

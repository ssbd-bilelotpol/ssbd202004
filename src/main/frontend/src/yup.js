import * as Yup from 'yup';

Yup.setLocale({
    mixed: {
        default: 'field_invalid',
        required: 'field_required',
    },
    string: {
        email: 'incorrect_email',
        min: ({ min }) => ({ key: 'field_too_short', value: { min } }),
        max: ({ max }) => ({ key: 'field_too_long', value: { max } }),
        matches: 'incorrect_regex',
    },
    array: {
        min: ({ min }) => ({ key: 'access_levels_too_short', value: { min } }),
    },
    number: {
        min: ({ min }) => ({ key: 'value_too_low', value: { min } }),
    },
});

const loginRegex = /^[a-zA-Z0-9]+([-._][a-zA-Z0-9])*$/iu;
const nameRegex = /^\p{L}+([ -]\p{L}*'?\p{L}+)*$/iu;
const lastNameRegex = /^\p{L}*'?\p{L}+([ -]\p{L}*'?\p{L}+)*$/iu;
const phoneRegex = /^\+?[0-9]+$/;

export const RegisterSchema = Yup.object().shape({
    login: Yup.string().required().min(3).max(30).matches(loginRegex, 'incorrect_login'),
    password: Yup.string().required().min(8).max(64),
    passwordConfirmation: Yup.string()
        .oneOf([Yup.ref('password'), null], 'Passwords must match')
        .required()
        .min(8)
        .max(64),
    email: Yup.string().required().email().min(3).max(255),
    firstName: Yup.string().required().min(1).max(30).matches(nameRegex, 'incorrect_name'),
    lastName: Yup.string().required().min(1).max(30).matches(lastNameRegex, 'incorrect_lastName'),
    phoneNumber: Yup.string().required().min(9).max(15).matches(phoneRegex, 'incorrect_phone'),
});

export const LoginSchema = Yup.object().shape({
    login: Yup.string().min(3).required().matches(loginRegex, 'incorrect_login'),
    password: Yup.string().required(),
});

export const SettingsSchema = Yup.object().shape({
    firstName: Yup.string().required().min(1).max(30).matches(nameRegex, 'incorrect_name'),
    lastName: Yup.string().required().min(1).max(30).matches(lastNameRegex, 'incorrect_lastName'),
    phoneNumber: Yup.string().required().min(9).max(15).matches(phoneRegex, 'incorrect_phone'),
});

export const AccountChangePasswordSchema = (showOldPassword) =>
    Yup.object().shape({
        oldPassword: showOldPassword
            ? Yup.string().required().min(8).max(64)
            : Yup.mixed().notRequired(),
        newPassword: Yup.string().required().min(8).max(64),
        passwordConfirmation: Yup.string()
            .oneOf([Yup.ref('newPassword'), null], 'Passwords must match')
            .required()
            .min(8)
            .max(64),
    });

export const PasswordResetRequestSchema = Yup.object().shape({
    email: Yup.string().email().required(),
});

export const PasswordResetSchema = Yup.object().shape({
    password: Yup.string().required().min(8).max(64),
    passwordConfirmation: Yup.string().oneOf([Yup.ref('password'), null], 'Passwords must match'),
});

export const AccountAccessLevelEditSchema = Yup.object().shape({
    accessLevels: Yup.array().min(1),
});

export const FlightSchema = Yup.object().shape(
    {
        code: Yup.string().min(5).max(30).required(),
        price: Yup.number().required().min(0.01),
        connection: Yup.number().required(),
        airplaneSchema: Yup.number().required(),
        departureTime: Yup.date()
            .required()
            .test('is-future', 'departure_min_date', (value) => value > new Date())
            .when(
                'arrivalTime',
                (arrivalTime, schema) =>
                    arrivalTime && schema.max(arrivalTime, 'departure_after_arrival')
            ),
        arrivalTime: Yup.date()
            .required()
            .when(
                'departureTime',
                (departureTime, schema) =>
                    departureTime && schema.min(departureTime, 'arrival_before_departure')
            ),
    },
    ['departureTime', 'arrivalTime']
);

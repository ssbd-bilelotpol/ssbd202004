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
        length: ({ length }) => ({ key: 'field_must_be', value: { length } }),
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
const planeRegex = /^[\p{L}0-9-](\s?[\p{L}0-9-])*$/iu;
const seatClassRegex = /^\p{L}+([ -]\p{L}*'?\p{L}+)*$/iu;
const benefitRegex = /^\p{L}+([ -]\p{L}*'?\p{L}+)*$/iu;
const cityRegex = /^\p{L}+([ -]\p{L}*'?\p{L}+)*$/iu;
const airportCodeRegex = /[a-zA-Z]{3}/;
const airportNameRegex = /^\p{L}+([ -]\p{L}*'?\p{L}+)*$/iu;

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

const flightCodeRegex = /^[A-Za-z0-9]{5}$/;

export const FlightSchema = Yup.object().shape(
    {
        flightCode: Yup.string()
            .length(5)
            .matches(flightCodeRegex, 'incorrect_flight_code')
            .required(),
        price: Yup.number()
            .required()
            .min(0.01)
            .test(
                'is-two-frac-digits',
                'price_format',
                (value) =>
                    value &&
                    (Number.isInteger(value) || value.toFixed(2).length >= value.toString().length)
            ),
        connectionId: Yup.number().required(),
        airplaneSchemaId: Yup.number().required(),
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

export const PlaneSchema = Yup.object().shape({
    name: Yup.string().min(5).max(60).required().matches(planeRegex, 'incorrect_plane_name'),
    plane: Yup.object().shape({
        columns: Yup.number().required().min(2).max(9),
        rows: Yup.number().required().min(3).max(40),
        emptyColumns: Yup.array().optional(),
        emptyRows: Yup.array().optional(),
    }),
});

export const AirportSchema = Yup.object().shape({
    name: Yup.string()
        .min(2)
        .max(32)
        .matches(airportNameRegex, 'incorrect_airport_name')
        .required(),
    code: Yup.string().length(3).matches(airportCodeRegex, 'incorrect_airport_code').required(),
    city: Yup.string().min(2).max(32).matches(cityRegex, 'incorrect_city').required(),
    country: Yup.string().length(2).required(),
});

export const AirportSearchBarSchema = Yup.object().shape({
    name: Yup.string().matches(airportNameRegex, 'incorrect_airport_name'),
    code: Yup.string().length(3).matches(airportCodeRegex, 'incorrect_airport_code'),
    city: Yup.string().matches(cityRegex, 'incorrect_city'),
    country: Yup.string().length(2),
});

export const SeatClassSchema = Yup.object().shape({
    name: Yup.string()
        .min(2)
        .max(30)
        .required()
        .matches(seatClassRegex, 'incorrect_seatClass_name'),
    color: Yup.string().required(),
    price: Yup.number().required().min(0.01),
    benefits: Yup.array().of(
        Yup.object().shape({
            name: Yup.string()
                .min(1)
                .max(128)
                .required()
                .matches(benefitRegex, 'incorrect_benefit_name'),
            description: Yup.string().min(5).max(255).required(),
        })
    ),
});

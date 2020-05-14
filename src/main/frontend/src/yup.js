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
        matches: 'phone_number_only_numbers',
    },
    array: {
        min: ({ min }) => ({ key: 'access_levels_too_short', value: { min } }),
    },
});

export const RegisterSchema = Yup.object().shape({
    login: Yup.string().required().min(3).max(30),
    password: Yup.string().required().min(8).max(64),
    passwordConfirmation: Yup.string()
        .oneOf([Yup.ref('password'), null], 'must_match')
        .required()
        .min(8)
        .max(64),
    email: Yup.string().required().email().min(3).max(255),
    firstName: Yup.string().required().min(1).max(30),
    lastName: Yup.string().required().min(1).max(30),
    phoneNumber: Yup.string()
        .required()
        .min(9)
        .max(15)
        .matches(/\+?[0-9]+/),
});

export const LoginSchema = Yup.object().shape({
    login: Yup.string().required(),
    password: Yup.string().required(),
});

export const SettingsSchema = Yup.object().shape({
    firstName: Yup.string().required().min(1).max(30),
    lastName: Yup.string().required().min(1).max(30),
    phoneNumber: Yup.string()
        .required()
        .min(9)
        .max(15)
        .matches(/\+?[0-9]+/),
});

export const AccountChangePasswordSchema = (showOldPassword) =>
    Yup.object().shape({
        oldPassword: showOldPassword
            ? Yup.string().required().min(8).max(64)
            : Yup.mixed().notRequired(),
        newPassword: Yup.string().required().min(8).max(64),
        passwordConfirmation: Yup.string()
            .oneOf([Yup.ref('newPassword'), null], 'must_match')
            .required()
            .min(8)
            .max(64),
    });

export const PasswordResetRequestSchema = Yup.object().shape({
    email: Yup.string().email().required(),
});

export const PasswordResetSchema = Yup.object().shape({
    password: Yup.string().required().min(8).max(64),
    passwordConfirmation: Yup.string().oneOf([Yup.ref('password'), null], 'must_match'),
});

export const AccountAcessLevelEditSchema = Yup.object().shape({
    accessLevels: Yup.array().min(1),
});

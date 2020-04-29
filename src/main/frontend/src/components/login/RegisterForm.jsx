import React, { useState } from 'react';
import { Button, Form, Message } from 'semantic-ui-react';
import { Formik } from 'formik';
import * as Yup from 'yup';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import { Trans, useTranslation } from 'react-i18next';
import AsteriskInput from '../controls/AsteriskInput';
import { registerApi } from '../../api/auth';

const RegisterSchema = Yup.object().shape({
    login: Yup.string().required().min(3).max(30),
    password: Yup.string().required().min(1).max(64),
    email: Yup.string().required().email().min(3).max(255),
    firstName: Yup.string().required().min(1).max(30),
    lastName: Yup.string().required().min(1).max(30),
    phoneNumber: Yup.string()
        .required()
        .min(9)
        .max(15)
        .matches(/\+?[0-9]+/),
});

const LocalForm = ({ handleSubmit, error }) => {
    const { t } = useTranslation();
    return (
        <Formik
            initialValues={{
                login: '',
                password: '',
                email: '',
                firstName: '',
                lastName: '',
                phoneNumber: '',
            }}
            onSubmit={handleSubmit}
            validationSchema={RegisterSchema}
        >
            {({
                values,
                errors,
                touched,
                handleChange,
                handleBlur,
                handleSubmit,
                isSubmitting,
            }) => (
                <Form error={!!error} size="large" onSubmit={handleSubmit}>
                    <Form.Input
                        name="login"
                        fluid
                        iconPosition="left"
                        icon="user"
                        placeholder={t('Login')}
                        control={AsteriskInput}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        value={values.login}
                        error={
                            touched.login &&
                            errors.login && {
                                content: errors.login,
                                pointing: 'below',
                            }
                        }
                    />
                    <Form.Input
                        name="password"
                        type="password"
                        icon="lock"
                        fluid
                        iconPosition="left"
                        placeholder={t('Password')}
                        control={AsteriskInput}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        value={values.password}
                        error={
                            touched.password &&
                            errors.password && {
                                content: errors.password,
                                pointing: 'below',
                            }
                        }
                    />
                    <Form.Input
                        name="email"
                        type="email"
                        icon="mail"
                        fluid
                        iconPosition="left"
                        placeholder={t('E-mail')}
                        control={AsteriskInput}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        value={values.email}
                        error={
                            touched.email &&
                            errors.email && {
                                content: errors.email,
                                pointing: 'below',
                            }
                        }
                    />
                    <Form.Input
                        name="firstName"
                        fluid
                        placeholder={t('First name')}
                        control={AsteriskInput}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        value={values.firstName}
                        error={
                            touched.firstName &&
                            errors.firstName && {
                                content: errors.firstName,
                                pointing: 'below',
                            }
                        }
                    />
                    <Form.Input
                        name="lastName"
                        fluid
                        placeholder={t('Last name')}
                        control={AsteriskInput}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        value={values.lastName}
                        error={
                            touched.lastName &&
                            errors.lastName && {
                                content: errors.lastName,
                                pointing: 'below',
                            }
                        }
                    />
                    <Form.Input
                        name="phoneNumber"
                        fluid
                        placeholder={t('Phone number')}
                        control={AsteriskInput}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        value={values.phoneNumber}
                        error={
                            touched.phoneNumber &&
                            errors.phoneNumber && {
                                content: errors.phoneNumber,
                                pointing: 'below',
                            }
                        }
                    />
                    <Message
                        error
                        header={t('Signing up error')}
                        content={error && t(error.message)}
                    />
                    <Button
                        disabled={isSubmitting}
                        type="submit"
                        color="yellow"
                        fluid
                        size="large"
                        loading={isSubmitting}
                    >
                        {t('Sign up')}
                    </Button>
                </Form>
            )}
        </Formik>
    );
};

const RegisterForm = () => {
    const [error, setError] = useState(null);
    const [registered, setRegistered] = useState(false);
    const makeCancellable = useCancellablePromise();

    const handleSubmit = async (values) => {
        try {
            await makeCancellable(registerApi(values));
            setRegistered(true);
        } catch (err) {
            setError(err);
        }
    };

    const { t } = useTranslation();

    if (registered) {
        return (
            <Message positive>
                <Message.Header>{t('Signed up successfully')}</Message.Header>
                <p>
                    <Trans i18nKey="sentConfirmationLink">
                        Confirmation link has been sent to the given
                        <b> e-mail address</b>
                    </Trans>
                </p>
            </Message>
        );
    }

    return <LocalForm handleSubmit={handleSubmit} error={error} />;
};

export default RegisterForm;

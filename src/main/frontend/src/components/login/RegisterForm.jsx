import React, { useState } from 'react';
import { Button, Form, Message } from 'semantic-ui-react';
import { Formik } from 'formik';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import { Trans, useTranslation } from 'react-i18next';
import { useGoogleReCaptcha } from 'react-google-recaptcha-v3';
import AsteriskInput from '../controls/AsteriskInput';
import { registerApi } from '../../api/auth';
import { RegisterSchema } from '../../yup';

const LocalForm = ({ handleSubmit, error }) => {
    const { t } = useTranslation();

    const translate = (msg) => {
        if (msg.key) {
            return t(msg.key, msg.value);
        }

        return t(msg);
    };

    return (
        <Formik
            initialValues={{
                login: '',
                password: '',
                passwordConfirmation: '',
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
                                content: translate(errors.login),
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
                                content: translate(errors.password),
                                pointing: 'below',
                            }
                        }
                    />
                    <Form.Input
                        name="passwordConfirmation"
                        type="password"
                        icon="lock"
                        fluid
                        iconPosition="left"
                        placeholder={t('Confirm password')}
                        control={AsteriskInput}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        value={values.passwordConfirmation}
                        error={
                            touched.passwordConfirmation &&
                            errors.passwordConfirmation && {
                                content: translate(errors.passwordConfirmation),
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
                                content: translate(errors.email),
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
                                content: translate(errors.firstName),
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
                                content: translate(errors.lastName),
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
                                content: translate(errors.phoneNumber),
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

    const { executeRecaptcha } = useGoogleReCaptcha();

    const handleSubmit = async (values) => {
        try {
            const token = await executeRecaptcha('register');
            await makeCancellable(
                registerApi({
                    ...values,
                    captcha: token,
                })
            );
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

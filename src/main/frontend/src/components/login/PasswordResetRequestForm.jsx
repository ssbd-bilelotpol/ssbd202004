import { Formik } from 'formik';
import { Button, Form, Message } from 'semantic-ui-react';
import React, { useState } from 'react';
import { Trans, useTranslation } from 'react-i18next';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import AsteriskInput from '../controls/AsteriskInput';
import { requestPasswordResetApi } from '../../api/auth';
import { PasswordResetRequestSchema } from '../../yup';

const PasswordResetRequestForm = () => {
    const { t } = useTranslation();
    const makeCancellable = useCancellablePromise();
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(false);

    const translate = (msg) => {
        if (msg.key) {
            return t(msg.key, msg.value);
        }

        return t(msg);
    };

    const handleSubmit = async (values) => {
        try {
            await makeCancellable(requestPasswordResetApi(values.email));
            setSuccess(true);
        } catch (err) {
            setError(err);
        }
    };

    return (
        <Formik
            initialValues={{
                email: '',
            }}
            onSubmit={handleSubmit}
            validationSchema={PasswordResetRequestSchema}
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
                        name="email"
                        fluid
                        icon="mail"
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
                    <Message
                        error
                        header={t('Password reset error')}
                        content={error && t(error.message)}
                    />
                    {success && (
                        <Message>
                            <Message.Header>
                                <Trans
                                    i18nKey="sentPasswordResetTitle"
                                    email={values.email}
                                    defaultValue=""
                                >
                                    Password reset e-mail sent to {{ email: values.email }}
                                </Trans>
                            </Message.Header>
                            <Message.Content>
                                <Trans i18nKey="sentPasswordResetContent">
                                    If this e-mail is assigned to any account, a reset token has
                                    been sent to it.
                                </Trans>
                            </Message.Content>
                        </Message>
                    )}
                    <Button
                        disabled={isSubmitting}
                        type="submit"
                        color="yellow"
                        fluid
                        size="large"
                        loading={isSubmitting}
                    >
                        {t('Send password reset link')}
                    </Button>
                </Form>
            )}
        </Formik>
    );
};

export default PasswordResetRequestForm;

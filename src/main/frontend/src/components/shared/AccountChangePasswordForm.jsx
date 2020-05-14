import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Message, FormInput, Form, Button, Placeholder } from 'semantic-ui-react';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import { Formik } from 'formik';
import AsteriskInput from '../controls/AsteriskInput';
import { AccountChangePasswordSchema } from '../../yup';

const AccountChangePasswordForm = ({ onSave, onSuccess, loading, showOldPasswordInput }) => {
    const { t } = useTranslation();

    const SettingsSchema = AccountChangePasswordSchema(showOldPasswordInput);

    const makeCancellable = useCancellablePromise();

    const [savingError, setSavingError] = useState(false);
    const [saved, setSaved] = useState(false);

    const translate = (msg) => {
        if (msg.key) {
            return t(msg.key, msg.value);
        }

        return t(msg);
    };

    const handleSave = async (values) => {
        setSaved(false);
        setSavingError(false);

        try {
            await makeCancellable(
                onSave({
                    newPassword: values.newPassword,
                    oldPassword: showOldPasswordInput ? values.oldPassword : undefined,
                })
            );
            setSaved(true);
            if (onSuccess) onSuccess();
        } catch (e) {
            setSavingError(e);
        }
    };

    return (
        <>
            {saved && <Message success content={t('Password successfully changed')} />}
            {savingError && <Message negative content={t(savingError.message)} />}
            {!loading && (
                <Formik
                    initialValues={{
                        oldPassword: showOldPasswordInput ? '' : undefined,
                        newPassword: '',
                        passwordConfirmation: '',
                    }}
                    validationSchema={SettingsSchema}
                    onSubmit={handleSave}
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
                        <Form onSubmit={handleSubmit}>
                            {showOldPasswordInput && (
                                <FormInput
                                    name="oldPassword"
                                    type="password"
                                    label={t('Old password')}
                                    value={values.oldPassword}
                                    control={AsteriskInput}
                                    onChange={handleChange}
                                    onBlur={handleBlur}
                                    disabled={isSubmitting}
                                    error={
                                        errors.oldPassword &&
                                        touched.oldPassword && {
                                            content: translate(errors.oldPassword),
                                            pointing: 'below',
                                        }
                                    }
                                />
                            )}
                            <FormInput
                                name="newPassword"
                                label={t('New Password')}
                                type="password"
                                value={values.newPassword}
                                control={AsteriskInput}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                disabled={isSubmitting}
                                error={
                                    errors.newPassword &&
                                    touched.newPassword && {
                                        content: translate(errors.newPassword),
                                        pointing: 'below',
                                    }
                                }
                            />
                            <FormInput
                                name="passwordConfirmation"
                                label={t('Confirm new password')}
                                type="password"
                                value={values.passwordConfirmation}
                                control={AsteriskInput}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                disabled={isSubmitting}
                                error={
                                    errors.passwordConfirmation &&
                                    touched.passwordConfirmation && {
                                        content: translate(errors.passwordConfirmation),
                                        pointing: 'below',
                                    }
                                }
                            />

                            <Button type="submit" disabled={isSubmitting} loading={isSubmitting}>
                                {t('Save')}
                            </Button>
                        </Form>
                    )}
                </Formik>
            )}
            {loading && (
                <Placeholder>
                    <Placeholder.Line />
                    <Placeholder.Line />
                    <Placeholder.Line />
                    <Placeholder.Line />
                    <Placeholder.Line />
                    <Placeholder.Line />
                    <Placeholder.Line />
                </Placeholder>
            )}
        </>
    );
};

export default AccountChangePasswordForm;

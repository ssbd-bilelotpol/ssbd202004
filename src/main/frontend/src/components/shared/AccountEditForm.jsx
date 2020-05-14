import { useTranslation } from 'react-i18next';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import React, { useState } from 'react';
import { Button, Form, FormInput, Message } from 'semantic-ui-react';
import { Formik } from 'formik';
import AsteriskInput from '../controls/AsteriskInput';
import { SettingsSchema } from '../../yup';

const AccountEditForm = ({ onSave, onSuccess, onFail, loading, data }) => {
    const { t } = useTranslation();
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
            await makeCancellable(onSave(values));
            setSaved(true);
            if (onSuccess) onSuccess();
        } catch (e) {
            setSavingError(e);
            if (onFail) onFail(e);
        }
    };

    return (
        <>
            {saved && <Message success content={t('Changes successfully saved')} />}
            {savingError && <Message negative content={t(savingError.message)} />}
            {!loading && (
                <Formik
                    initialValues={{
                        firstName: data.firstName,
                        lastName: data.lastName,
                        phoneNumber: data.phoneNumber,
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
                            <FormInput
                                name="firstName"
                                label={t('First name')}
                                value={values.firstName}
                                control={AsteriskInput}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                disabled={isSubmitting}
                                error={
                                    errors.firstName &&
                                    touched.firstName && {
                                        content: translate(errors.firstName),
                                        pointing: 'below',
                                    }
                                }
                            />
                            <FormInput
                                name="lastName"
                                label={t('Last name')}
                                value={values.lastName}
                                control={AsteriskInput}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                disabled={isSubmitting}
                                error={
                                    errors.lastName &&
                                    touched.lastName && {
                                        content: translate(errors.lastName),
                                        pointing: 'below',
                                    }
                                }
                            />
                            <FormInput
                                name="phoneNumber"
                                label={t('Phone number')}
                                value={values.phoneNumber}
                                control={AsteriskInput}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                disabled={isSubmitting}
                                error={
                                    errors.phoneNumber &&
                                    touched.phoneNumber && {
                                        content: translate(errors.phoneNumber),
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
        </>
    );
};

export default AccountEditForm;

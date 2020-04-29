import * as Yup from 'yup';
import { useTranslation } from 'react-i18next';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import React, { useState } from 'react';
import { Button, Form, FormInput, Message } from 'semantic-ui-react';
import { Formik } from 'formik';

const SettingsSchema = Yup.object().shape({
    firstName: Yup.string().required().min(1).max(30),
    lastName: Yup.string().required().min(1).max(30),
    phoneNumber: Yup.string()
        .required()
        .min(9)
        .max(15)
        .matches(/\+?[0-9]+/),
});

const AccountEditForm = ({ onSave, onSuccess, onFail, loading, data }) => {
    const { t } = useTranslation();
    const makeCancellable = useCancellablePromise();

    const [savingError, setSavingError] = useState(false);
    const [saved, setSaved] = useState(false);

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
                                onChange={handleChange}
                                onBlur={handleBlur}
                                disabled={isSubmitting}
                                error={
                                    errors.firstName &&
                                    touched.firstName && {
                                        content: errors.firstName,
                                        pointing: 'below',
                                    }
                                }
                            />
                            <FormInput
                                name="lastName"
                                label={t('Last name')}
                                value={values.lastName}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                disabled={isSubmitting}
                                error={
                                    errors.lastName &&
                                    touched.lastName && {
                                        content: errors.lastName,
                                        pointing: 'below',
                                    }
                                }
                            />
                            <FormInput
                                name="phoneNumber"
                                label={t('Phone number')}
                                value={values.phoneNumber}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                disabled={isSubmitting}
                                error={
                                    errors.phoneNumber &&
                                    touched.phoneNumber && {
                                        content: errors.phoneNumber,
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

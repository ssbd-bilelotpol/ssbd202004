import React, { useState, useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import { Formik } from 'formik';
import { Message, Form, Label } from 'semantic-ui-react';
import { useHistory } from 'react-router-dom';
import * as i18nISOCountries from 'i18n-iso-countries';
import { addAirport } from '../../../api/airports';
import { AirportSchema } from '../../../yup';
import AsteriskInput from '../../controls/AsteriskInput';
import { ContentCard } from '../../shared/Dashboard';
import ConfirmSubmit from '../../controls/ConfirmSubmit';
import { route } from '../../../routing';
import RequireableDropdown from '../../shared/RequireableDropdown';

const getCountryOptions = (t) => {
    return Object.keys(i18nISOCountries.getAlpha2Codes()).map((code) => ({
        key: code,
        value: code.toUpperCase(),
        text: t(code),
    }));
};

const AddAirportForm = () => {
    const { t } = useTranslation();
    const [error, setError] = useState(false);
    const history = useHistory();
    const makeCancellable = useCancellablePromise();
    const countryOptions = useMemo(() => getCountryOptions(t), [t]);

    const handleSubmit = async (values) => {
        try {
            await makeCancellable(addAirport(values));
            history.push(route('manager.airports.list'));
        } catch (err) {
            setError(err);
        }
    };

    return (
        <>
            <Formik
                initialValues={{
                    name: '',
                    code: '',
                    city: '',
                    country: '',
                }}
                onSubmit={handleSubmit}
                validationSchema={AirportSchema}
            >
                {({
                    values,
                    errors,
                    touched,
                    handleChange,
                    handleBlur,
                    handleSubmit,
                    isSubmitting,
                    setFieldValue,
                }) => (
                    <Form error={!!error} onSubmit={handleSubmit}>
                        <Message error content={error && error.message} />
                        <Form.Group>
                            <Form.Input
                                width={12}
                                name="name"
                                fluid
                                placeholder={t('Airport name')}
                                control={AsteriskInput}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                value={values.name}
                                error={
                                    touched.name &&
                                    errors.name && {
                                        content: t(errors.name),
                                        pointing: 'below',
                                    }
                                }
                            />
                            <Form.Input
                                width={4}
                                name="code"
                                fluid
                                placeholder={t('Airport code')}
                                control={AsteriskInput}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                value={values.code}
                                error={
                                    touched.code &&
                                    errors.code && {
                                        content: t(errors.code),
                                        pointing: 'below',
                                    }
                                }
                            />
                        </Form.Group>
                        <Form.Group>
                            <Form.Input
                                width={14}
                                name="city"
                                fluid
                                placeholder={t('City')}
                                control={AsteriskInput}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                value={values.city}
                                error={
                                    touched.city &&
                                    errors.city && {
                                        content: t(errors.city),
                                        pointing: 'below',
                                    }
                                }
                            />
                            <RequireableDropdown
                                options={countryOptions}
                                name="country"
                                placeholder={t('Country')}
                                value={values.country}
                                onChange={(_, value) => setFieldValue('country', value.value)}
                                required
                                error={
                                    touched.country &&
                                    errors.country && {
                                        content: t(errors.country),
                                        pointing: 'below',
                                    }
                                }
                            />
                        </Form.Group>
                        <ConfirmSubmit
                            onSubmit={handleSubmit}
                            disabled={isSubmitting}
                            loading={isSubmitting}
                        >
                            {t('Add')}
                        </ConfirmSubmit>
                    </Form>
                )}
            </Formik>
        </>
    );
};

const AddAirport = () => {
    const { t } = useTranslation();

    return (
        <ContentCard fluid>
            <Label attached="top">{t('Add airport')}</Label>
            <AddAirportForm />
        </ContentCard>
    );
};

export default AddAirport;

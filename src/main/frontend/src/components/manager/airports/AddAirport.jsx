import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import { Formik } from 'formik';
import { Message, Form, Label } from 'semantic-ui-react';
import { useHistory } from 'react-router-dom';
import { addAirport } from '../../../api/airports';
import { AirportSchema } from '../../../yup';
import AsteriskInput from '../../controls/AsteriskInput';
import { ContentCard } from '../../shared/Dashboard';
import CountriesDropdown from './CountriesDropdown';
import ConfirmSubmit from '../../controls/ConfirmSubmit';
import { route } from '../../../routing';

const AddAirportForm = () => {
    const { t } = useTranslation();
    const [error, setError] = useState(false);
    const history = useHistory();
    const makeCancellable = useCancellablePromise();

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
                            <CountriesDropdown
                                name="country"
                                placeholder={t('Country')}
                                value={values.city}
                                onChange={(value) => setFieldValue('country', value.value)}
                                setError={setError}
                                required
                                error={
                                    touched.city &&
                                    errors.city && {
                                        content: t(errors.city),
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

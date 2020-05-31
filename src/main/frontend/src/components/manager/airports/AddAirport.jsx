import React, { useMemo, useState } from 'react';
import { Form, Label, Message } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import { Formik } from 'formik';
import * as i18nISOCountries from 'i18n-iso-countries';
import styled from 'styled-components';
import { useHistory } from 'react-router';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import { ContentCard } from '../../shared/Dashboard';
import { AirportSchema } from '../../../yup';
import AsteriskInput from '../../controls/AsteriskInput';
import ConfirmSubmit from '../../controls/ConfirmSubmit';
import { createAirport } from '../../../api/airport';
import { route } from '../../../routing';

const AlignedFormGroup = styled(Form.Group)`
    &&& {
        align-items: flex-end;
    }
`;
const AddAirport = () => {
    const { t } = useTranslation();

    return (
        <ContentCard fluid>
            <Label attached="top">{t('Add airport')}</Label>
            <AirportAddForm />
        </ContentCard>
    );
};

const AirportAddForm = () => {
    const { t } = useTranslation();
    const history = useHistory();
    const [error, setError] = useState(false);
    const makeCancellable = useCancellablePromise();

    const handleSubmit = async (values) => {
        try {
            const airport = await makeCancellable(createAirport(values));
            history.push(route('manager.airports.airport.edit', { code: airport.code }));
        } catch (err) {
            setError(err);
        }
    };

    const getCountryOptions = () => {
        return Object.keys(i18nISOCountries.getAlpha2Codes()).map((code) => ({
            key: code,
            value: code.toLowerCase(),
            text: t(code),
        }));
    };
    const countryOptions = useMemo(getCountryOptions, []);

    const translate = (msg) => {
        if (msg.key) {
            return t(msg.key, msg.value);
        }

        return t(msg);
    };

    return (
        <>
            <Formik
                initialValues={{
                    code: '',
                    name: '',
                    country: '',
                    city: '',
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
                        <Message error content={error && t(error.message)} />
                        <AlignedFormGroup>
                            <Form.Input
                                width={6}
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
                                        content: translate(errors.code),
                                        pointing: 'below',
                                    }
                                }
                            />
                            <Form.Input
                                width={10}
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
                                        content: translate(errors.name),
                                        pointing: 'below',
                                    }
                                }
                            />
                        </AlignedFormGroup>
                        <RequiredDropdown
                            name="country"
                            placeholder={t('Country')}
                            options={countryOptions}
                            onChange={(_, { value }) => setFieldValue('country', value)}
                            value={values.country}
                            error={
                                touched.country &&
                                errors.country && {
                                    content: translate(errors.country),
                                    pointing: 'below',
                                }
                            }
                        />
                        <Form.Input
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
                                    content: translate(errors.city),
                                    pointing: 'below',
                                }
                            }
                        />
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

const DropdownLabel = styled(Label)`
    &&& {
        position: absolute;
        right: 1px;
        top: auto;
        transform: translateY(1px);
        height: inherit;
        font-size: 0.64285714em;
        z-index: 100;
    }
`;

const LabeledDropdown = styled(Form.Dropdown)`
    &&& {
        .icon {
            right: 3em !important;
        }
    }
`;

const DropdownControl = (props) => <Form.Field {...props} />;

const RequiredDropdown = ({ error, ...props }) => {
    return (
        <Form.Field error={error} control={DropdownControl}>
            <DropdownLabel icon="asterisk" corner="right" />
            <LabeledDropdown search selection {...props} />
        </Form.Field>
    );
};
export default AddAirport;

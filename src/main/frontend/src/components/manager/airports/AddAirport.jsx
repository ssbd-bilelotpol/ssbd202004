import React, { useMemo, useState } from 'react';
import { Form, Label, Message } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import { Formik } from 'formik';
import * as i18nISOCountries from 'i18n-iso-countries';
import styled from 'styled-components';
import { ContentCard } from '../../shared/Dashboard';
import { AirportSchema } from '../../../yup';
import AsteriskInput from '../../controls/AsteriskInput';
import ConfirmSubmit from '../../controls/ConfirmSubmit';

const AddAirport = () => {
    const { t } = useTranslation();

    return (
        <ContentCard fluid>
            <Label attached="top">{t('Add airport')}</Label>
            <AirportAddForm />
        </ContentCard>
    );
};

const getCountryOptions = (t) => {
    return Object.keys(i18nISOCountries.getAlpha2Codes()).map((code) => ({
        key: code,
        value: code.toLowerCase(),
        text: t(code),
    }));
};

const AirportAddForm = () => {
    const { t } = useTranslation();
    const handleSubmit = () => console.log('not implemented');
    const [error] = useState(false);
    const translate = (msg) => {
        if (msg.key) {
            return t(msg.key, msg.value);
        }

        return t(msg);
    };
    const countryOptions = useMemo(() => getCountryOptions(t), []);
    return (
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
                setFieldTouched,
            }) => (
                <Form error={!!error} onSubmit={handleSubmit}>
                    <Form.Input
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
                    <RequiredDropdown
                        name="country"
                        placeholder={t('Country')}
                        options={countryOptions}
                        onChange={(_, { value }) => setFieldValue('country', value)}
                        onBlur={(_, { value }) => setFieldTouched('country', value)}
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
                    <Message
                        error
                        header={t('Sign in error')}
                        content={error && t(error.message)}
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
            right: 4em !important;
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

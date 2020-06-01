import React, { useState } from 'react';
import { Form, Label, Message } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import { Formik } from 'formik';
import styled from 'styled-components';
import { useHistory } from 'react-router';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import { ContentCard } from '../../shared/Dashboard';
import { FlightSchema } from '../../../yup';
import AsteriskInput from '../../controls/AsteriskInput';
import ConfirmSubmit from '../../controls/ConfirmSubmit';
import { createFlight } from '../../../api/flight';
import { route } from '../../../routing';
import { errorColor, errorLighterColor } from '../../../constants';
import ConnectionDropdown from './ConnectionDropdown';
import SchemaDropdown from './SchemaDropdown';

const AlignedFormGroup = styled(Form.Group)`
    &&& {
        align-items: flex-end;
    }
`;

const LeftSideLabel = styled(Label)`
    &&& {
        border-bottom-right-radius: 0 !important;
        border-top-right-radius: 0 !important;
        border-right-width: 0 !important;
    }

    &&&.warning {
        color: ${errorColor};
        border-color: ${errorLighterColor};
    }
`;

const SquishedInput = styled(Form.Input)`
    input {
        border-bottom-left-radius: 0 !important;
        border-top-left-radius: 0 !important;
    }
`;

const AddFlight = () => {
    const { t } = useTranslation();

    return (
        <ContentCard fluid>
            <Label attached="top">{t('Add flight')}</Label>
            <FlightAddForm />
        </ContentCard>
    );
};

const DateTimeInput = ({ min, children, ...props }) => {
    return (
        <Form.Input
            type="datetime-local"
            min={min && min.toISOString().split(':', 2).join(':')}
            {...props}
        >
            {children}
        </Form.Input>
    );
};

const FlightAddForm = () => {
    const { t } = useTranslation();
    const history = useHistory();
    const [error, setError] = useState(false);
    const makeCancellable = useCancellablePromise();

    const handleSubmit = async (values) => {
        try {
            const flight = await makeCancellable(createFlight(values));
            history.push(route('manager.flights.flight.edit', { code: flight.code }));
        } catch (err) {
            setError(err);
        }
    };

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
                    price: '',
                    connection: '',
                    airplaneSchema: '',
                    departureTime: '',
                    arrivalTime: '',
                }}
                onSubmit={handleSubmit}
                validationSchema={FlightSchema}
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
                                width={4}
                                name="code"
                                fluid
                                placeholder={t('Flight code')}
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
                            <SquishedInput
                                width={4}
                                name="price"
                                placeholder={t('Price')}
                                labelPosition="right corner"
                                onChange={handleChange}
                                onBlur={handleBlur}
                                value={values.price}
                                error={
                                    touched.price &&
                                    errors.price && {
                                        content: translate(errors.price),
                                        pointing: 'below',
                                    }
                                }
                            >
                                <LeftSideLabel
                                    className={touched.price && errors.price && 'warning'}
                                    basic
                                >
                                    PLN
                                </LeftSideLabel>
                                <input />
                                <Label icon="asterisk" corner="right" />
                            </SquishedInput>
                            <ConnectionDropdown
                                width={8}
                                name="connection"
                                placeholder={t('Connection')}
                                value={values.connection}
                                onChange={(value, price) => {
                                    setFieldValue('connection', value);
                                    setFieldValue('price', price);
                                }}
                                setError={setError}
                                required
                                error={
                                    touched.connection &&
                                    errors.connection && {
                                        content: translate(errors.connection),
                                        pointing: 'below',
                                    }
                                }
                            />
                        </AlignedFormGroup>
                        <SchemaDropdown
                            name="airplaneSchema"
                            placeholder={t('Airplane')}
                            value={values.airplaneSchema}
                            onChange={(value) => setFieldValue('airplaneSchema', value)}
                            setError={setError}
                            required
                            error={
                                touched.airplaneSchema &&
                                errors.airplaneSchema && {
                                    content: translate(errors.airplaneSchema),
                                    pointing: 'below',
                                }
                            }
                        />
                        <AlignedFormGroup>
                            <DateTimeInput
                                width={8}
                                name="departureTime"
                                labelPosition="right corner"
                                control={SquishedInput}
                                value={values.departureTime}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                min={new Date()}
                                error={
                                    touched.departureTime &&
                                    errors.departureTime && {
                                        content: translate(errors.departureTime),
                                        pointing: 'below',
                                    }
                                }
                            >
                                <LeftSideLabel
                                    className={
                                        touched.departureTime && errors.departureTime && 'warning'
                                    }
                                    basic
                                >
                                    {t('Departure time')}
                                </LeftSideLabel>
                                <input />
                                <Label icon="asterisk" corner="right" />
                            </DateTimeInput>
                            <DateTimeInput
                                width={8}
                                name="arrivalTime"
                                labelPosition="right corner"
                                control={SquishedInput}
                                value={values.arrivalTime}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                min={new Date()}
                                error={
                                    touched.arrivalTime &&
                                    errors.arrivalTime && {
                                        content: translate(errors.arrivalTime),
                                        pointing: 'below',
                                    }
                                }
                            >
                                <LeftSideLabel
                                    className={
                                        touched.arrivalTime && errors.arrivalTime && 'warning'
                                    }
                                    basic
                                >
                                    {t('Arrival time')}
                                </LeftSideLabel>
                                <input />
                                <Label icon="asterisk" corner="right" />
                            </DateTimeInput>
                        </AlignedFormGroup>
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

export default AddFlight;

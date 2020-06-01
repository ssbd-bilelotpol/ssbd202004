import React, { useCallback, useEffect, useState } from 'react';
import { Form, Label, Message } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import { Formik } from 'formik';
import styled from 'styled-components';
import { useHistory } from 'react-router';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import debounce from 'lodash.debounce';
import { ContentCard } from '../../shared/Dashboard';
import { FlightSchema } from '../../../yup';
import AsteriskInput from '../../controls/AsteriskInput';
import ConfirmSubmit from '../../controls/ConfirmSubmit';
import { createFlight } from '../../../api/flight';
import { route } from '../../../routing';
import RequiredDropdown from '../../shared/RequiredDropdown';
import { errorColor, errorLighterColor } from '../../../constants';
import { fetchConnectionsByCodes } from '../../../api/connections';

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

let searchCounter = 0;
const FlightAddForm = () => {
    const { t } = useTranslation();
    const history = useHistory();
    const [error, setError] = useState(false);
    const [isFetching, setFetching] = useState(false);
    const makeCancellable = useCancellablePromise();
    const [connections, setConnections] = useState([]);

    const fetchConnections = useCallback(
        async (searchCodes) => {
            searchCounter += 1;
            const before = searchCounter;
            try {
                setFetching(true);
                let connectionsDto = await fetchConnectionsByCodes(searchCodes);
                connectionsDto = connectionsDto.map((connection) => ({
                    key: connection.code,
                    value: connection.code,
                    text: `${connection.source.code} - ${connection.destination.code}`,
                    description: `${t(connection.source.country.toUpperCase())} - ${t(
                        connection.destination.country.toUpperCase()
                    )}`,
                    'data-price': connection.price,
                }));
                setConnections(connectionsDto);
            } catch (err) {
                setError(err);
            } finally {
                if (searchCounter === before) {
                    setFetching(false);
                }
            }
        },
        [t]
    );

    useEffect(() => {
        fetchConnections('');
    }, [fetchConnections]);

    const getConnectionPrice = (code) =>
        connections.find((connection) => connection.value === code)['data-price'];
    const updateSearchQuery = useCallback(debounce(fetchConnections, 250), []);

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
                            <RequiredDropdown
                                width={8}
                                name="connection"
                                placeholder={t('Connection')}
                                options={connections}
                                onChange={(_, { value }) => {
                                    setFieldValue('connection', value);
                                    setFieldValue('price', getConnectionPrice(value));
                                }}
                                onSearchChange={(_, { searchQuery }) =>
                                    updateSearchQuery(searchQuery)
                                }
                                value={values.connection}
                                loading={isFetching}
                                disabled={isFetching}
                                error={
                                    touched.connection &&
                                    errors.connection && {
                                        content: translate(errors.connection),
                                        pointing: 'below',
                                    }
                                }
                            />
                        </AlignedFormGroup>
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

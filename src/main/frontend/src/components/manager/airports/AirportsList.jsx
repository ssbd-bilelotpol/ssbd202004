import React, { useState, useEffect, useCallback } from 'react';
import { useTranslation } from 'react-i18next';
import { Table, Button, Message, Placeholder, Form, Label } from 'semantic-ui-react';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import styled from 'styled-components';
import debounce from 'lodash.debounce';
import { Formik } from 'formik';
import { listAirports } from '../../../api/airports';
import { ContentCard } from '../../shared/Dashboard';
import CountriesDropdown from './CountriesDropdown';
import { AirportSearchBarSchema } from '../../../yup';

const AlignedFormGroup = styled(Form.Group)`
    &&& {
        align-items: flex-end;
    }
`;

const AirportSearchBar = ({ filterData, setFilterData, setError }) => {
    const { t } = useTranslation();

    const debounceLoadData = useCallback(debounce(setFilterData, 250), []);

    const search = (data) => {
        debounceLoadData({ ...filterData, [data.name]: data.value });
    };

    const translate = (msg) => {
        if (msg.key) {
            return t(msg.key, msg.value);
        }
        return t(msg);
    };

    return (
        <Formik
            validationSchema={AirportSearchBarSchema}
            initialValues={{
                name: '',
                city: '',
                code: '',
                country: '',
            }}
            validateOnChange
        >
            {({ errors, values, setFieldValue, handleBlur, isValid }) => (
                <Form>
                    <AlignedFormGroup>
                        <Form.Input
                            placeholder={t('Airport name')}
                            width={4}
                            name="name"
                            value={values.name}
                            onChange={(event) => {
                                setFieldValue(event.target.name, event.target.value, true);
                                if (isValid) {
                                    search({ name: event.target.name, value: event.target.value });
                                }
                            }}
                            onBlur={handleBlur}
                            error={
                                errors.name && {
                                    content: translate(errors.name),
                                    pointing: 'below',
                                }
                            }
                        />
                        <Form.Input
                            placeholder={t('Airport code')}
                            width={2}
                            name="code"
                            value={values.code}
                            onChange={(event) => {
                                setFieldValue(event.target.name, event.target.value);
                                if (isValid) {
                                    search({ name: event.target.name, value: event.target.value });
                                }
                            }}
                            error={
                                errors.code && {
                                    content: translate(errors.code),
                                    pointing: 'below',
                                }
                            }
                        />
                        <Form.Input
                            placeholder={t('City')}
                            width={4}
                            name="city"
                            onChange={(event) => {
                                setFieldValue(event.target.name, event.target.value);
                                if (isValid) {
                                    search({ name: event.target.name, value: event.target.value });
                                }
                            }}
                            value={values.city}
                            error={
                                errors.city && {
                                    content: translate(errors.city),
                                    pointing: 'below',
                                }
                            }
                        />
                        <CountriesDropdown
                            onChange={(value) => {
                                setFieldValue(value.name, value.value);
                                search(value);
                            }}
                            value={values.country}
                            setError={setError}
                        />
                    </AlignedFormGroup>
                </Form>
            )}
        </Formik>
    );
};

const AirportsTable = ({ airports, loading }) => {
    const { t } = useTranslation();
    const [init, setInit] = useState(true);
    if (init && !loading) {
        setInit(false);
    }

    return (
        <>
            {(init || (airports && airports.length > 0)) && (
                <Table celled>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell width={4} textAlign="center">
                                {t('Airport name')}
                            </Table.HeaderCell>
                            <Table.HeaderCell width={2} textAlign="center">
                                {t('Airport code')}
                            </Table.HeaderCell>
                            <Table.HeaderCell width={4} textAlign="center">
                                {t('City')}
                            </Table.HeaderCell>
                            <Table.HeaderCell width={4} textAlign="center">
                                {t('Country')}
                            </Table.HeaderCell>
                            <Table.HeaderCell width={1} textAlign="center">
                                {t('Action')}
                            </Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>
                    <Table.Body>
                        {init
                            ? [...Array(5).keys()].map((value) => (
                                  <Table.Row key={value}>
                                      {[...Array(6).keys()].map((value) => (
                                          <Table.Cell key={value}>
                                              <Placeholder>
                                                  <Placeholder.Paragraph>
                                                      <Placeholder.Line />
                                                      <Placeholder.Line />
                                                  </Placeholder.Paragraph>
                                              </Placeholder>
                                          </Table.Cell>
                                      ))}
                                  </Table.Row>
                              ))
                            : airports.map((airport) => (
                                  <Table.Row key={airport.code} disabled={loading}>
                                      <Table.Cell>{airport.name}</Table.Cell>
                                      <Table.Cell>{airport.code}</Table.Cell>
                                      <Table.Cell>{airport.city}</Table.Cell>
                                      <Table.Cell>{t(airport.country)}</Table.Cell>
                                      <Table.Cell textAlign="center">
                                          <Button size="small">{t('Edit')}</Button>
                                      </Table.Cell>
                                  </Table.Row>
                              ))}
                    </Table.Body>
                </Table>
            )}
        </>
    );
};

let searchCounter = 0;
const AirportsList = () => {
    const [airports, setAirports] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [filterData, setFilterData] = useState({});
    const makeCancellable = useCancellablePromise();
    const [countriesError, setCountriesError] = useState(null);
    const { t } = useTranslation();

    useEffect(() => {
        searchCounter += 1;
        const before = searchCounter;
        const fetchAirports = async () => {
            setError(null);
            try {
                setLoading(true);
                const airports = await makeCancellable(listAirports(filterData));
                if (searchCounter === before) {
                    setAirports(airports.content);
                }
            } catch (err) {
                setError(err);
            } finally {
                if (searchCounter === before) {
                    setLoading(false);
                }
            }
        };
        fetchAirports();
    }, [filterData, makeCancellable]);

    return (
        <ContentCard fluid>
            <Label attached="top">{t('Search for airports')}</Label>
            <AirportSearchBar
                filterData={filterData}
                setFilterData={setFilterData}
                setError={setCountriesError}
            />
            {error || countriesError ? (
                <Message
                    error
                    header={t('Failed to retrieve data')}
                    content={error && t(error.message)}
                />
            ) : (
                <>
                    <AirportsTable airports={airports} loading={loading} />
                    {airports && airports.length === 0 && (
                        <Message
                            header={t('No airport found')}
                            content={t('There are no results matching criteria')}
                        />
                    )}
                </>
            )}
        </ContentCard>
    );
};

export default AirportsList;

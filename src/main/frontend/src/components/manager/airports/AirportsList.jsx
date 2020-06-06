import React, { useState, useEffect, useCallback } from 'react';
import { useTranslation } from 'react-i18next';
import { Table, Button, Message, Placeholder, Form, Label } from 'semantic-ui-react';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import styled from 'styled-components';
import debounce from 'lodash.debounce';
import { listAirports, getCountries } from '../../../api/airports';
import { ContentCard } from '../../shared/Dashboard';
import RequireableDropdown from '../../shared/RequireableDropdown';

const AlignedFormGroup = styled(Form.Group)`
    &&& {
        align-items: flex-end;
    }
`;

const CountriesDropdown = ({ updateSearch }) => {
    const { t } = useTranslation();
    const [countries, setCountries] = useState([]);
    const [isFetching, setFetching] = useState(false);
    const makeCancellable = useCancellablePromise();

    const fetchCountries = useCallback(async () => {
        try {
            setFetching(true);
            let countries = await makeCancellable(getCountries());
            countries = countries.content.map((country) => ({
                key: country,
                value: country,
                text: t(country),
            }));
            setCountries(countries);
        } catch (err) {
            // mayhaps error handling in the future?
        } finally {
            setFetching(false);
        }
    }, [makeCancellable]);

    useEffect(() => {
        fetchCountries();
    }, [fetchCountries]);

    return (
        <RequireableDropdown
            options={countries}
            loading={isFetching}
            disabled={isFetching}
            onChange={(_, _value) => updateSearch({ name: 'country', value: _value.value })}
        />
    );
};

const AirportSearchBar = ({ setFilterData, filterData }) => {
    const { t } = useTranslation();

    const debounceLoadData = useCallback(debounce(setFilterData, 250), []);

    const handleChange = (data) => {
        debounceLoadData({ ...filterData, [data.name]: data.value });
    };

    return (
        <Form>
            <AlignedFormGroup>
                <Form.Input
                    placeholder={t('Airport name')}
                    width={4}
                    name="name"
                    onChange={(_, value) => handleChange(value)}
                />
                <Form.Input
                    placeholder={t('Airport code')}
                    width={2}
                    name="code"
                    onChange={(_, value) => handleChange(value)}
                />
                <Form.Input
                    placeholder={t('City')}
                    width={4}
                    name="city"
                    onChange={(_, value) => handleChange(value)}
                />
                <CountriesDropdown updateSearch={handleChange} />
            </AlignedFormGroup>
        </Form>
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

const AirportsList = () => {
    const [airports, setAirports] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [filterData, setFilterData] = useState({});
    const makeCancellable = useCancellablePromise();
    const { t } = useTranslation();

    useEffect(() => {
        const fetchAirports = async () => {
            setError(null);
            try {
                setLoading(true);
                const airports = await makeCancellable(listAirports(filterData));
                setAirports(airports.content);
            } catch (err) {
                setError(err);
            } finally {
                setLoading(false);
            }
        };
        fetchAirports();
    }, [filterData]);

    return (
        <ContentCard fluid>
            <Label attached="top">{t('Search for airports')}</Label>
            <AirportSearchBar filterData={filterData} setFilterData={setFilterData} />
            {error ? (
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

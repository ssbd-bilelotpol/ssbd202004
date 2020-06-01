import React, { useCallback, useEffect, useMemo, useState } from 'react';
import { Button, Label, Message, Table } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import Form from 'semantic-ui-react/dist/commonjs/collections/Form';
import * as i18nISOCountries from 'i18n-iso-countries';
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import debounce from 'lodash.debounce';
import { route } from '../../../routing';
import { ContentCard } from '../../shared/Dashboard';
import { listFlights } from '../../../api/flight';

const ButtonCell = styled(Table.Cell)`
    &&& {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
`;

const FlightSearchBar = ({ setFilterData }) => {
    const { t } = useTranslation();
    const [filterData, setFormFilterData] = useState({ code: '', name: '', country: '', city: '' });
    const debounceLoadData = useCallback(debounce(setFilterData, 250), []);

    const getCountryOptions = () => {
        return [{ key: 'empty' }].concat(
            Object.keys(i18nISOCountries.getAlpha2Codes()).map((code) => ({
                key: code,
                value: code.toLowerCase(),
                text: t(code),
            }))
        );
    };
    const countryOptions = useMemo(getCountryOptions, []);

    const handleChange = (_, data) => {
        setFormFilterData({
            ...filterData,
            [data.name]: data.value,
        });
        debounceLoadData(filterData);
    };

    return (
        <Form>
            <Form.Group>
                <Form.Input
                    placeholder={t('Flight code')}
                    width={3}
                    name="code"
                    onChange={handleChange}
                    value={filterData.code}
                />
                <Form.Input
                    placeholder={t('Flight name')}
                    width={8}
                    name="name"
                    onChange={handleChange}
                    value={filterData.name}
                />
                <Form.Input
                    placeholder={t('City')}
                    width={7}
                    name="city"
                    onChange={handleChange}
                    value={filterData.city}
                />
            </Form.Group>
            <Form.Dropdown
                search
                selection
                name="country"
                placeholder={t('Country')}
                options={countryOptions}
                onChange={handleChange}
                value={filterData.country}
            />
        </Form>
    );
};

const FlightTable = ({ flights, loading }) => {
    const { t } = useTranslation();
    return (
        <>
            {flights && (
                <Table celled>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell width={2}>{t('Code')}</Table.HeaderCell>
                            <Table.HeaderCell width={4}>{t('Flight name')}</Table.HeaderCell>
                            <Table.HeaderCell width={4}>{t('Country')}</Table.HeaderCell>
                            <Table.HeaderCell width={6}>{t('City')}</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>
                    <Table.Body>
                        {flights.map((flight) => (
                            <Table.Row key={flight.code} disabled={loading}>
                                <Table.Cell>{flight.code}</Table.Cell>
                                <Table.Cell>{flight.name}</Table.Cell>
                                <Table.Cell>{t(flight.country.toUpperCase())}</Table.Cell>
                                <ButtonCell>
                                    {flight.city}
                                    <Button
                                        as={Link}
                                        to={route('manager.flights.flight.edit', {
                                            code: flight.code,
                                        })}
                                        size="small"
                                    >
                                        {t('Edit')}
                                    </Button>
                                </ButtonCell>
                            </Table.Row>
                        ))}
                    </Table.Body>
                </Table>
            )}
        </>
    );
};

let searchCounter = 0;
const FlightsList = () => {
    const [flights, setFlights] = useState(null);
    const [filterData, setFilterData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const { t } = useTranslation();
    const makeCancellable = useCancellablePromise();

    useEffect(() => {
        const fetchFlights = async () => {
            searchCounter += 1;
            const before = searchCounter;
            try {
                setLoading(true);
                const flights = await makeCancellable(listFlights(filterData));
                if (searchCounter === before) {
                    setFlights(flights.content);
                }
            } catch (err) {
                setError(err);
            } finally {
                if (searchCounter === before) {
                    setLoading(false);
                }
            }
        };
        fetchFlights();
    }, [filterData, makeCancellable]);

    return (
        <ContentCard fluid>
            <Label attached="top">{t('Search for flights')}</Label>
            <FlightSearchBar filterData={filterData} setFilterData={setFilterData} />
            {error ? (
                <Message
                    error
                    header={t('Failed to retrieve data')}
                    content={error && t(error.message)}
                />
            ) : (
                <>
                    <FlightTable flights={flights} loading={loading} />
                    {filterData && flights && (
                        <Message
                            header={t('No such flight')}
                            content={t('There are no results matching criteria')}
                        />
                    )}
                </>
            )}
        </ContentCard>
    );
};

export default FlightsList;

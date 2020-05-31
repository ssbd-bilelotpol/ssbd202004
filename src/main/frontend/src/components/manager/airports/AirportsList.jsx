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
import { listAirports } from '../../../api/airport';

const ButtonCell = styled(Table.Cell)`
    &&& {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
`;

const AirportSearchBar = ({ filterData, setFilterData }) => {
    const { t } = useTranslation();
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
        debounceLoadData({
            ...filterData,
            [data.name]: data.value,
        });
    };

    return (
        <Form>
            <Form.Group>
                <Form.Input
                    placeholder={t('Airport code')}
                    width={3}
                    name="code"
                    onChange={handleChange}
                    value={filterData.code}
                />
                <Form.Input
                    placeholder={t('Airport name')}
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

const AirportTable = ({ airports, loading }) => {
    const { t } = useTranslation();

    return (
        <>
            {airports && airports.length > 0 && (
                <Table celled>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell width={2}>{t('Code')}</Table.HeaderCell>
                            <Table.HeaderCell width={4}>{t('Airport name')}</Table.HeaderCell>
                            <Table.HeaderCell width={4}>{t('Country')}</Table.HeaderCell>
                            <Table.HeaderCell width={6}>{t('City')}</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>
                    <Table.Body>
                        {airports.map((airport) => (
                            <Table.Row key={airport.code} disabled={loading}>
                                <Table.Cell>{airport.code}</Table.Cell>
                                <Table.Cell>{airport.name}</Table.Cell>
                                <Table.Cell>{t(airport.country.toUpperCase())}</Table.Cell>
                                <ButtonCell>
                                    {airport.city}
                                    <Button
                                        as={Link}
                                        to={route('manager.airports.airport.edit', {
                                            code: airport.code,
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
const AirportsList = () => {
    const [airports, setAirports] = useState(null);
    const [filterData, setFilterData] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const { t } = useTranslation();
    const makeCancellable = useCancellablePromise();

    useEffect(() => {
        const fetchAirports = async () => {
            searchCounter += 1;
            const before = searchCounter;
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
            <AirportSearchBar filterData={filterData} setFilterData={setFilterData} />
            {error ? (
                <Message
                    error
                    header={t('Failed to retrieve data')}
                    content={error && t(error.message)}
                />
            ) : (
                <>
                    <AirportTable airports={airports} loading={loading} />
                    {searchCounter > 0 && airports.length === 0 && (
                        <Message
                            header={t('No such airport')}
                            content={t('There are no results matching criteria')}
                        />
                    )}
                </>
            )}
        </ContentCard>
    );
};

export default AirportsList;

import React, { useCallback, useEffect, useState } from 'react';
import { Button, Label, Message, Table } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import Form from 'semantic-ui-react/dist/commonjs/collections/Form';
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import debounce from 'lodash.debounce';
import { route } from '../../../routing';
import { ContentCard } from '../../shared/Dashboard';
import { listFlights } from '../../../api/flight';
import ConnectionDropdown from './ConnectionDropdown';
import SchemaDropdown from './SchemaDropdown';

const ButtonCell = styled.div`
    &&& {
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
`;

const AlignedFormGroup = styled(Form.Group)`
    &&& {
        align-items: flex-end;
    }
`;

const FlightSearchBar = ({ setFilterData, setError }) => {
    const { t } = useTranslation();
    const [filterData, setFormFilterData] = useState({
        code: '',
        connection: '',
        airplane: '',
        date: '',
    });
    const debounceLoadData = useCallback(debounce(setFilterData, 250), []);

    const handleChange = (_, data) => {
        setFormFilterData({
            ...filterData,
            [data.name]: data.value,
        });
        debounceLoadData(filterData);
    };

    return (
        <Form>
            <AlignedFormGroup>
                <Form.Input
                    placeholder={t('Flight code')}
                    width={2}
                    name="code"
                    onChange={handleChange}
                    value={filterData.code}
                />
                <ConnectionDropdown
                    placeholder={t('Connection')}
                    width={5}
                    name="connection"
                    value={filterData.connection}
                    onChange={(value) => handleChange(null, { name: 'connection', value })}
                    setError={setError}
                />
                <SchemaDropdown
                    placeholder={t('Airplane')}
                    width={4}
                    name="airplane"
                    value={filterData.airplane}
                    onChange={(value) => handleChange(null, { name: 'airplane', value })}
                    setError={setError}
                />
                <Form.Input
                    width={5}
                    name="date"
                    type="date"
                    onChange={handleChange}
                    value={filterData.date}
                    labelPosition="left"
                >
                    <Label basic content={t('Date')} />
                    <input />
                </Form.Input>
            </AlignedFormGroup>
        </Form>
    );
};

const FlightTable = ({ flights, loading }) => {
    const { t } = useTranslation();
    return (
        <>
            {flights && flights.length > 0 && (
                <Table celled structured striped size="small">
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell width={2} rowSpan="2" textAlign="center">
                                {t('Code')}
                            </Table.HeaderCell>
                            <Table.HeaderCell width={6} colSpan="2" textAlign="center">
                                {t('Connection')}
                            </Table.HeaderCell>
                            <Table.HeaderCell width={3} rowSpan="2" textAlign="center">
                                {t('Airplane')}
                            </Table.HeaderCell>
                            <Table.HeaderCell width={5} rowSpan="2" textAlign="center">
                                {t('Date')}
                            </Table.HeaderCell>
                        </Table.Row>
                        <Table.Row>
                            <Table.HeaderCell textAlign="center">{t('Airports')}</Table.HeaderCell>
                            <Table.HeaderCell textAlign="center">{t('Countries')}</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>
                    <Table.Body>
                        {flights.map((flight) => (
                            <Table.Row key={flight.code} disabled={loading}>
                                <Table.Cell>{flight.code}</Table.Cell>
                                <Table.Cell>
                                    {`${flight.source.code} - ${flight.destination.code}`}
                                </Table.Cell>
                                <Table.Cell>
                                    {`${t(flight.source.country.toUpperCase())} - ${t(
                                        flight.destination.country.toUpperCase()
                                    )}`}
                                </Table.Cell>
                                <Table.Cell>{flight.airplane}</Table.Cell>
                                <Table.Cell>
                                    <ButtonCell>
                                        {flight.date}
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
                    setFlights(flights);
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
                    {flights && flights.length === 0 && (
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

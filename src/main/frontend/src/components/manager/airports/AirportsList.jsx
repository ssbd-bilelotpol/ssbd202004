import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { Table, Button, Message, Placeholder } from 'semantic-ui-react';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import { listAirports } from '../../../api/airports';

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
                                      <Table.Cell>{airport.country}</Table.Cell>
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
    const makeCancellable = useCancellablePromise();
    const { t } = useTranslation();

    useEffect(() => {
        const fetchAirports = async () => {
            try {
                setLoading(true);
                const airports = await makeCancellable(listAirports());
                setAirports(airports.content);
            } catch (err) {
                setError(err);
            } finally {
                setLoading(false);
            }
        };
        fetchAirports();
    }, [makeCancellable]);

    return (
        <>
            {error ? (
                <Message
                    error
                    header={t('Failed to retrieve data')}
                    content={error && t(error.message)}
                />
            ) : (
                <AirportsTable airports={airports} loading={loading} />
            )}
        </>
    );
};

export default AirportsList;

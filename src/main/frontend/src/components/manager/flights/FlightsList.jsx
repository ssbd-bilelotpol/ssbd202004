import React, { useCallback, useState, useEffect } from 'react';
import { Button, Grid, Label, Message, Placeholder, Table } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import Form from 'semantic-ui-react/dist/commonjs/collections/Form';
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import debounce from 'lodash.debounce';
import i18next from 'i18next';
import moment from 'moment';
import Moment from 'react-moment';
import Icon from 'semantic-ui-react/dist/commonjs/elements/Icon';
import Popup from 'semantic-ui-react/dist/commonjs/modules/Popup';
import { route } from '../../../routing';
import { ContentCard } from '../../shared/Dashboard';
import { useFlights } from '../../../api/flights';
import ConnectionDropdown from './ConnectionDropdown';
import SchemaDropdown from './SchemaDropdown';
import SemanticDatePicker from '../../shared/Datepicker';
import { flightStatus } from '../../../constants';

const AlignedFormGroup = styled(Form.Group)`
    &&& {
        align-items: flex-end;
    }
`;

const FlightSearchBar = ({ setFilterData, setError, dates }) => {
    const { t } = useTranslation();
    const convertedDates = React.useMemo(() => dates.map((date) => new Date(date)), [dates]);
    const [filterData, setFormFilterData] = useState({
        code: '',
        connectionId: '',
        airplaneId: '',
        from: '',
        to: '',
    });
    const debounceLoadData = useCallback(debounce(setFilterData, 250), []);
    const formatData = (data) => {
        const formattedData = { ...data };
        if (formattedData.from) {
            formattedData.from = formattedData.from.toISOString();
        }
        if (formattedData.to) {
            formattedData.to = moment(formattedData.to).endOf('day').toISOString();
        }
        return formattedData;
    };

    const handleChange = (data) => {
        const newData = {
            ...filterData,
            ...data,
        };
        setFormFilterData(newData);
        debounceLoadData(formatData(newData));
    };

    return (
        <Form>
            <AlignedFormGroup>
                <Form.Input
                    placeholder={t('Code')}
                    width={3}
                    name="code"
                    onChange={(_, value) => handleChange({ code: value.value })}
                    value={filterData.code}
                />
                <ConnectionDropdown
                    placeholder={t('Connection')}
                    width={8}
                    name="connectionId"
                    value={filterData.connection}
                    onChange={(value) => handleChange({ connection: value })}
                    setError={setError}
                    clearable
                />
                <SchemaDropdown
                    placeholder={t('Airplane')}
                    name="airplaneId"
                    value={filterData.airplane}
                    setFieldValue={(_, value) => handleChange({ airplane: value })}
                    setError={setError}
                    clearable
                />
                <Label>
                    {t('Flight date')}
                    <Grid columns={2}>
                        <Grid.Column>
                            <SemanticDatePicker
                                placeholderText={t('From')}
                                isClearable={filterData.from}
                                name="from"
                                selectsStart
                                selected={filterData.from}
                                startDate={filterData.from}
                                endDate={filterData.to}
                                setFieldValue={(_, value) => handleChange({ from: value })}
                                locale={i18next.language}
                                dateFormat="P"
                                maxDate={filterData.to}
                                includeDates={convertedDates}
                            />
                        </Grid.Column>
                        <Grid.Column>
                            <SemanticDatePicker
                                placeholderText={t('To')}
                                isClearable={filterData.to}
                                name="to"
                                selectsEnd
                                selected={filterData.to}
                                startDate={filterData.from}
                                endDate={filterData.to}
                                setFieldValue={(_, value) => handleChange({ to: value })}
                                locale={i18next.language}
                                dateFormat="P"
                                maxDate={filterData.to}
                                includeDates={convertedDates}
                            />
                        </Grid.Column>
                    </Grid>
                </Label>
            </AlignedFormGroup>
        </Form>
    );
};

const FlightStatus = ({ t, flight }) => {
    if (flight.status === flightStatus.cancelled) {
        return <Popup trigger={<Icon name="ban" />} content={t('Cancelled')} size="mini" />;
    }
    if (moment(flight.startDateTime).isBefore(moment.now())) {
        return <Popup trigger={<Icon name="plane" />} content={t('Took place')} size="mini" />;
    }
    if (flight.status === flightStatus.active) {
        return <Popup trigger={<Icon name="check" />} content={t('Active')} size="mini" />;
    }
    return <Popup trigger={<Icon name="minus circle" />} content={t('Inactive')} size="mini" />;
};
const FlightTable = ({ flights, loading }) => {
    const { t } = useTranslation();
    const [init, setInit] = useState(true);
    if (init && !loading) {
        setInit(false);
    }

    return (
        <>
            {(init || (flights && flights.length > 0)) && (
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
                            <Table.HeaderCell width={3} rowSpan="2" textAlign="center">
                                {t('Date')}
                            </Table.HeaderCell>
                            <Table.HeaderCell width={1} rowSpan="2" textAlign="center">
                                {t('Status')}
                            </Table.HeaderCell>
                            <Table.HeaderCell width={1} rowSpan="2" textAlign="center">
                                {t('Action')}
                            </Table.HeaderCell>
                        </Table.Row>
                        <Table.Row>
                            <Table.HeaderCell textAlign="center">{t('Airports')}</Table.HeaderCell>
                            <Table.HeaderCell textAlign="center">{t('Countries')}</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>
                    <Table.Body>
                        {init
                            ? [...Array(5).keys()].map((value) => (
                                  <Table.Row key={value}>
                                      {[...Array(7).keys()].map((value) => (
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
                            : flights
                                  .sort((a, b) => a.startDateTime - b.startDateTime)
                                  .map((flight) => (
                                      <Table.Row key={flight.code} disabled={loading}>
                                          <Table.Cell>{flight.code}</Table.Cell>
                                          <Table.Cell>
                                              {flight.connection.source.code} -{' '}
                                              {flight.connection.destination.code}
                                          </Table.Cell>
                                          <Table.Cell>
                                              {t(flight.connection.source.country.toUpperCase())} -{' '}
                                              {t(
                                                  flight.connection.destination.country.toUpperCase()
                                              )}
                                          </Table.Cell>
                                          <Table.Cell>{flight.airplaneSchema.name}</Table.Cell>
                                          <Table.Cell>
                                              <Moment
                                                  date={flight.startDateTime}
                                                  format="L LT"
                                                  locale={i18next.language}
                                              />
                                          </Table.Cell>
                                          <Table.Cell textAlign="center">
                                              <FlightStatus flight={flight} t={t} />
                                          </Table.Cell>
                                          <Table.Cell textAlign="center">
                                              <Button
                                                  as={Link}
                                                  to={route('manager.flights.flight.edit', {
                                                      code: flight.code,
                                                  })}
                                                  size="small"
                                              >
                                                  {t('Edit')}
                                              </Button>
                                          </Table.Cell>
                                      </Table.Row>
                                  ))}
                    </Table.Body>
                </Table>
            )}
        </>
    );
};

const FlightsList = () => {
    const [filterData, setFilterData] = useState({});
    const [error, setError] = useState();
    const { t } = useTranslation();
    const { data: flights, loading, error: flightsError } = useFlights(filterData);
    useEffect(() => flightsError && setError(flightsError), [flightsError]);

    return (
        <ContentCard fluid>
            <Label attached="top">{t('Search for flights')}</Label>
            <FlightSearchBar
                filterData={filterData}
                setFilterData={setFilterData}
                dates={flights.map((flight) => flight.startDateTime)}
            />
            {error ? (
                <Message
                    error
                    header={t('Failed to retrieve data')}
                    content={error && t(error.message)}
                />
            ) : (
                <>
                    <FlightTable flights={flights} loading={loading} />
                    {!loading && flights && flights.length === 0 && (
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

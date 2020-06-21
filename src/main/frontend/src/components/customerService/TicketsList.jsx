import React, { useCallback, useState } from 'react';
import { Button, Grid, Label, Message, Placeholder, Table } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import Form from 'semantic-ui-react/dist/commonjs/collections/Form';
import styled from 'styled-components';
import debounce from 'lodash.debounce';
import i18next from 'i18next';
import moment from 'moment';
import { Link } from 'react-router-dom';
import { ContentCard } from '../shared/Dashboard';
import ConnectionDropdown from '../manager/flights/ConnectionDropdown';
import SchemaDropdown from '../manager/flights/SchemaDropdown';
import SemanticDatePicker from '../shared/Datepicker';
import { useTickets } from '../../api/tickets';
import { route } from '../../routing';

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
                <Form.Field>
                    <SchemaDropdown
                        placeholder={t('Airplane')}
                        name="airplaneId"
                        value={filterData.airplane}
                        setFieldValue={(_, value) => handleChange({ airplane: value })}
                        setError={setError}
                        clearable
                    />
                </Form.Field>
                <Label>
                    {t('Purchase date')}
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

const TicketsTable = ({ tickets, loading }) => {
    const { t } = useTranslation();
    const [init, setInit] = useState(true);
    if (init && !loading) {
        setInit(false);
    }

    return (
        <>
            {(init || (tickets && tickets.length > 0)) && (
                <Table celled structured striped size="small" textAlign="center">
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell width={2} rowSpan="2" textAlign="center">
                                {t('Flight code')}
                            </Table.HeaderCell>
                            <Table.HeaderCell width={4} rowSpan="2" textAlign="center">
                                {t('Email')}
                            </Table.HeaderCell>
                            <Table.HeaderCell width={4} rowSpan="2" textAlign="center">
                                {t('Price')}
                            </Table.HeaderCell>
                            <Table.HeaderCell width={4} rowSpan="2" textAlign="center">
                                {t('Purchase date')}
                            </Table.HeaderCell>
                            <Table.HeaderCell width={2} rowSpan="2" textAlign="center">
                                {t('Action')}
                            </Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>
                    <Table.Body>
                        {init
                            ? [...Array(5).keys()].map((value) => (
                                  <Table.Row key={value}>
                                      {[...Array(4).keys()].map((value) => (
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
                            : tickets
                                  .sort((a, b) => a.flightCode - b.flightCode)
                                  .map((ticket) => (
                                      <Table.Row key={ticket.id} disabled={loading}>
                                          <Table.Cell>{ticket.flightCode}</Table.Cell>
                                          <Table.Cell>{ticket.email}</Table.Cell>
                                          <Table.Cell>{ticket.totalPrice}</Table.Cell>
                                          <Table.Cell>
                                              {new Date(ticket.date).toLocaleString()}
                                          </Table.Cell>
                                          <Table.Cell textAlign="center">
                                              <Button
                                                  as={Link}
                                                  to={route('customer_service.tickets.view', {
                                                      id: ticket.id,
                                                  })}
                                                  size="small"
                                              >
                                                  {t('View')}
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

const TicketsList = () => {
    const [filterData, setFilterData] = useState({});
    const { t } = useTranslation();
    const { data: tickets, loading, error } = useTickets(filterData);

    return (
        <ContentCard fluid>
            <Label attached="top">{t('Search for tickets')}</Label>
            <FlightSearchBar
                filterData={filterData}
                setFilterData={setFilterData}
                dates={tickets.map((ticket) => ticket.date)}
            />
            {!loading && (
                <>
                    {error ? (
                        <Message
                            error
                            header={t('Failed to retrieve data')}
                            content={error && t(error.message)}
                        />
                    ) : (
                        <>
                            <TicketsTable tickets={tickets} loading={loading} />
                            {tickets && tickets.length === 0 && (
                                <Message
                                    header={t('No such ticket')}
                                    content={t('There are no results matching criteria')}
                                />
                            )}
                        </>
                    )}
                </>
            )}
        </ContentCard>
    );
};

export default TicketsList;

import React, { useState, useCallback } from 'react';
import { Table, Message, Button, Label } from 'semantic-ui-react';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';
import debounce from 'lodash.debounce';
import Form from 'semantic-ui-react/dist/commonjs/collections/Form';
import { Link } from 'react-router-dom';
import { ContentCard } from '../shared/Dashboard';
import { useClients } from '../../api/client';
import { route } from '../../routing';

const AlignedFormGroup = styled(Form.Group)`
    &&& {
        align-items: flex-end;
    }
`;

const ClientSearchBar = ({ setFilterData }) => {
    const { t } = useTranslation();
    const [filterData, setFormFilterData] = useState({
        flightCode: '',
        name: '',
    });
    const debounceLoadData = useCallback(debounce(setFilterData, 250), []);

    const handleChange = (data) => {
        const newData = {
            ...filterData,
            ...data,
        };
        setFormFilterData(newData);
        debounceLoadData(newData);
    };

    return (
        <Form>
            <AlignedFormGroup>
                <Form.Input
                    placeholder={t('Flight code')}
                    width={8}
                    name="flightCode"
                    onChange={(_, value) => handleChange({ flightCode: value.value })}
                    value={filterData.flightCode}
                />
                <Form.Input
                    placeholder={t('Name')}
                    width={8}
                    name="name"
                    onChange={(_, value) => handleChange({ name: value.value })}
                    value={filterData.name}
                />
            </AlignedFormGroup>
        </Form>
    );
};

const ClientsTable = ({ clients, loading }) => {
    const { t } = useTranslation();
    const [init, setInit] = useState(true);
    if (init && !loading) {
        setInit(false);
    }

    return (
        <>
            {(init || (clients && clients.length > 0)) && (
                <Table celled>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell>{t('Flight code')}</Table.HeaderCell>
                            <Table.HeaderCell>{t("Client's name")}</Table.HeaderCell>
                            <Table.HeaderCell>{t('Email')}</Table.HeaderCell>
                            <Table.HeaderCell>{t('Phone number')}</Table.HeaderCell>
                            <Table.HeaderCell>{t('Action')}</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>
                    <Table.Body>
                        {clients
                            .sort((a, b) => a.flight.code.localeCompare(b.flight.code))
                            .map((client) => (
                                <Table.Row key={client.id} disabled={loading}>
                                    <Table.Cell>
                                        <span>{client.flight.code}</span>
                                    </Table.Cell>
                                    <Table.Cell>
                                        <span>
                                            {client.firstName} {client.lastName}
                                        </span>
                                    </Table.Cell>
                                    <Table.Cell>
                                        <span>{client.email}</span>
                                    </Table.Cell>
                                    <Table.Cell>
                                        <span>{client.phoneNumber}</span>
                                    </Table.Cell>
                                    <Table.Cell>
                                        <Button
                                            as={Link}
                                            to={route('customer_service.tickets.view', {
                                                id: client.ticketId,
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

const ClientsList = () => {
    const [filterData, setFilterData] = useState({});
    const { data, loading, error } = useClients(filterData);
    const { t } = useTranslation();

    return (
        <ContentCard fluid>
            <Label attached="top">{t('Search for users')}</Label>
            <ClientSearchBar setFilterData={setFilterData} />
            {error ? (
                <Message
                    error
                    header={t('Failed to retrieve data')}
                    content={error && t(error.message)}
                />
            ) : (
                <>
                    <ClientsTable clients={data} loading={loading} />
                    {!loading && data && data.length === 0 && (
                        <Message
                            header={t('No such client')}
                            content={t('There are no results matching criteria')}
                        />
                    )}
                </>
            )}
        </ContentCard>
    );
};

export default ClientsList;

import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useTranslation } from 'react-i18next';
import { Label, Message, Placeholder, Table } from 'semantic-ui-react';
import styled from 'styled-components';
import { getAccountAuthReport } from '../../actions/accounts';
import { ContentCard } from '../shared/Dashboard';

const TableBorder = styled(Table)`
    &&& {
        border-spacing: 2px;
    }
`;

const AccountAuthReport = () => {
    const { t } = useTranslation();
    const users = useSelector((state) => state.accountsAuthReport.users);
    const loading = useSelector((state) => state.accountsAuthReport.isLoadingAccounts);
    const error = useSelector((store) => store.accountsAuthReport.error);

    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(getAccountAuthReport());
    }, [dispatch]);

    return (
        <ContentCard fluid>
            <Label attached="top">{t('Last valid authentication report')}</Label>
            {!loading && !error && users.length === 0 && (
                <Message content={t('No results in the database')} />
            )}
            <TableBorder celled>
                <Table.Header>
                    <Table.Row>
                        <Table.HeaderCell>{t('User')}</Table.HeaderCell>
                        <Table.HeaderCell>{t('IP address')}</Table.HeaderCell>
                        <Table.HeaderCell>{t('Date and time')}</Table.HeaderCell>
                    </Table.Row>
                </Table.Header>
                <Table.Body>
                    {users.length > 0 &&
                        users.map((user) => (
                            <Table.Row key={user.login}>
                                <Table.Cell>{user.login}</Table.Cell>
                                <Table.Cell>{user.lastIpAddress}</Table.Cell>
                                <Table.Cell>
                                    {user.currentAuth &&
                                        new Date(user.currentAuth).toLocaleString()}
                                </Table.Cell>
                            </Table.Row>
                        ))}
                    {loading &&
                        [...Array(10).keys()].map((value) => (
                            <Table.Row key={value}>
                                {[...Array(3).keys()].map((value) => (
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
                        ))}
                </Table.Body>
            </TableBorder>
            {error && (
                <Message error header={t('Failed to retrieve data')} content={t(error.message)} />
            )}
        </ContentCard>
    );
};

export default AccountAuthReport;

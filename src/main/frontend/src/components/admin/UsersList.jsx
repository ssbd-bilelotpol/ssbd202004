import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { Table, Input, Placeholder, Message, Button } from 'semantic-ui-react';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';
import { getAllAccountsAction } from '../../actions/accounts';

const StyledInput = styled(Input)`
    &&& {
        width: 100%;
    }
`;

const SearchBar = ({ filter, t }) => {
    return <StyledInput loading={false} placeholder={t('Search')} onChange={filter} />;
};

const FilterableUsersTable = () => {
    const [userToFilter, setFilteredUsers] = useState('');
    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(getAllAccountsAction());
    }, [dispatch]);

    const filterUsers = (event) => {
        setFilteredUsers(event.target.value.toLowerCase());
    };

    const { t } = useTranslation();
    const users = useSelector((store) => store.accounts.users.content);
    const error = useSelector((store) => store.accounts.error);

    if (users) {
        const filteredUsers = users.filter(
            (user) =>
                user.firstName.concat(' ', user.lastName).toLowerCase().includes(userToFilter) ||
                user.login.toLowerCase().includes(userToFilter)
        );
        if (filteredUsers.length > 0) {
            return (
                <div>
                    <SearchBar filter={filterUsers} t={t} />
                    <Table celled>
                        <Table.Header>
                            <Table.Row>
                                <Table.HeaderCell>{t('User')}</Table.HeaderCell>
                            </Table.Row>
                        </Table.Header>
                        <Table.Body>
                            {filteredUsers.map((user) => (
                                <Table.Row key={user.login}>
                                    <Table.Cell
                                        style={{
                                            display: 'flex',
                                            justifyContent: 'space-between',
                                            alignItems: 'center',
                                        }}
                                    >
                                        <span>
                                            {user.firstName} {user.lastName} ({user.login})
                                        </span>
                                        <Button
                                            as={Link}
                                            to={`/admin/accounts/${user.login}/edit`}
                                            size="small"
                                        >
                                            {t('Edit')}
                                        </Button>
                                    </Table.Cell>
                                </Table.Row>
                            ))}
                        </Table.Body>
                    </Table>
                </div>
            );
        }
        return (
            <div>
                <SearchBar filter={filterUsers} t={t} />
                <Message
                    header={t('No such person')}
                    content={t('There are no results matching criteria')}
                />
            </div>
        );
    }
    if (error) {
        return (
            <div>
                <Message
                    error
                    header={t('Failed to retrieve data')}
                    content={error && t(error.message)}
                />
            </div>
        );
    }
    return (
        <div>
            <SearchBar filter={filterUsers} t={t} />
            <Table celled>
                <Table.Header>
                    <Table.Row>
                        <Table.HeaderCell>{t('User')}</Table.HeaderCell>
                    </Table.Row>
                </Table.Header>
                <Table.Body>
                    <Table.Row>
                        <Table.Cell>
                            <Placeholder>
                                <Placeholder.Paragraph>
                                    <Placeholder.Line />
                                    <Placeholder.Line />
                                </Placeholder.Paragraph>
                            </Placeholder>
                        </Table.Cell>
                    </Table.Row>
                    <Table.Row>
                        <Table.Cell>
                            <Placeholder>
                                <Placeholder.Paragraph>
                                    <Placeholder.Line />
                                    <Placeholder.Line />
                                </Placeholder.Paragraph>
                            </Placeholder>
                        </Table.Cell>
                    </Table.Row>
                    <Table.Row>
                        <Table.Cell>
                            <Placeholder>
                                <Placeholder.Paragraph>
                                    <Placeholder.Line />
                                    <Placeholder.Line />
                                </Placeholder.Paragraph>
                            </Placeholder>
                        </Table.Cell>
                    </Table.Row>
                </Table.Body>
            </Table>
        </div>
    );
};

export default FilterableUsersTable;

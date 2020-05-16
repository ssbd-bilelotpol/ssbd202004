import React, { useState, useEffect, useCallback } from 'react';
import { Link } from 'react-router-dom';
import { Table, Input, Message, Button } from 'semantic-ui-react';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import debounce from 'lodash.debounce';
import { listAccountsApi } from '../../api/accounts';
import { route } from '../../routing';

const StyledInput = styled(Input)`
    &&& {
        width: 100%;
    }
`;

const UserCell = styled(Table.Cell)`
    &&& {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
`;

const SearchBar = ({ filterUsers, loading }) => {
    const [nameToFind, setNameToFind] = useState('');
    const { t } = useTranslation();

    const debounceLoadData = useCallback(debounce(filterUsers, 250), []);

    useEffect(() => {
        debounceLoadData(nameToFind);
    }, [nameToFind, debounceLoadData]);

    return (
        <StyledInput
            loading={loading}
            placeholder={t('Search')}
            value={nameToFind}
            onChange={(event) => setNameToFind(event.target.value)}
        />
    );
};

const UsersTable = ({ users, loading }) => {
    const { t } = useTranslation();

    return (
        <>
            {users && users.length > 0 && (
                <Table celled>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell>{t('User')}</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>
                    <Table.Body>
                        {users.map((user) => (
                            <Table.Row key={user.login} disabled={loading}>
                                <UserCell>
                                    <span>
                                        {user.firstName} {user.lastName} ({user.login})
                                    </span>
                                    <Button
                                        as={Link}
                                        to={route('admin.accounts.user.edit', {
                                            login: user.login,
                                        })}
                                        size="small"
                                    >
                                        {t('Edit')}
                                    </Button>
                                </UserCell>
                            </Table.Row>
                        ))}
                    </Table.Body>
                </Table>
            )}
        </>
    );
};

let searchCounter = 0;

const FilterableUsersTable = () => {
    const [users, setUsers] = useState(null);
    const [nameToFind, setNameToFind] = useState('');
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const { t } = useTranslation();
    const makeCancellable = useCancellablePromise();

    const filterUsers = (name) => {
        setNameToFind(name);
    };

    useEffect(() => {
        const fetchUsers = async () => {
            searchCounter += 1;
            const before = searchCounter;
            try {
                setLoading(true);
                const accounts = await makeCancellable(listAccountsApi(nameToFind));
                if (searchCounter === before) {
                    setUsers(accounts.content);
                }
            } catch (err) {
                setError(err);
            } finally {
                if (searchCounter === before) {
                    setLoading(false);
                }
            }
        };
        fetchUsers();
    }, [nameToFind, makeCancellable]);

    return (
        <>
            <SearchBar filterUsers={filterUsers} loading={users ? loading : false} />
            {!error && (
                <>
                    <UsersTable users={users} loading={loading} />
                    {nameToFind && users.length === 0 && (
                        <Message
                            header={t('No such person')}
                            content={t('There are no results matching criteria')}
                        />
                    )}
                </>
            )}
            {error && (
                <Message
                    error
                    header={t('Failed to retrieve data')}
                    content={error && t(error.message)}
                />
            )}
        </>
    );
};

export default FilterableUsersTable;

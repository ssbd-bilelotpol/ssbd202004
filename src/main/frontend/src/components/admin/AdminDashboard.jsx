import React from 'react';
import { Route, Switch, Redirect } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import FilterableUsersTable from './UsersList';
import EditAccount from './EditAccount';
import AccountAuthReport from './AccountAuthReport';
import { route } from '../../routing';
import Dashboard from '../shared/Dashboard';

const AdminDashboard = () => {
    const { t } = useTranslation();
    const menuItems = [
        {
            header: t('Accounts'),
            items: [
                {
                    name: t('List accounts'),
                    route: 'admin.accounts.list',
                },
                {
                    name: t('Accounts auth report'),
                    route: 'admin.accounts.authReport',
                },
            ],
        },
    ];
    return (
        <Dashboard menuItems={menuItems}>
            <Switch>
                <Route path={route('admin.accounts.user.edit')}>
                    <EditAccount />
                </Route>
                <Route exact path={route('admin')}>
                    <Redirect to={route('admin.accounts.list')} />
                </Route>
                <Route exact path={route('admin.accounts')}>
                    <Redirect to={route('admin.accounts.list')} />
                </Route>
                <Route path={route('admin.accounts.list')}>
                    <FilterableUsersTable />
                </Route>
                <Route path={route('admin.accounts.authReport')}>
                    <AccountAuthReport />
                </Route>
            </Switch>
        </Dashboard>
    );
};

export default AdminDashboard;

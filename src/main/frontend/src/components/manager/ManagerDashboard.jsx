import { useTranslation } from 'react-i18next';
import { Redirect, Route, Switch } from 'react-router-dom';
import React from 'react';
import Dashboard from '../shared/Dashboard';
import { route } from '../../routing';
import AddAirport from './airports/AddAirport';
import AirportsList from './airports/AirportsList';

const ManagerDashboard = () => {
    const { t } = useTranslation();
    const menuItems = [
        {
            header: t('Airports'),
            items: [
                {
                    name: t('List airports'),
                    route: 'manager.airports.list',
                },
                {
                    name: t('Add airport'),
                    route: 'manager.airports.add',
                },
            ],
        },
        {
            header: t('Relations'),
            items: [
                {
                    name: t('List relations'),
                    route: 'manager.relations.list',
                },
                {
                    name: t('Add relation'),
                    route: 'manager.relations.add',
                },
            ],
        },
        {
            header: t('Flights'),
            items: [
                {
                    name: t('List flights'),
                    route: 'manager.flights.list',
                },
                {
                    name: t('Add flight'),
                    route: 'manager.flights.add',
                },
            ],
        },
        {
            header: t('Planes'),
            items: [
                {
                    name: t('List planes'),
                    route: 'manager.planes.list',
                },
                {
                    name: t('Add plane'),
                    route: 'manager.planes.add',
                },
            ],
        },
    ];

    return (
        <Dashboard menuItems={menuItems}>
            <Switch>
                <Route exact path={route('manager')}>
                    <Redirect to={route('manager.airports.list')} />
                </Route>
                <Route exact path={route('manager.airports')}>
                    <Redirect to={route('manager.airports.list')} />
                </Route>
                <Route path={route('manager.airports.list')}>
                    <AirportsList />
                </Route>
                <Route path={route('manager.airports.add')}>
                    <AddAirport />
                </Route>
            </Switch>
        </Dashboard>
    );
};

export default ManagerDashboard;

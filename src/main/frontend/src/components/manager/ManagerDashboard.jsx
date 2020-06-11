import { useTranslation } from 'react-i18next';
import { Redirect, Route, Switch } from 'react-router-dom';
import React from 'react';
import Dashboard from '../shared/Dashboard';
import { route } from '../../routing';
import AddFlight from './flights/AddFlight';
import FlightsList from './flights/FlightsList';
import AirportsList from './airports/AirportsList';
import AddAirport from './airports/AddAirport';

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
                    <Redirect to={route('manager.flights.list')} />
                </Route>
                <Route path={route('manager.airports.list')}>
                    <AirportsList />
                </Route>
                <Route path={route('manager.airports.add')}>
                    <AddAirport />
                </Route>
                <Route exact path={route('manager.flights')}>
                    <Redirect to={route('manager.flights.list')} />
                </Route>
                <Route path={route('manager.flights.list')}>
                    <FlightsList />
                </Route>
                <Route path={route('manager.flights.add')}>
                    <AddFlight />
                </Route>
            </Switch>
        </Dashboard>
    );
};

export default ManagerDashboard;

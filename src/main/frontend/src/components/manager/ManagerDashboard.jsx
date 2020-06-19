import { useTranslation } from 'react-i18next';
import { Redirect, Route, Switch } from 'react-router-dom';
import React from 'react';
import Dashboard from '../shared/Dashboard';
import { route } from '../../routing';
import AddFlight from './flights/AddFlight';
import FlightsList from './flights/FlightsList';
import AddConnection from './connections/AddConnection';
import ConnectionsList from './connections/ConnectionsList';
import AddPlane from './planes/AddPlane';
import PlanesList from './planes/PlanesList';
import SeatClassesList from './seatClasses/SeatClassesList';
import AddSeatClass from './seatClasses/AddSeatClass';
import AirportsList from './airports/AirportsList';
import AddAirport from './airports/AddAirport';
import EditSeatClass from './seatClasses/EditSeatClass';
import EditAirport from './airports/EditAirport';
import EditPlane from './planes/EditPlane';
import EditFlight from './flights/EditFlight';
import EditConnection from './connections/EditConnection';

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
                    route: 'manager.connections.list',
                },
                {
                    name: t('Add relation'),
                    route: 'manager.connections.add',
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
        {
            header: t('Seat classes'),
            items: [
                {
                    name: t('List seat classes'),
                    route: 'manager.seatClasses.list',
                },
                {
                    name: t('Add seat class'),
                    route: 'manager.seatClasses.add',
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
                <Route path={route('manager.airports.airport.edit')}>
                    <EditAirport />
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
                <Route path={route('manager.flights.flight.edit')}>
                    <EditFlight />
                </Route>
                <Route path={route('manager.flights.list')}>
                    <FlightsList />
                </Route>
                <Route path={route('manager.flights.add')}>
                    <AddFlight />
                </Route>
                <Route path={route('manager.connections.list')}>
                    <ConnectionsList />
                </Route>
                <Route path={route('manager.connections.add')}>
                    <AddConnection />
                </Route>
                <Route path={route('manager.connections.connection.edit')}>
                    <EditConnection />
                </Route>
                <Route path={route('manager.planes.add')}>
                    <AddPlane />
                </Route>
                <Route path={route('manager.planes.plane.edit')}>
                    <EditPlane />
                </Route>
                <Route path={route('manager.planes.list')}>
                    <PlanesList />
                </Route>
                <Route exact path={route('manager.seatClasses')}>
                    <Redirect to={route('manager.seatClasses.list')} />
                </Route>
                <Route path={route('manager.seatClasses.list')}>
                    <SeatClassesList />
                </Route>
                <Route path={route('manager.seatClasses.add')}>
                    <AddSeatClass />
                </Route>
                <Route path={route('manager.seatClasses.seatClass.edit')}>
                    <EditSeatClass />
                </Route>
            </Switch>
        </Dashboard>
    );
};

export default ManagerDashboard;

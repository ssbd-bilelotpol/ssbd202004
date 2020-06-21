import React from 'react';
import { Route, Switch, useRouteMatch } from 'react-router-dom';
import ClientDashboard from './ClientDashboard';
import { route } from '../../routing';
import MainPage from './MainPage';
import PurchaseTickets from './purchase/PurchaseTickets';
import PurchaseSuccess from './purchase/PurchaseSuccess';
import ProtectedRoute from '../ProtectedRoute';
import { roles } from '../../constants';

const ClientApp = () => {
    const { path } = useRouteMatch();
    return (
        <>
            <Switch>
                <ProtectedRoute role={roles.client} path={route('flights.purchase.success')}>
                    <PurchaseSuccess />
                </ProtectedRoute>
                <ProtectedRoute role={roles.client} path={route('flights.purchase')}>
                    <PurchaseTickets />
                </ProtectedRoute>
                <ProtectedRoute role={roles.client} path={route('panel')}>
                    <ClientDashboard />
                </ProtectedRoute>
                <Route path={path}>
                    <MainPage />
                </Route>
            </Switch>
        </>
    );
};

export default ClientApp;

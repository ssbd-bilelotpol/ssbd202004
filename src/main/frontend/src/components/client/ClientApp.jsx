import React from 'react';
import { Route, Switch, useRouteMatch } from 'react-router-dom';
import ClientDashboard from './ClientDashboard';
import { route } from '../../routing';
import MainPage from './MainPage';
import PurchaseTickets from './purchase/PurchaseTickets';
import PurchaseSuccess from './purchase/PurchaseSuccess';

const ClientApp = () => {
    const { path } = useRouteMatch();
    return (
        <>
            <Switch>
                <Route path={route('flights.purchase.success')}>
                    <PurchaseSuccess />
                </Route>
                <Route path={route('flights.purchase')}>
                    <PurchaseTickets />
                </Route>
                <Route path={path}>
                    <MainPage />
                </Route>
                <Route path={route('panel')}>
                    <ClientDashboard />
                </Route>
            </Switch>
        </>
    );
};

export default ClientApp;

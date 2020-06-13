import React from 'react';
import { Route, Switch, useRouteMatch } from 'react-router-dom';
import ClientDashboard from './ClientDashboard';
import { route } from '../../routing';
import MainPage from './MainPage';

const ClientApp = () => {
    const { path } = useRouteMatch();
    return (
        <>
            <Switch>
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

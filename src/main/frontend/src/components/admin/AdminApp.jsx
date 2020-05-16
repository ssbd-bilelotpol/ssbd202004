import React from 'react';
import { Route, Switch, useRouteMatch } from 'react-router-dom';
import { Container } from 'semantic-ui-react';
import TopMenu from '../TopMenu';
import AdminDashboard from './AdminDashboard';
import { Breadcrumbs } from '../Breadcrumbs';
import { route } from '../../routing';

const AdminApp = () => {
    const { path } = useRouteMatch();
    return (
        <>
            <Switch>
                <Route path={path}>
                    <TopMenu backgroundColor="#b52738" />
                </Route>
            </Switch>

            <Container>
                <Breadcrumbs />
                <Switch>
                    <Route path={route('admin')}>
                        <AdminDashboard />
                    </Route>
                </Switch>
            </Container>
        </>
    );
};

export default AdminApp;

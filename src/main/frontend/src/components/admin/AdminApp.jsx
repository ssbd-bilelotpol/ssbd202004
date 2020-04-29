import React from 'react';
import { Route, Switch, useRouteMatch } from 'react-router-dom';
import { Container } from 'semantic-ui-react';
import TopMenu from '../TopMenu';
import { urls } from '../../constants';
import AdminDashboard from './AdminDashboard';

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
                <Switch>
                    <Route path={urls.roles.admin}>
                        <AdminDashboard />
                    </Route>
                </Switch>
            </Container>
        </>
    );
};

export default AdminApp;

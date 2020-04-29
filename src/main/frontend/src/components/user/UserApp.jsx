import React from 'react';
import { Container } from 'semantic-ui-react';
import { Route, Switch, useRouteMatch } from 'react-router-dom';
import { useSelector } from 'react-redux';
import TopMenu from '../TopMenu';
import UserDashboard from './UserDashboard';
import { roleColors } from '../../constants';

const UserApp = () => {
    const { path } = useRouteMatch();
    const role = useSelector((state) => state.auth.user.role);
    return (
        <>
            <Switch>
                <Route path={path}>
                    <TopMenu
                        clouds={roleColors[role] ? '' : 'center'}
                        backgroundColor={roleColors[role]}
                    />
                </Route>
            </Switch>

            <Container>
                <Switch>
                    <Route path={path}>
                        <UserDashboard />
                    </Route>
                </Switch>
            </Container>
        </>
    );
};

export default UserApp;

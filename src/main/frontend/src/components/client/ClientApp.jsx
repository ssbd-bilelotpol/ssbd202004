import React from 'react';
import { Icon, Menu, Container } from 'semantic-ui-react';
import { NavLink, Route, Switch, useRouteMatch } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import TopMenu from '../TopMenu';
import SearchFlight from './SearchFlight';
import { urls } from '../../constants';
import ClientDashboard from './ClientDashboard';

const MenuItems = () => {
    const { t } = useTranslation();
    return (
        <>
            <Menu.Item as="a">
                <Icon name="plane" />
                {t('Schedule')}
            </Menu.Item>
            <Menu.Item as={NavLink} to={urls.pages.panel.reservations}>
                <Icon name="calendar" />
                {t('My reservations')}
            </Menu.Item>
        </>
    );
};

const ClientApp = () => {
    const { path } = useRouteMatch();
    return (
        <>
            <Switch>
                <Route exact path={path}>
                    <TopMenu clouds="bottom" menuItems={MenuItems}>
                        <SearchFlight />
                    </TopMenu>
                </Route>
                <Route path={path}>
                    <TopMenu clouds="center" menuItems={MenuItems} />
                </Route>
            </Switch>

            <Container>
                <Switch>
                    <Route path={urls.pages.panel.root}>
                        <ClientDashboard />
                    </Route>
                </Switch>
            </Container>
        </>
    );
};

export default ClientApp;

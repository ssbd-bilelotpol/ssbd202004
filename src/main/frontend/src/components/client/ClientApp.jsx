import React from 'react';
import { Icon, Menu, Container } from 'semantic-ui-react';
import { Route, Switch, useRouteMatch } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import Confirm from './Confirm';
import TopMenu from '../TopMenu';
import SearchFlight from './SearchFlight';

const MenuItems = () => {
    const { t } = useTranslation();
    return (
        <>
            <Menu.Item as="a" active>
                <Icon name="plane" />
                {t('Schedule')}
            </Menu.Item>
            <Menu.Item as="a">
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
                    <Route path={`${path}confirm/:token`}>
                        <Confirm />
                    </Route>
                </Switch>
            </Container>
        </>
    );
};

export default ClientApp;

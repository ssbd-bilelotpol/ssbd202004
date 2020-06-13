import React from 'react';
import { NavLink, Redirect, Route, Switch } from 'react-router-dom';
import { Grid, Menu, Container, Card, Icon } from 'semantic-ui-react';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';
import { route } from '../../routing';
import TopMenu from '../TopMenu';

const DashboardContainer = styled(Container)`
    &&& {
        margin-top: 25px;
    }
`;

const DashboardMenu = styled(Menu)`
    &&& {
        width: initial;
    }
`;

const ContentCard = styled(Card)`
    &&& {
        padding: 12px;
    }
`;

const MenuItems = () => {
    const { t } = useTranslation();
    return (
        <>
            <Menu.Item as="a">
                <Icon name="plane" />
                {t('Schedule')}
            </Menu.Item>
            <Menu.Item as={NavLink} to={route('panel.reservations')}>
                <Icon name="calendar" />
                {t('My reservations')}
            </Menu.Item>
        </>
    );
};

const ClientDashboard = () => {
    const { t } = useTranslation();
    return (
        <>
            <TopMenu clouds="center" menuItems={MenuItems} />
            <Container>
                <DashboardContainer>
                    <Grid>
                        <Grid.Column width={3}>
                            <ContentCard>
                                <DashboardMenu secondary vertical pointing>
                                    <Menu.Item as={NavLink} to={route('panel.dashboard')}>
                                        {t('Dashboard')}
                                    </Menu.Item>
                                    <Menu.Item as={NavLink} to={route('panel.reservations')}>
                                        {t('My reservations')}
                                    </Menu.Item>
                                </DashboardMenu>
                            </ContentCard>
                        </Grid.Column>
                        <Grid.Column width={13}>
                            <ContentCard fluid>
                                <Switch>
                                    <Route path={route('panel.reservations')}>TODO</Route>

                                    <Route exact>
                                        <Redirect to={route('panel.dashboard')} />
                                    </Route>
                                </Switch>
                            </ContentCard>
                        </Grid.Column>
                    </Grid>
                </DashboardContainer>
            </Container>
        </>
    );
};

export default ClientDashboard;

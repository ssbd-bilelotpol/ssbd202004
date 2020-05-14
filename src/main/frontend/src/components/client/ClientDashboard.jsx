import React from 'react';
import { NavLink, Redirect, Route, Switch } from 'react-router-dom';
import { Grid, Menu, Container, Card } from 'semantic-ui-react';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';
import { urls } from '../../constants';

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

const ClientDashboard = () => {
    const { t } = useTranslation();
    return (
        <DashboardContainer>
            <Grid>
                <Grid.Column width={3}>
                    <ContentCard>
                        <DashboardMenu secondary vertical pointing>
                            <Menu.Item as={NavLink} to={urls.pages.panel.dashboard}>
                                {t('Dashboard')}
                            </Menu.Item>
                            <Menu.Item as={NavLink} to={urls.pages.panel.reservations}>
                                {t('My reservations')}
                            </Menu.Item>
                        </DashboardMenu>
                    </ContentCard>
                </Grid.Column>
                <Grid.Column width={13}>
                    <ContentCard fluid>
                        <Switch>
                            <Route path={urls.pages.panel.reservations}>TODO</Route>

                            <Route exact>
                                <Redirect to={urls.pages.panel.dashboard} />
                            </Route>
                        </Switch>
                    </ContentCard>
                </Grid.Column>
            </Grid>
        </DashboardContainer>
    );
};

export default ClientDashboard;

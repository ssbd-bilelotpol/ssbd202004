import React from 'react';
import { NavLink, Redirect, Route, Switch } from 'react-router-dom';
import { Grid, Menu, Container, Card } from 'semantic-ui-react';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';
import { route } from '../../routing';
import TopMenu from '../TopMenu';
import ListTickets from './tickets/ListTickets';
import { Breadcrumbs } from '../Breadcrumbs';
import ViewTicket from '../shared/ViewTicket';

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
        <>
            <TopMenu clouds="center" />
            <Container>
                <Breadcrumbs />
                <DashboardContainer>
                    <Grid>
                        <Grid.Column width={3}>
                            <ContentCard>
                                <DashboardMenu secondary vertical pointing>
                                    <Menu.Item as={NavLink} to={route('panel.tickets')}>
                                        {t('My tickets')}
                                    </Menu.Item>
                                </DashboardMenu>
                            </ContentCard>
                        </Grid.Column>
                        <Grid.Column width={13}>
                            <ContentCard fluid>
                                <Switch>
                                    <Route path={route('panel.tickets.view')}>
                                        <ViewTicket />
                                    </Route>
                                    <Route path={route('panel.tickets')}>
                                        <ListTickets />
                                    </Route>

                                    <Route exact>
                                        <Redirect to={route('panel.tickets')} />
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

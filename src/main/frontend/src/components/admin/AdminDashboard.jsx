import React from 'react';
import { Route, Switch, NavLink } from 'react-router-dom';
import { Card, Container, Menu, Grid } from 'semantic-ui-react';
import styled from 'styled-components';
import { urls } from '../../constants';
import FilterableUsersTable from './UsersList';
import EditAccount from './EditAccount';

const DashboardContainer = styled(Container)`
    &&& {
        margin-top: 25px;
    }
`;

const ContentCard = styled(Card)`
    &&& {
        padding: 12px;
    }

    &&& > .card {
        border-radius: 0.28571429rem 0.28571429rem 0 0 !important;
    }
`;

const GappedColumn = styled(Grid.Column)`
    &&&.column {
        padding-left: 1.5rem;
    }
`;

const ShadowMenu = styled(Menu)`
    &&& {
        border: none;
        box-shadow: 0 1px 3px 0 #d4d4d5, 0 0 0 1px #d4d4d5;
    }
`;
const AdminDashboard = () => {
    return (
        <DashboardContainer>
            <Grid>
                <Grid.Column width={3}>
                    <ShadowMenu vertical>
                        <Menu.Item>
                            <Menu.Header>Accounts</Menu.Header>
                            <Menu.Menu>
                                <Menu.Item
                                    name="List accounts"
                                    as={NavLink}
                                    to={urls.pages.admin.accounts.list}
                                />
                            </Menu.Menu>
                        </Menu.Item>

                        <Menu.Item>
                            <Menu.Header>Reservations</Menu.Header>
                            <Menu.Menu>
                                <Menu.Item name="List reservations" />
                                <Menu.Item name="Generate raport" />
                            </Menu.Menu>
                        </Menu.Item>

                        <Menu.Item>
                            <Menu.Header>Airports</Menu.Header>
                            <Menu.Menu>
                                <Menu.Item name="List airports" />
                                <Menu.Item name="Add airport" />
                            </Menu.Menu>
                        </Menu.Item>

                        <Menu.Item>
                            <Menu.Header>Relations</Menu.Header>
                            <Menu.Menu>
                                <Menu.Item name="List relations" />
                                <Menu.Item name="Add relation" />
                            </Menu.Menu>
                        </Menu.Item>

                        <Menu.Item>
                            <Menu.Header>Flights</Menu.Header>
                            <Menu.Menu>
                                <Menu.Item name="List flights" />
                                <Menu.Item name="Add flight" />
                            </Menu.Menu>
                        </Menu.Item>
                        <Menu.Item>
                            <Menu.Header>Planes</Menu.Header>
                            <Menu.Menu>
                                <Menu.Item name="List planes" />
                                <Menu.Item name="Add plane" />
                            </Menu.Menu>
                        </Menu.Item>
                    </ShadowMenu>
                </Grid.Column>
                <GappedColumn width={13}>
                    <Switch>
                        <Route path={urls.pages.admin.accounts.edit}>
                            <EditAccount />
                        </Route>
                        <Route path={urls.pages.admin.accounts.list}>
                            <ContentCard fluid>
                                <FilterableUsersTable />
                            </ContentCard>
                        </Route>
                    </Switch>
                </GappedColumn>
            </Grid>
        </DashboardContainer>
    );
};

export default AdminDashboard;

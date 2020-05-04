import React from 'react';
import { Route, Switch, NavLink } from 'react-router-dom';
import { Card, Container, Menu, Grid } from 'semantic-ui-react';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';
import { urls } from '../../constants';
import FilterableUsersTable from './UsersList';
import EditAccount from './EditAccount';
import AccountAuthReport from './AccountAuthReport';

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
    const { t } = useTranslation();
    return (
        <DashboardContainer>
            <Grid>
                <Grid.Column width={3}>
                    <ShadowMenu vertical>
                        <Menu.Item>
                            <Menu.Header>Accounts</Menu.Header>
                            <Menu.Menu>
                                <Menu.Item
                                    name={t('List accounts')}
                                    as={NavLink}
                                    to={urls.pages.admin.accounts.list}
                                />
                                <Menu.Item
                                    name={t('Accounts auth report')}
                                    as={NavLink}
                                    to={urls.pages.admin.accounts.authReport}
                                />
                            </Menu.Menu>
                        </Menu.Item>

                        <Menu.Item>
                            <Menu.Header>Reservations</Menu.Header>
                            <Menu.Menu>
                                <Menu.Item name={t('List reservations')} />
                                <Menu.Item name={t('Generate report')} />
                            </Menu.Menu>
                        </Menu.Item>

                        <Menu.Item>
                            <Menu.Header>Airports</Menu.Header>
                            <Menu.Menu>
                                <Menu.Item name={t('List airports')} />
                                <Menu.Item name={t('Add airport')} />
                            </Menu.Menu>
                        </Menu.Item>

                        <Menu.Item>
                            <Menu.Header>Relations</Menu.Header>
                            <Menu.Menu>
                                <Menu.Item name={t('List relations')} />
                                <Menu.Item name={t('Add relation')} />
                            </Menu.Menu>
                        </Menu.Item>

                        <Menu.Item>
                            <Menu.Header>Flights</Menu.Header>
                            <Menu.Menu>
                                <Menu.Item name={t('List flights')} />
                                <Menu.Item name={t('Add flight')} />
                            </Menu.Menu>
                        </Menu.Item>
                        <Menu.Item>
                            <Menu.Header>Planes</Menu.Header>
                            <Menu.Menu>
                                <Menu.Item name={t('List planes')} />
                                <Menu.Item name={t('Add plane')} />
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
                        <Route path={urls.pages.admin.accounts.authReport}>
                            <ContentCard fluid>
                                <AccountAuthReport />
                            </ContentCard>
                        </Route>
                    </Switch>
                </GappedColumn>
            </Grid>
        </DashboardContainer>
    );
};

export default AdminDashboard;

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
                            <Menu.Header>{t('Accounts')}</Menu.Header>
                            <Menu.Menu>
                                <Menu.Item as={NavLink} to={urls.pages.admin.accounts.list}>
                                    {t('List accounts')}
                                </Menu.Item>
                                <Menu.Item as={NavLink} to={urls.pages.admin.accounts.authReport}>
                                    {t('Accounts auth report')}
                                </Menu.Item>
                            </Menu.Menu>
                        </Menu.Item>

                        <Menu.Item>
                            <Menu.Header>{t('Reservations')}</Menu.Header>
                            <Menu.Menu>
                                <Menu.Item>{t('List reservations')}</Menu.Item>
                                <Menu.Item>{t('Generate report')}</Menu.Item>
                            </Menu.Menu>
                        </Menu.Item>

                        <Menu.Item>
                            <Menu.Header>{t('Airports')}</Menu.Header>
                            <Menu.Menu>
                                <Menu.Item>{t('List airports')}</Menu.Item>
                                <Menu.Item>{t('Add airport')}</Menu.Item>
                            </Menu.Menu>
                        </Menu.Item>

                        <Menu.Item>
                            <Menu.Header>{t('Relations')}</Menu.Header>
                            <Menu.Menu>
                                <Menu.Item>{t('List relations')}</Menu.Item>
                                <Menu.Item>{t('Add relation')}</Menu.Item>
                            </Menu.Menu>
                        </Menu.Item>

                        <Menu.Item>
                            <Menu.Header>{t('Flights')}</Menu.Header>
                            <Menu.Menu>
                                <Menu.Item>{t('List flights')}</Menu.Item>
                                <Menu.Item>{t('Add flight')}</Menu.Item>
                            </Menu.Menu>
                        </Menu.Item>

                        <Menu.Item>
                            <Menu.Header>{t('Planes')}</Menu.Header>
                            <Menu.Menu>
                                <Menu.Item>{t('List planes')}</Menu.Item>
                                <Menu.Item>{t('Add plane')}</Menu.Item>
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

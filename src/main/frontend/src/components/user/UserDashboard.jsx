import React from 'react';
import { NavLink, Redirect, Route, Switch } from 'react-router-dom';
import { Grid, Menu, Container, Card } from 'semantic-ui-react';
import styled from 'styled-components';
import { GoogleReCaptchaProvider } from 'react-google-recaptcha-v3';
import { urls } from '../../constants';
import Settings from './Settings';
import ChangePassword from './ChangePassword';

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

const UserDashboard = () => {
    return (
        <DashboardContainer>
            <Grid>
                <Grid.Column width={3}>
                    <ContentCard>
                        <DashboardMenu secondary vertical pointing>
                            <Menu.Item as={NavLink} exact to={urls.pages.user.settings.root}>
                                Settings
                            </Menu.Item>
                            <Menu.Item as={NavLink} to={urls.pages.user.settings.changePassword}>
                                Change Password
                            </Menu.Item>
                        </DashboardMenu>
                    </ContentCard>
                </Grid.Column>
                <Grid.Column width={13}>
                    <ContentCard fluid>
                        <GoogleReCaptchaProvider
                            reCaptchaKey={process.env.REACT_APP_GOOGLE_RECAPTCHA_SITE_KEY}
                        >
                            <Switch>
                                <Route exact path={urls.pages.user.settings.root}>
                                    <Settings />
                                </Route>

                                <Route path={urls.pages.user.settings.changePassword}>
                                    <ChangePassword />
                                </Route>

                                <Route exact>
                                    <Redirect to={urls.pages.user.settings.root} />
                                </Route>
                            </Switch>
                        </GoogleReCaptchaProvider>
                    </ContentCard>
                </Grid.Column>
            </Grid>
        </DashboardContainer>
    );
};

export default UserDashboard;

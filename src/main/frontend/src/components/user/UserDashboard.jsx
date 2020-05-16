import React from 'react';
import { NavLink, Redirect, Route, Switch } from 'react-router-dom';
import { Grid, Menu, Container, Card } from 'semantic-ui-react';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';
import { GoogleReCaptchaProvider } from 'react-google-recaptcha-v3';
import Settings from './Settings';
import ChangePassword from './ChangePassword';
import { route } from '../../routing';

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
    const { t } = useTranslation();
    return (
        <DashboardContainer>
            <Grid>
                <Grid.Column width={3}>
                    <ContentCard>
                        <DashboardMenu secondary vertical pointing>
                            <Menu.Item as={NavLink} exact to={route('user.settings')}>
                                {t('Settings')}
                            </Menu.Item>
                            <Menu.Item as={NavLink} to={route('user.settings.changePassword')}>
                                {t('Change Password')}
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
                                <Route exact path={route('user.settings')}>
                                    <Settings />
                                </Route>

                                <Route path={route('user.settings.changePassword')}>
                                    <ChangePassword />
                                </Route>

                                <Route exact>
                                    <Redirect to={route('user.settings')} />
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

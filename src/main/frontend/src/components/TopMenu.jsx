import React from 'react';
import { Button, Container, Dropdown, Icon, Image, Menu, Segment } from 'semantic-ui-react';
import styled, { css } from 'styled-components';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useHistory } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { changeRoleAction, logoutAction } from '../actions/auth';
import { urls } from '../constants';
import Login from './login/Login';
import Register from './login/Register';
import LastAuth from './login/LastAuth';

const HeaderSegment = styled(({ clouds, backgroundColor, ...rest }) => <Segment {...rest} />)`
    &&& {
        padding-top: 0;
        padding-bottom: 0;
        ${(props) =>
            props.clouds
                ? css`
                      background: url(${process.env.PUBLIC_URL}/clouds.jpg);
                      background-size: cover;
                      background-position-y: ${props.clouds};
                      box-shadow: inset 0px -20px 50px -30px rgba(0, 0, 0, 0.32);
                  `
                : css`
                      background: ${(props) => props.backgroundColor};
                      padding-bottom: 0;
                  `}
    }
`;

const BorderlessMenu = styled(Menu)`
    &&&.menu {
        border: none;
    }

    &&&.menu .item {
        align-self: center;
    }

    &&&.menu .item .image {
        margin-top: -7px;
    }
`;

const InactiveMenuItem = styled(Menu.Item)`
    &&& .item.active {
        border-color: transparent !important;
        font-weight: 400 !important;
    }
`;

const TopMenu = ({ backgroundColor, clouds, children, menuItems = () => {} }) => {
    const loggedIn = useSelector((state) => state.auth.loggedIn);
    const roles = useSelector((state) => state.auth.user.roles);
    const role = useSelector((state) => state.auth.user.role);
    const principal = useSelector((state) => state.auth.user.principal);
    const dispatch = useDispatch();
    const history = useHistory();

    const { t } = useTranslation();

    const roleOptions =
        roles &&
        roles.length > 1 &&
        roles.map((r) => ({
            key: r,
            value: r,
            text: t(r),
        }));

    const handleRoleChange = (e, { value }) => {
        dispatch(changeRoleAction(value));
        history.push(urls.roles[value]);
    };

    const handleLogout = () => {
        dispatch(logoutAction());
    };

    return (
        <>
            <HeaderSegment
                backgroundColor={backgroundColor}
                clouds={clouds}
                inverted
                textAlign="center"
                vertical
            >
                <BorderlessMenu borderless secondary pointing inverted size="large">
                    <Container>
                        <Menu.Item as={Link} to={urls.roles[role] || '/'}>
                            <Image size="small" src={`${process.env.PUBLIC_URL}/logo.svg`} />
                        </Menu.Item>
                        {menuItems()}
                        {loggedIn ? (
                            <InactiveMenuItem position="right">
                                {roles.length > 1 && (
                                    <Dropdown
                                        value={role}
                                        onChange={handleRoleChange}
                                        fluid
                                        trigger={
                                            <span>
                                                <Icon name="group" />
                                                {t(role)}
                                            </span>
                                        }
                                        options={roleOptions}
                                    />
                                )}
                                <Dropdown
                                    item
                                    trigger={
                                        <span>
                                            <Icon name="user" />
                                            {principal}
                                        </span>
                                    }
                                >
                                    <Dropdown.Menu>
                                        <Dropdown.Item as={Link} to={urls.pages.user.settings.root}>
                                            {t('Settings')}
                                        </Dropdown.Item>
                                        <Dropdown.Item onClick={handleLogout}>
                                            {t('Logout')}
                                        </Dropdown.Item>
                                    </Dropdown.Menu>
                                </Dropdown>
                                <LastAuth />
                            </InactiveMenuItem>
                        ) : (
                            <Menu.Item style={{ border: 'none' }} position="right" as="a">
                                <Register
                                    trigger={
                                        <Button basic inverted style={{ marginRight: '0.5em' }}>
                                            <Icon name="signup" />
                                            {t('Sign up')}
                                        </Button>
                                    }
                                />
                                <Login
                                    trigger={
                                        <Button basic inverted>
                                            <Icon name="user outline" />
                                            {t('Sign in')}
                                        </Button>
                                    }
                                />
                            </Menu.Item>
                        )}
                    </Container>
                </BorderlessMenu>
                {children}
            </HeaderSegment>
        </>
    );
};

export default TopMenu;

import styled from 'styled-components';
import { Card, Container, Grid, Menu } from 'semantic-ui-react';
import { NavLink } from 'react-router-dom';
import React from 'react';
import { route } from '../../routing';

export const ContentCard = styled(Card)`
    &&& {
        padding: 12px;
    }

    &&& > .card {
        border-radius: 0.28571429rem 0.28571429rem 0 0 !important;
    }
`;

const DashboardContainer = styled(Container)`
    &&& {
        margin-top: 25px;
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

const Dashboard = ({ menuItems, children }) => (
    <DashboardContainer>
        <Grid>
            <Grid.Column width={3}>
                <ShadowMenu vertical>
                    {menuItems.map((section) => (
                        <Menu.Item key={section.header}>
                            <Menu.Header>{section.header}</Menu.Header>
                            <Menu.Menu>
                                {section.items.map((item) => (
                                    <Menu.Item
                                        name={item.name}
                                        as={NavLink}
                                        to={route(item.route)}
                                        key={item.name}
                                    >
                                        {item.name}
                                    </Menu.Item>
                                ))}
                            </Menu.Menu>
                        </Menu.Item>
                    ))}
                </ShadowMenu>
            </Grid.Column>
            <GappedColumn width={13}>{children}</GappedColumn>
        </Grid>
    </DashboardContainer>
);

export default Dashboard;

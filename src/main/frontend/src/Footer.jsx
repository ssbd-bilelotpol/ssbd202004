import { Container, Header, Segment } from 'semantic-ui-react';
import React from 'react';
import styled from 'styled-components';
import { useSelector } from 'react-redux';
import { roleColors } from './constants';

export const BottomSegment = styled(Segment)`
    height: 42px;
    padding: 1em 0em;
`;

const Footer = () => {
    const role = useSelector((state) => state.auth.user.role);
    return (
        <BottomSegment inverted vertical style={{ backgroundColor: roleColors[role] }}>
            <Container>
                <Header as="h4" inverted>
                    Bilelotpol &copy; 2020
                </Header>
            </Container>
        </BottomSegment>
    );
};

export default Footer;

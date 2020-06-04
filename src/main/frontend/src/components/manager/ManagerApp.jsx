import React from 'react';
import { Container } from 'semantic-ui-react';
import TopMenu from '../TopMenu';
import { Breadcrumbs } from '../Breadcrumbs';
import ManagerDashboard from './ManagerDashboard';

const ManagerApp = () => {
    return (
        <>
            <TopMenu backgroundColor="#2e9ba8" />
            <Container>
                <Breadcrumbs />
                <ManagerDashboard />
            </Container>
        </>
    );
};

export default ManagerApp;

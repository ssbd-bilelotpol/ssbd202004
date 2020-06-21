import React from 'react';
import { Container } from 'semantic-ui-react';
import TopMenu from '../TopMenu';
import { Breadcrumbs } from '../Breadcrumbs';
import CustomerServiceDashboard from './CustomerServiceDashboard';

const CustomerServiceApp = () => {
    return (
        <>
            <TopMenu />
            <Container>
                <Breadcrumbs />
                <CustomerServiceDashboard />
            </Container>
        </>
    );
};

export default CustomerServiceApp;

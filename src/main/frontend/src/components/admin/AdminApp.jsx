import React from 'react';
import { Container } from 'semantic-ui-react';
import TopMenu from '../TopMenu';
import AdminDashboard from './AdminDashboard';
import { Breadcrumbs } from '../Breadcrumbs';

const AdminApp = () => {
    return (
        <>
            <TopMenu />
            <Container>
                <Breadcrumbs />
                <AdminDashboard />
            </Container>
        </>
    );
};

export default AdminApp;

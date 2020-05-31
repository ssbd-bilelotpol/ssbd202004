import React from 'react';
import { Container } from 'semantic-ui-react';
import TopMenu from '../TopMenu';
import AdminDashboard from './AdminDashboard';
import { Breadcrumbs } from '../Breadcrumbs';

const AdminApp = () => {
    return (
        <>
            <TopMenu backgroundColor="#b52738" />
            <Container>
                <Breadcrumbs />
                <AdminDashboard />
            </Container>
        </>
    );
};

export default AdminApp;

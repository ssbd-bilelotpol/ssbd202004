import React from 'react';
import { useTranslation } from 'react-i18next';
import { Switch, Redirect, Route } from 'react-router-dom';
import Dashboard from '../shared/Dashboard';
import { route } from '../../routing';
import GenerateReport from './reports/GenerateReport';
import TicketsList from './TicketsList';
import ViewTicket from '../shared/ViewTicket';
import { WideCard } from '../shared/Flight';
import EditTicket from './tickets/EditTicket';
import ClientsList from './ClientsList';

const CustomerServiceDashboard = () => {
    const { t } = useTranslation();
    const menuItems = [
        {
            header: t('Reports'),
            items: [
                {
                    name: t('Generate report'),
                    route: 'customer_service.reports.generate',
                },
            ],
        },
        {
            header: t('Tickets'),
            items: [
                {
                    name: t('Search for tickets'),
                    route: 'customer_service.tickets.list',
                },
            ],
        },
        {
            header: t('Clients'),
            items: [
                {
                    name: t('Search for clients'),
                    route: 'customer_service.clients.list',
                },
            ],
        },
    ];

    return (
        <Dashboard menuItems={menuItems}>
            <Switch>
                <Route exact path={route('customer_service')}>
                    <Redirect to={route('customer_service.reports.generate')} />
                </Route>
                <Route path={route('customer_service.tickets.list')}>
                    <TicketsList />
                </Route>
                <Route path={route('customer_service.tickets.view.edit')}>
                    <EditTicket />
                </Route>
                <Route path={route('customer_service.tickets.view')}>
                    <WideCard>
                        <ViewTicket editable />
                    </WideCard>
                </Route>
                <Route path={route('customer_service.reports.generate')}>
                    <GenerateReport />
                </Route>
                <Route path={route('customer_service.clients.list')}>
                    <ClientsList />
                </Route>
            </Switch>
        </Dashboard>
    );
};

export default CustomerServiceDashboard;

import React from 'react';
import { useTranslation } from 'react-i18next';
import { Switch, Redirect, Route } from 'react-router-dom';
import Dashboard from '../shared/Dashboard';
import { route } from '../../routing';
import GenerateReport from './reports/GenerateReport';

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
    ];

    return (
        <Dashboard menuItems={menuItems}>
            <Switch>
                <Route exact path={route('customer_service')}>
                    <Redirect to={route('customer_service.reports.generate')} />
                </Route>
                <Route path={route('customer_service.reports.generate')}>
                    <GenerateReport />
                </Route>
            </Switch>
        </Dashboard>
    );
};

export default CustomerServiceDashboard;

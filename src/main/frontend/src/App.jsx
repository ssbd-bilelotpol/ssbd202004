import React from 'react';
import { Route, Switch } from 'react-router-dom';
import { roles, urls } from './constants';
import ProtectedRoute from './components/ProtectedRoute';
import AdminApp from './components/admin/AdminApp';
import ManagerApp from './components/manager/ManagerApp';
import CustomerServiceApp from './components/customerService/CustomerServiceApp';
import ClientApp from './components/client/ClientApp';
import UserApp from './components/user/UserApp';
import Confirm from './components/client/Confirm';

const App = () => {
    return (
        <>
            <Switch>
                <Route path={urls.pages.confirm}>
                    <Confirm />
                </Route>
                <ProtectedRoute role={roles.admin} path={urls.roles[roles.admin]}>
                    <AdminApp />
                </ProtectedRoute>
                <ProtectedRoute role={roles.manager} path={urls.roles[roles.manager]}>
                    <ManagerApp />
                </ProtectedRoute>
                <ProtectedRoute
                    role={roles.customerService}
                    path={urls.roles[roles.customerService]}
                >
                    <CustomerServiceApp />
                </ProtectedRoute>
                <ProtectedRoute condition={(role) => role} path={urls.pages.user.root}>
                    <UserApp />
                </ProtectedRoute>
                <ProtectedRoute
                    condition={(role) => !role || role === roles.client}
                    path={urls.roles[roles.client]}
                >
                    <ClientApp />
                </ProtectedRoute>
            </Switch>
        </>
    );
};

export default App;

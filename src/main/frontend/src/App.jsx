import React from 'react';
import { Route, Switch } from 'react-router-dom';
import { roles } from './constants';
import ProtectedRoute from './components/ProtectedRoute';
import AdminApp from './components/admin/AdminApp';
import ManagerApp from './components/manager/ManagerApp';
import CustomerServiceApp from './components/customerService/CustomerServiceApp';
import ClientApp from './components/client/ClientApp';
import UserApp from './components/user/UserApp';
import Confirm from './components/client/Confirm';
import PasswordReset from './components/client/settings/PasswordReset';
import { route } from './routing';
import { useTitle } from './components/Title';
import Footer from './Footer';
import { PageContent } from './components/shared/SimpleComponents';

const App = () => {
    useTitle();

    return (
        <>
            <PageContent>
                <Switch>
                    <Route path={route('confirm')}>
                        <Confirm />
                    </Route>
                    <Route path={route('resetPassword')}>
                        <PasswordReset />
                    </Route>
                    <ProtectedRoute role={roles.admin} path={route(roles.admin)}>
                        <AdminApp />
                    </ProtectedRoute>
                    <ProtectedRoute role={roles.manager} path={route(roles.manager)}>
                        <ManagerApp />
                    </ProtectedRoute>
                    <ProtectedRoute
                        role={roles.customerService}
                        path={route(roles.customerService)}
                    >
                        <CustomerServiceApp />
                    </ProtectedRoute>
                    <ProtectedRoute condition={(role) => role} path={route('user')}>
                        <UserApp />
                    </ProtectedRoute>
                    <ProtectedRoute
                        condition={(role) => !role || role === roles.client}
                        path={route(roles.client)}
                    >
                        <ClientApp />
                    </ProtectedRoute>
                </Switch>
            </PageContent>
            <Footer />
        </>
    );
};

export default App;

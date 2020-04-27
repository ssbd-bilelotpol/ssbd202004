import React from 'react';
import { Redirect, Route } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { urls } from '../constants';

const ProtectedRoute = ({
    condition = () => false,
    role,
    redirect = true,
    redirectTarget,
    children,
    ...rest
}) => {
    const currentRole = useSelector((state) => state.auth.user.role);
    if (role === currentRole || condition(currentRole)) {
        return <Route {...rest}>{children}</Route>;
    }

    if (redirect) {
        return <Redirect to={redirectTarget || urls.roles[currentRole] || '/'} />;
    }

    return null;
};

export default ProtectedRoute;

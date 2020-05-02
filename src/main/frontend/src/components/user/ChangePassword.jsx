import React from 'react';
import AccountChangePasswordForm from '../shared/AccountChangePasswordForm';
import { changeCurrentAccountPassword, useCurrentAccountDetails } from '../../api/profile';

const ChangePassword = () => {
    const { etag, refetch } = useCurrentAccountDetails();

    return (
        <AccountChangePasswordForm
            etag={etag}
            onSave={(values) => changeCurrentAccountPassword(values, etag)}
            onSuccess={refetch}
            showOldPasswordInput
        />
    );
};

export default ChangePassword;

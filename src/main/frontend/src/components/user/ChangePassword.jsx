import React from 'react';
import { useGoogleReCaptcha } from 'react-google-recaptcha-v3';
import AccountChangePasswordForm from '../shared/AccountChangePasswordForm';
import { changeCurrentAccountPassword, useCurrentAccountDetails } from '../../api/profiles';

const ChangePassword = () => {
    const { etag, refetch } = useCurrentAccountDetails();

    const { executeRecaptcha } = useGoogleReCaptcha();

    const onSave = async (values) => {
        const token = await executeRecaptcha('change_password');
        await changeCurrentAccountPassword({ ...values, captcha: token }, etag);
    };

    return <AccountChangePasswordForm onSave={onSave} onSuccess={refetch} showOldPasswordInput />;
};

export default ChangePassword;

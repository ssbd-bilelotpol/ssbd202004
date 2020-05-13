import React from 'react';
import { useTranslation } from 'react-i18next';
import { Message, Placeholder } from 'semantic-ui-react';
import { useGoogleReCaptcha } from 'react-google-recaptcha-v3';
import { editCurrentAccountDetails, useCurrentAccountDetails } from '../../api/profile';
import AccountEditForm from '../shared/AccountEditForm';

const Settings = () => {
    const { t } = useTranslation();
    const { data, etag, error, loading, refetch } = useCurrentAccountDetails();

    const { executeRecaptcha } = useGoogleReCaptcha();

    const onSave = async (values) => {
        const token = await executeRecaptcha('edit_account');
        await editCurrentAccountDetails({ ...values, captcha: token }, etag);
    };

    return (
        <>
            {!error ? (
                <AccountEditForm
                    data={data}
                    etag={etag}
                    loading={loading}
                    onSave={onSave}
                    onSuccess={refetch}
                />
            ) : (
                <Message negative content={t('Failed to retrieve data')} />
            )}
            {loading && (
                <Placeholder>
                    <Placeholder.Line />
                    <Placeholder.Line />
                    <Placeholder.Line />
                    <Placeholder.Line />
                    <Placeholder.Line />
                </Placeholder>
            )}
        </>
    );
};

export default Settings;

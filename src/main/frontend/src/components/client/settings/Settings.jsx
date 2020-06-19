import React from 'react';
import { useTranslation } from 'react-i18next';
import { Message, Placeholder } from 'semantic-ui-react';
import { editCurrentAccountDetails, useCurrentAccountDetails } from '../../../api/profiles';
import AccountEditForm from '../../shared/AccountEditForm';

const Settings = () => {
    const { t } = useTranslation();
    const { data, etag, error, loading, refetch } = useCurrentAccountDetails();

    return (
        <>
            {!error ? (
                <AccountEditForm
                    data={data}
                    etag={etag}
                    loading={loading}
                    onSave={(values) => editCurrentAccountDetails(values, etag)}
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

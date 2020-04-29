import React from 'react';
import { useParams } from 'react-router';
import { Message } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import { editAccountDetails, useAccountDetails } from '../../../api/profile';
import AccountEditForm from '../../shared/AccountEditForm';

const EditAccount = () => {
    const { t } = useTranslation();

    const { login } = useParams();
    const { data, etag, error, loading, refetch } = useAccountDetails(login);

    return (
        <>
            {!error ? (
                <AccountEditForm
                    data={data}
                    loading={loading}
                    onSave={(values) => editAccountDetails(login, values, etag)}
                    onSuccess={refetch}
                />
            ) : (
                <Message negative content={t('Failed to retrieve data')} />
            )}
        </>
    );
};

export default EditAccount;

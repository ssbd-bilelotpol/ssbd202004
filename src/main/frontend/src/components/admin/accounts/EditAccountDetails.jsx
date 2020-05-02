import React from 'react';
import { Label } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import { editAccountDetails } from '../../../api/profile';
import AccountEditForm from '../../shared/AccountEditForm';

const EditAccountDetails = ({ data, etag, loading, refetch, login }) => {
    const { t } = useTranslation();

    return (
        <>
            <Label attached="top">{t('Edit account details')}</Label>
            <AccountEditForm
                data={data}
                loading={loading}
                onSave={(values) => editAccountDetails(login, values, etag)}
                onSuccess={refetch}
            />
        </>
    );
};

export default EditAccountDetails;

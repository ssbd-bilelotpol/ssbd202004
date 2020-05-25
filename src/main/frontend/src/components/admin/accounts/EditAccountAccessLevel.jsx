import React, { useEffect } from 'react';
import { Label, Message, Placeholder } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import { editAccountAccessLevels, useAccountAccessLevels } from '../../../api/profile';
import AccountAccessLevelEditForm from './AccountAccessLevelEditForm';

const EditAccountAccessLevel = ({ etagAccountDetails, login, refetchAccountDetails }) => {
    const { t } = useTranslation();

    const { etag, data, error, loading, refetch } = useAccountAccessLevels(login);
    useEffect(() => {
        refetch();
    }, [etagAccountDetails, refetch]);

    return (
        <>
            <Label attached="top">{t('Edit account access level')}</Label>
            {!error ? (
                <AccountAccessLevelEditForm
                    data={data}
                    loading={loading}
                    onSave={(values) => editAccountAccessLevels(login, values, etag)}
                    onSuccess={refetchAccountDetails}
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
                    <Placeholder.Line />
                    <Placeholder.Line />
                </Placeholder>
            )}
        </>
    );
};

export default EditAccountAccessLevel;

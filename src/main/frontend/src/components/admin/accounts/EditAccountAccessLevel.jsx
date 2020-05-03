import React from 'react';
import { useParams } from 'react-router';
import { Message, Label, Placeholder } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import { editAccountAccessLevels, useAccountAccessLevels } from '../../../api/profile';
import AccountAccessLevelEditForm from './AccountAccessLevelEditForm';

const EditAccountAccessLevel = () => {
    const { t } = useTranslation();

    const { login } = useParams();
    const { data, etag, error, loading, refetch } = useAccountAccessLevels(login);

    return (
        <>
            <Label attached="top">{t('Edit account access level')}</Label>
            {!error ? (
                <AccountAccessLevelEditForm
                    data={data}
                    loading={loading}
                    onSave={(values) => editAccountAccessLevels(login, values, etag)}
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
                    <Placeholder.Line />
                    <Placeholder.Line />
                </Placeholder>
            )}
        </>
    );
};

export default EditAccountAccessLevel;

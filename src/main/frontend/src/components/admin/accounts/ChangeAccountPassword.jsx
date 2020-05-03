import React from 'react';
import { useTranslation } from 'react-i18next';
import { Label } from 'semantic-ui-react';
import { changeAccountPassword } from '../../../api/profile';
import AccountChangePasswordForm from '../../shared/AccountChangePasswordForm';

const ChangeAccountPassword = ({ etag, refetch, login, loading }) => {
    const { t } = useTranslation();

    return (
        <>
            <Label attached="top">{t("Change account's password")}</Label>
            <AccountChangePasswordForm
                etag={etag}
                onSave={(values) => changeAccountPassword(login, values, etag)}
                onSuccess={refetch}
                showOldPasswordInput={false}
                loading={loading}
            />
        </>
    );
};

export default ChangeAccountPassword;

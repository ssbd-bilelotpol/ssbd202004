import React from 'react';
import { useTranslation } from 'react-i18next';
import ConfirmSubmit from '../../controls/ConfirmSubmit';

const DeleteSeatClass = ({ loading, onSubmit }) => {
    const { t } = useTranslation();

    return (
        <>
            <ConfirmSubmit
                negative
                icon="remove"
                onSubmit={onSubmit}
                disabled={loading}
                loading={loading}
            >
                {t('Delete')}
            </ConfirmSubmit>
        </>
    );
};

export default DeleteSeatClass;

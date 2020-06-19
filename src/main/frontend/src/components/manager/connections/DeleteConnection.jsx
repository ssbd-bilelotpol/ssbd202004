import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import { useHistory } from 'react-router-dom';
import { route } from '../../../routing';
import ConfirmSubmit from '../../controls/ConfirmSubmit';
import { deleteConnection } from '../../../api/connections';

const DeleteConnection = ({ id, etag, setError }) => {
    const { t } = useTranslation();
    const makeCancellable = useCancellablePromise();
    const history = useHistory();
    const [loading, setLoading] = useState(false);

    const handleSubmit = async () => {
        setError();
        setLoading(true);
        try {
            await makeCancellable(deleteConnection(id, etag));
            history.push(route('manager.connections.list'));
        } catch (err) {
            setError(err);
            setLoading(false);
        }
    };

    return (
        <>
            <ConfirmSubmit
                negative
                icon="remove"
                onSubmit={handleSubmit}
                disabled={loading}
                loading={loading}
            >
                {t('Delete')}
            </ConfirmSubmit>
        </>
    );
};

export default DeleteConnection;

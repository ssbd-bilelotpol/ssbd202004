import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import { useHistory } from 'react-router-dom';
import { deleteAirport } from '../../../api/airports';
import { route } from '../../../routing';
import ConfirmSubmit from '../../controls/ConfirmSubmit';

const DeleteAirport = ({ code, etag, setError }) => {
    const { t } = useTranslation();
    const makeCancellable = useCancellablePromise();
    const history = useHistory();
    const [loading, setLoading] = useState(false);

    const handleSubmit = async () => {
        setError();
        setLoading(true);
        try {
            await makeCancellable(deleteAirport(code, etag));
            history.push(route('manager.airports.list'));
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

export default DeleteAirport;

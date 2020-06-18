import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import { useHistory } from 'react-router-dom';
import { deleteAirplaneSchema } from '../../../api/airplaneSchemas';
import { route } from '../../../routing';
import ConfirmSubmit from '../../controls/ConfirmSubmit';

const DeletePlane = ({ id, etag, setError }) => {
    const { t } = useTranslation();
    const makeCancellable = useCancellablePromise();
    const history = useHistory();
    const [loading, setLoading] = useState(false);
    const handleSubmit = async () => {
        setError();
        setLoading(true);
        try {
            await makeCancellable(deleteAirplaneSchema(id, etag));
            history.push(route('manager.planes.list'));
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

export default DeletePlane;

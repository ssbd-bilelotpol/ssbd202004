import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import ConfirmSubmit from '../../controls/ConfirmSubmit';
import { cancelFlight } from '../../../api/flights';

const CancelFlight = ({ code, etag, setError, refetch }) => {
    const { t } = useTranslation();
    const makeCancellable = useCancellablePromise();
    const [loading, setLoading] = useState(false);

    const handleSubmit = async () => {
        setError();
        setLoading(true);
        try {
            await makeCancellable(cancelFlight(code, etag));
            if (refetch) refetch();
        } catch (err) {
            setError(err);
            setLoading(false);
        }
    };

    return (
        <>
            <ConfirmSubmit
                negative
                icon="ban"
                onSubmit={handleSubmit}
                disabled={loading}
                loading={loading}
            >
                {t('Cancel flight')}
            </ConfirmSubmit>
        </>
    );
};

export default CancelFlight;

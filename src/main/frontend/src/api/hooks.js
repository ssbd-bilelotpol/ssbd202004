import { useCallback, useEffect, useState } from 'react';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import { get } from './index';

export const useGet = (url) => {
    const [data, setData] = useState(Object);
    const [error, setError] = useState();
    const [etag, setETag] = useState();
    const [loading, setLoading] = useState(true);
    const makeCancellable = useCancellablePromise();

    const fetchData = useCallback(async () => {
        try {
            const result = await makeCancellable(get(url));
            setData(result.content);
            setETag(result.etag);
        } catch (e) {
            setError(e);
        }
        setLoading(false);
    }, [url, makeCancellable]);

    useEffect(() => {
        fetchData();
    }, [url, fetchData]);

    return { data, etag, error, loading, refetch: fetchData };
};
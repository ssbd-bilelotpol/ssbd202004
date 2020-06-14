import { useCallback, useEffect, useState } from 'react';
import useCancellablePromise from '@rodw95/use-cancelable-promise';

const schemas = [
    {
        id: 123123,
        name: 'Airbus 3000',
        rows: 6,
        columns: 4,
    },
    {
        id: 18,
        name: 'Boeing 3000',
        rows: 10,
        columns: 6,
    },
];

let lastId = 123131;
export const createAirplaneSchema = async ({ name, plane }) => {
    await new Promise((resolve) => {
        setTimeout(() => {
            resolve();
        }, 1000);
    });
    lastId += 1;
    const airplaneSchema = {
        id: lastId,
        name,
        ...plane,
    };
    schemas.push(airplaneSchema);

    return airplaneSchema;
};

export const fetchAirplaneSchemasByName = async () => {
    await new Promise((resolve) => {
        setTimeout(() => {
            resolve();
        }, 1000);
    });
    return schemas;
};

const listAirplaneSchemas = async () => {
    await new Promise((resolve) => {
        setTimeout(() => {
            resolve();
        }, 1000);
    });
    return schemas;
};

export const useAirplaneSchemas = (filterData) => {
    const [data, setData] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);
    const makeCancellable = useCancellablePromise();

    const fetchData = useCallback(async () => {
        setLoading(true);
        setError(null);
        try {
            const result = await makeCancellable(listAirplaneSchemas(filterData));
            setData(result);
        } catch (e) {
            setError(e);
        }
        setLoading(false);
    }, [makeCancellable, filterData]);

    useEffect(() => {
        fetchData();
    }, [fetchData]);

    return { data, error, loading, refetch: fetchData };
};

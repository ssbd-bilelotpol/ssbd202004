import { useGet } from './hooks';

export const useClients = (filterData) => {
    const params = new URLSearchParams(filterData);
    Object.keys(filterData).forEach((key) => {
        if (filterData[key] == null || filterData[key] === '') {
            params.delete(key);
        }
    });
    const value = useGet(`/clients?${params.toString()}`, []);
    return {
        ...value,
    };
};

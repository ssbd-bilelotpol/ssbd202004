import { post } from './index';
import { useGet } from './hooks';

export const createConnection = (data) => post(`/connections`, data);

export const useListConnectionsByCodePhrase = (phrase) =>
    useGet(`/connections?phrase=${phrase}`, []);

export const useConnections = (filterData) => {
    const params = new URLSearchParams(filterData);
    Object.keys(filterData).forEach((key) => {
        if (filterData[key] == null || filterData[key] === '') {
            params.delete(key);
        }
    });
    const value = useGet(`/connections?${params.toString()}`, []);
    return {
        ...value,
    };
};

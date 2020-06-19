import { post, put } from './index';
import { useGet } from './hooks';

export const createConnection = (data) => post(`/connections`, data);

export const useConnections = (filterData) => {
    const params = new URLSearchParams(filterData);
    Object.keys(filterData).forEach((key) => {
        if (filterData[key] == null || filterData[key] === '') {
            params.delete(key);
        }
    });
    const value = useGet(`/connections?${params.toString()}`, []);
    console.log(value);
    return {
        ...value,
    };
};

export const useConnection = (id) => useGet(`/connections/${id}`);

export const updateConnection = (id, data, etag) => put(`/connections/${id}`, data, etag);

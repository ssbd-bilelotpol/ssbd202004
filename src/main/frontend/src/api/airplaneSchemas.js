import { post, put } from './index';
import { useGet } from './hooks';

export const createAirplaneSchema = async ({ name, plane }) =>
    post('/airplane-schemas', {
        ...plane,
        name,
    });

export const useAirplaneSchemas = (filterData) => {
    const params = new URLSearchParams(filterData);
    Object.keys(filterData || {}).forEach((key) => {
        if (filterData[key] === '') {
            params.delete(key);
        }
    });
    return useGet(`/airplane-schemas?${params.toString()}`, []);
};

export const useAirplaneSchema = (id) => useGet(`/airplane-schemas/${id}`);

export const updateAirplaneSchema = (id, data, etag) =>
    put(`/airplane-schemas/${id}`, { ...data.plane, name: data.name }, etag);

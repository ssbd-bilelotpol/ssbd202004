import { httpDelete, post, put } from './index';
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

export const deleteAirplaneSchema = (id, etag) => httpDelete(`/airplane-schemas/${id}`, etag);

export const useAirplaneSchema = (id) => useGet(`/airplane-schemas/${id}`, null);

export const updateAirplaneSchema = (id, data, etag) =>
    put(`/airplane-schemas/${id}`, { ...data.plane, name: data.name }, etag);

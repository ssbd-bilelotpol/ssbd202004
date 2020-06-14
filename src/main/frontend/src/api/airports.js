import { post, put } from './index';
import { useGet } from './hooks';

export const useFindAirports = (filterData) => {
    const params = new URLSearchParams(filterData);
    Object.keys(filterData || {}).forEach((key) => {
        if (filterData[key] === '') {
            params.delete(key);
        }
    });
    return useGet(`/airports?${params.toString()}`);
};

export const useGetCountries = () => useGet('/airports/countries', []);

export const addAirport = async (data) => post('/airports', data);

export const useAirport = (code) => useGet(`/airports/${code}`);

export const updateAirport = (code, data, etag) => put(`/airports/${code}`, data, etag);

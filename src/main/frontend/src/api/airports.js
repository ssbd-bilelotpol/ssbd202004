import { post, put, httpDelete } from './index';
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

export const useListAirportsByCodePhrase = (phrase) => useGet(`/airports/${phrase}`);

export const useGetCountries = () => useGet('/airports/countries', []);

export const addAirport = async (data) => post('/airports', data);

export const useAirport = (code) => useGet(`/airports/find/${code}`);

export const updateAirport = (code, data, etag) => put(`/airports/${code}`, data, etag);

export const deleteAirport = (code, etag) => httpDelete(`/airports/${code}`, etag);

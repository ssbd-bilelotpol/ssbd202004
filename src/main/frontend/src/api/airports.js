import { get, post } from './index';
import { useGet } from './hooks';

export const useListAirports = (filterData) => {
    const params = new URLSearchParams(filterData);
    Object.keys(filterData).forEach((key) => {
        if (filterData[key] === '') {
            params.delete(key);
        }
    });
    return useGet(`/airports?${params.toString()}`);
};

export const getCountries = async () => get('/airports/countries');

export const addAirport = async (data) => post('/airports', data);

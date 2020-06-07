import { get, post } from './index';

export const listAirports = async (filterData) => {
    const params = new URLSearchParams(filterData);
    Object.keys(filterData).forEach((key) => {
        if (filterData[key] === '') {
            params.delete(key);
        }
    });
    return get(`/airports?${params.toString()}`);
};

export const getCountries = async () => get('/airports/countries');

export const addAirport = async (data) => post('/airports', data);

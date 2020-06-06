import { get } from './index';

export const listAirports = async (filterData) => {
    if (!filterData) {
        return get('/airports');
    }

    const params = new URLSearchParams(filterData);
    return get(`/airports?${params.toString()}`);
};

export const getCountries = async () => get('/airports/countries');

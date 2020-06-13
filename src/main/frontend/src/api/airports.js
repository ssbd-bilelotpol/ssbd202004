import { post } from './index';
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

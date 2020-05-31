import { get, post } from './index';

export const createAirport = (data) => {
    return post(`/airports`, data);
};

export const listAirports = async (query) =>
    get(
        `/airports?name=${query.name || ''}&code=${query.code || ''}&country=${
            query.country || ''
        }&city=${query.city || ''}`
    );

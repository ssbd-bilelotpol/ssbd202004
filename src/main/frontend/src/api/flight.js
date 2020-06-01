import { get, post } from './index';

export const createFlight = (data) => {
    return post(`/flights`, data);
};

export const listFlights = async (query) =>
    get(
        `/flights?connection=${query.connection || ''}&code=${query.code || ''}&startDateTime=${
            query.startDateTime || ''
        }`
    );

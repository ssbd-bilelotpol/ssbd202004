import { post } from './index';

export const createAirport = (data) => {
    return post(`/airports`, data);
};

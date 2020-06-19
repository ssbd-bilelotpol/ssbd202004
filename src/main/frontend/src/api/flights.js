import { post, put } from './index';
import { useGet } from './hooks';

export const createFlight = (data) => post(`/flights`, data);

export const useFlights = (filterData, shouldExecute = true) => {
    const params = new URLSearchParams(filterData);
    Object.keys(filterData).forEach((key) => {
        if (filterData[key] == null || filterData[key] === '') {
            params.delete(key);
        }
    });
    const value = useGet(`/flights?${params.toString()}`, [], shouldExecute);
    return {
        ...value,
        data: value.data.map((v) => ({
            ...v,
            startDateTime: new Date(v.startDateTime),
            endDateTime: new Date(v.endDateTime),
        })),
    };
};

export const useFindFlight = (code, shouldExecute = true) => {
    return useGet(`/flights/${code}`, null, shouldExecute);
};

export const useListFlightDates = (filterData) => {
    const params = new URLSearchParams(filterData);
    return useGet(`/flights/dates?${params.toString()}`, []);
};

export const useTakenSeats = (flightCode) => {
    return useGet(`/flights/${flightCode}/taken-seats`, null);
};

export const useFlight = (code) => {
    const value = useGet(`/flights/${code}`);
    return {
        ...value,
        data: Object.keys(value.data).length !== 0 && {
            ...value.data,
            startDateTime: new Date(value.data.startDateTime),
            endDateTime: new Date(value.data.endDateTime),
        },
    };
};

export const updateFlight = (code, data, etag) => put(`/flights/${code}`, data, etag);

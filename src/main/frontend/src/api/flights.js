import { post } from './index';
import { useGet } from './hooks';

export const createFlight = (data) => post(`/flights`, data);

export const useFlights = (filterData) => {
    const params = new URLSearchParams(filterData);
    Object.keys(filterData).forEach((key) => {
        if (filterData[key] == null || filterData[key] === '') {
            params.delete(key);
        }
    });
    const value = useGet(`/flights?${params.toString()}`, []);
    return {
        ...value,
        data: value.data.map((v) => ({
            ...v,
            startDateTime: new Date(v.startDateTime),
            endDateTime: new Date(v.endDateTime),
        })),
    };
};

export const useListFlightDates = () => {
    return {
        data: [new Date('2020-02-20'), new Date('2020-02-21')],
        error: undefined,
        loading: false,
    };
};

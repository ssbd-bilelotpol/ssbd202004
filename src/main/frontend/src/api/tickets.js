import { httpDelete, post } from './index';
import { useGet } from './hooks';

export const useTickets = (filterData, shouldExecute = true) => {
    const params = new URLSearchParams(filterData);
    Object.keys(filterData).forEach((key) => {
        if (filterData[key] == null || filterData[key] === '') {
            params.delete(key);
        }
    });
    const value = useGet(`/tickets/list?${params.toString()}`, [], shouldExecute);
    return {
        ...value,
        data: value.data.map((v) => ({
            ...v,
            startDateTime: new Date(v.startDateTime),
            endDateTime: new Date(v.endDateTime),
        })),
    };
};

export const buyTickets = (data) => post(`/tickets`, data);
export const returnTicket = (id) => httpDelete(`/tickets/${id}`);

export const useTicket = (id) => useGet(`/tickets/${id}`, null);
export const useOwnTickets = () => useGet(`/accounts/self/tickets`, null);

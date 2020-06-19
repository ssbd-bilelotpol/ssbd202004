import { post } from './index';
import { useGet } from './hooks';

export const buyTickets = (data) => post(`/tickets`, data);

export const useTicket = (id) => useGet(`/tickets/${id}`, null);
export const useOwnTickets = () => useGet(`/accounts/self/tickets`, null);

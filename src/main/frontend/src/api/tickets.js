import { post } from './index';

export const buyTickets = (data) => post(`/tickets`, data);

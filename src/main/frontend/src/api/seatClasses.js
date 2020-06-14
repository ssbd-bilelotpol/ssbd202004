import { post } from './index';
import { useGet } from './hooks';

export const useSeatClasses = () => useGet(`/seat-classes`, null);

export const createSeatClass = (data) => {
    return post(`/seat-classes`, data);
};

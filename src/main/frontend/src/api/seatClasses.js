import { post, put } from './index';
import { useGet } from './hooks';

export const useSeatClass = (name) => useGet(`/seat-classes/${name}`);

export const useBenefits = () => useGet(`/seat-classes/benefits`);

export const useSeatClasses = () => useGet(`/seat-classes`, null);

export const createSeatClass = (data) => {
    return post(`/seat-classes`, data);
};

export const editSeatClass = (name, data, etag) => put(`/seat-classes/${name}`, data, etag);

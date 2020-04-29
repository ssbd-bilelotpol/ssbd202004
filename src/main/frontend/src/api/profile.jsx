import { useGet } from './hooks';
import { put } from './index';

export const useCurrentAccountDetails = () => {
    return useGet('/accounts/self');
};

export const editCurrentAccountDetails = (details, etag) => put('/accounts/self', details, etag);

export const useAccountDetails = (login) => {
    return useGet(`/accounts/${login}`);
};

export const editAccountDetails = (login, details, etag) =>
    put(`/accounts/${login}`, details, etag);

import { get, put } from './index';

export const listAccountsApi = async () => get(`/accounts`);

export const changeAccountActiveState = async (login, active, etag) =>
    put(`/accounts/${login}/active`, active, etag);

export const accountAuthReport = async () => get(`/accounts/auth-info`);

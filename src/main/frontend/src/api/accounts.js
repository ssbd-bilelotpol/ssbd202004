import { get, put } from './index';

export const listAccountsApi = async (name) => get(`/accounts?name=${name || ''}`);

export const changeAccountActiveState = async (login, active, etag) =>
    put(`/accounts/${login}/active`, active, etag);

export const accountAuthReport = async () => get(`/accounts/auth-info`);

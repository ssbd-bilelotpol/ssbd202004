import { get } from './index';

export const listAccountsApi = async () => get(`/accounts`);

export const accountAuthReport = async () => get(`/accounts/auth-report`);

import { listAccountsApi, accountAuthReport } from '../api/accounts';
import {
    ACTION_GET_ALL_ACCOUNTS,
    ACTION_GET_ALL_ACCOUNTS_FAILURE,
    ACTION_GET_ALL_ACCOUNTS_BEGIN,
    ACTION_GET_ACCOUNT_AUTH_REPORT_BEGIN,
    ACTION_GET_ACCOUNT_AUTH_REPORT,
    ACTION_GET_ACCOUNT_AUTH_REPORT_FAILURE,
} from './index';

export const getAllAccountsAction = () => async (dispatch) => {
    const begin = () => ({
        type: ACTION_GET_ALL_ACCOUNTS_BEGIN,
    });

    const success = (users) => ({
        type: ACTION_GET_ALL_ACCOUNTS,
        payload: {
            users,
        },
    });

    const failure = (error) => ({
        type: ACTION_GET_ALL_ACCOUNTS_FAILURE,
        payload: error,
    });

    dispatch(begin());
    try {
        dispatch(success(await listAccountsApi()));
    } catch (err) {
        dispatch(failure(err));
    }
};

export const getAccountAuthReport = () => async (dispatch) => {
    const begin = () => ({
        type: ACTION_GET_ACCOUNT_AUTH_REPORT_BEGIN,
    });

    const success = (users) => ({
        type: ACTION_GET_ACCOUNT_AUTH_REPORT,
        payload: {
            users,
        },
    });

    const failure = (error) => ({
        type: ACTION_GET_ACCOUNT_AUTH_REPORT_FAILURE,
        payload: error,
    });

    dispatch(begin());
    try {
        dispatch(success(await accountAuthReport()));
    } catch (err) {
        dispatch(failure(err));
    }
};

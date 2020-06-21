import jwtDecode from 'jwt-decode';
import { changeRoleApi, loginApi, refreshTokenApi } from '../api/auth';
import {
    ACTION_CHANGE_ROLE,
    ACTION_LOGIN_BEGIN,
    ACTION_LOGIN_FAILURE,
    ACTION_LOGIN_SUCCESS,
    ACTION_LOGOUT,
    ACTION_REFRESH_TOKEN,
} from './index';

export const changeRoleAction = (role) => async (dispatch) => {
    const change = (role) => ({
        type: ACTION_CHANGE_ROLE,
        payload: {
            role,
        },
    });

    dispatch(change(role));
    await changeRoleApi(role);
};

export const refreshTokenAction = () => async (dispatch) => {
    const refresh = (user, tokenExp) => ({
        type: ACTION_REFRESH_TOKEN,
        payload: {
            user,
            tokenExp,
        },
    });
    const user = await refreshTokenApi();
    const tokenExp = jwtDecode(user.token).exp;
    dispatch(refresh(user, tokenExp));
};

export const logoutAction = () => (dispatch) => {
    dispatch({
        type: ACTION_LOGOUT,
    });
};

export const loginAction = (username, password) => async (dispatch) => {
    const begin = () => ({
        type: ACTION_LOGIN_BEGIN,
    });

    const success = (user) => ({
        type: ACTION_LOGIN_SUCCESS,
        payload: {
            ...user,
            tokenExp: jwtDecode(user.token).exp,
        },
    });

    const failure = (error) => ({
        type: ACTION_LOGIN_FAILURE,
        payload: error,
        error: true,
    });

    dispatch(begin());
    try {
        dispatch(success(await loginApi(username, password)));
    } catch (err) {
        dispatch(failure(err));
    }
};

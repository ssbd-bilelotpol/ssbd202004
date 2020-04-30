import {
    ACTION_CHANGE_ROLE,
    ACTION_LOGIN_BEGIN,
    ACTION_LOGIN_FAILURE,
    ACTION_LOGIN_SUCCESS,
    ACTION_LOGOUT,
} from './index';
import { changeRoleApi, loginApi } from '../api/auth';

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

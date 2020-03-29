import {rolePriority} from "../constants";

export const ACTION_LOGIN_BEGIN = 'LOGIN_BEGIN';
export const ACTION_LOGIN_SUCCESS = 'LOGIN_SUCCESS';
export const ACTION_LOGIN_FAILURE = 'LOGIN_FAILURE';
export const ACTION_LOGOUT = 'LOGOUT';
export const ACTION_OPEN_AUTH_MODAL = 'OPEN_AUTH_MODAL';
export const ACTION_CLOSE_AUTH_MODAL = 'CLOSE_AUTH_MODAL';
export const ACTION_CHANGE_ROLE = 'CHANGE_ROLE';

export const openAuthModalAction = () => ({
    type: ACTION_OPEN_AUTH_MODAL
});

export const closeAuthModalAction = () => ({
    type: ACTION_CLOSE_AUTH_MODAL
});

export const changeRoleAction = (role) => ({
    type: ACTION_CHANGE_ROLE,
    payload: {
        role
    }
});

export const logoutAction = () => dispatch => {
    localStorage.removeItem("user");
    dispatch({
        type: ACTION_LOGOUT
    });
};

export const loginAction = (username, password) => dispatch => {
    const begin = () => ({
        type: ACTION_LOGIN_BEGIN,
    });

    const success = (user) => ({
        type: ACTION_LOGIN_SUCCESS,
        payload: {
            ...user
        }
    });

    const failure = (error) => ({
        type: ACTION_LOGIN_FAILURE,
        error
    });

    dispatch(begin());
    return fetch(`${process.env.REACT_APP_API_URL}/auth`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username,
            password
        })
    }).then(res => {
        if (!res.ok) {
            const err = new Error('Nieprawidłowy login lub hasło');
            err.custom = true;
            throw err;
        }
        return res.json()
    }).then(u => {
        u.authorities.sort((a, b) => (rolePriority[a] - rolePriority[b]));
        const user = {
            ...u,
            role: u.authorities[0]
        };
        localStorage.setItem("user", JSON.stringify(user));
        dispatch(success(user));
    }).catch((err) => {
        if (err.custom) {
            dispatch(failure(err.message));
        } else {
            dispatch(failure('Brak połączenia z serwerem'));
        }

    });
};
import axios from 'axios';

export const ACTION_LOGIN_BEGIN = 'LOGIN_BEGIN';
export const ACTION_LOGIN_SUCCESS = 'LOGIN_SUCCESS';
export const ACTION_LOGIN_FAILURE = 'LOGIN_FAILURE';
export const ACTION_LOGOUT = 'LOGOUT';
export const ACTION_OPEN_AUTH_MODAL = 'OPEN_AUTH_MODAL';
export const ACTION_CLOSE_AUTH_MODAL = 'CLOSE_AUTH_MODAL';

export const openAuthModalAction = () => ({
    type: ACTION_OPEN_AUTH_MODAL
});

export const closeAuthModalAction = () => ({
    type: ACTION_CLOSE_AUTH_MODAL
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
    return axios.post(`${process.env.REACT_APP_API_URL}/auth`, {
        username,
        password
    }).then(response => {
        localStorage.setItem("user", JSON.stringify(response.data));
        dispatch(success(response.data));
    }).catch(err => {
        if (err.response) {
            dispatch(failure(err.response.data.message));
        } else {
            dispatch(failure('Service is unavailable'));
        }
    });
};
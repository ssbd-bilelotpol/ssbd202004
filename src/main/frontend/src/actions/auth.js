import axios from 'axios';

export const ACTION_LOGIN_BEGIN = 'LOGIN_BEGIN';
export const ACTION_LOGIN_SUCCESS = 'LOGIN_SUCCESS';
export const ACTION_LOGIN_FAILURE = 'LOGIN_FAILURE';

export const login = (login, password) => dispatch => {
    const begin = () => ({
        type: ACTION_LOGIN_BEGIN,
    });

    const success = (accessToken) => ({
        type: ACTION_LOGIN_SUCCESS,
        payload: {
            accessToken
        }
    });

    const failure = (error) => ({
        type: ACTION_LOGIN_FAILURE,
        error
    });

    dispatch(begin());
    return axios.post("http://localhost:8080/api/auth", {
        username: login,
        password
    }).then(response => {
        const { token } = response.data;
        localStorage.setItem("accessToken", token);
        dispatch(success(token))
    }).catch(err => {
        if (err.response) {
            dispatch(failure(err.response.data.message));
        } else {
            dispatch(failure('Service is unavailable'));
        }
    });
};
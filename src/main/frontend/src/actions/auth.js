import {rolePriority} from "../constants";
import {changeRoleApi, loginApi} from "../api";

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

export const changeRoleAction = (role) => dispatch => {

    const change = (role) => ({
        type: ACTION_CHANGE_ROLE,
        payload: {
            role
        }
    });

     return changeRoleApi(role).then(res => {
         if (!res.ok) {
             const err = new Error("Błąd dostępu");
             err.custom = true;
             throw err;
         }
     }).then( () => {
         dispatch(change(role));
     }).catch( err => {
         console.log(err.message);
         if (err.custom) {
             // dispatch(failure(err.message));
         } else {
             // dispatch(failure("Brak połączenia z serwerem"));
         }
     });
};

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
    return loginApi(username, password).then(res => {
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
        dispatch(success(user));
    }).catch((err) => {
        if (err.custom) {
            dispatch(failure(err.message));
        } else {
            dispatch(failure('Brak połączenia z serwerem'));
        }

    });
};

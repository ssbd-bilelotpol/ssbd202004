import {
    ACTION_CLOSE_AUTH_MODAL,
    ACTION_LOGIN_BEGIN,
    ACTION_LOGIN_FAILURE,
    ACTION_LOGIN_SUCCESS,
    ACTION_LOGOUT,
    ACTION_OPEN_AUTH_MODAL
} from "../actions/auth";

const user = JSON.parse(localStorage.getItem("user"));
const clearState = {
    principal: '',
    authorities: '',
    token: '',
    loggingIn: false,
    loggedIn: false,
    openModal: false
};

const initialState = user ? {
    ...user,
    openModal: false,
    loggedIn: true,
    loggingIn: false
} : clearState;

export default function auth(state = initialState, action) {
    switch (action.type) {
        case ACTION_OPEN_AUTH_MODAL:
            return {
                ...state,
                openModal: true
            };
        case ACTION_CLOSE_AUTH_MODAL:
            return {
                ...state,
                openModal: false
            };
        case ACTION_LOGIN_BEGIN:
            return {
                ...state,
                loggedIn: false,
                loggingIn: true,
            };
        case ACTION_LOGIN_SUCCESS:
            return {
                ...state,
                loggingIn: false,
                openModal: false,
                loggedIn: true,
                ...action.payload
            };
        case ACTION_LOGIN_FAILURE:
            return {
                ...state,
                loggingIn: false,
                loggedIn: false,
                error: action.error
            };
        case ACTION_LOGOUT:
            return {
                ...clearState
            };
        default:
            return state;
    }
}
import {ACTION_LOGIN_BEGIN, ACTION_LOGIN_FAILURE, ACTION_LOGIN_SUCCESS} from "../actions/auth";

const accessToken = localStorage.getItem("accessToken");
const initialState = accessToken ? {
    accessToken,
    loggedIn: true,
    loggingIn: false
} : {
    accessToken: '',
    loggingIn: false,
    loggedIn: false
};

export default function auth(state = initialState, action) {
    switch (action.type) {
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
                loggedIn: true,
                accessToken: action.payload.accessToken
            };
        case ACTION_LOGIN_FAILURE:
            return {
                ...state,
                loggingIn: false,
                loggedIn: false,
                error: action.error
            };
        default:
            return state;
    }
}
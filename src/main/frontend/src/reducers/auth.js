import {
    ACTION_CHANGE_ROLE,
    ACTION_CLOSE_AUTH_MODAL,
    ACTION_LOGIN_BEGIN,
    ACTION_LOGIN_FAILURE,
    ACTION_LOGIN_SUCCESS,
    ACTION_LOGOUT,
    ACTION_OPEN_AUTH_MODAL
} from "../actions/auth";

const initialState = {
    user: {
        principal: '',
        authorities: '',
        token: '',
        role: ''
    },
    loggingIn: false,
    loggedIn: false,
    openModal: false
};


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
                openModal: false,
                error: ''
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
                user: {
                    ...action.payload
                }
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
                ...initialState
            };
        case ACTION_CHANGE_ROLE:
            return {
                ...state,
                user: {
                    ...state.user,
                    role: action.payload.role
                }
            };
        default:
            return state;
    }
}

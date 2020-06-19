import {
    ACTION_CHANGE_ROLE,
    ACTION_LOGIN_BEGIN,
    ACTION_LOGIN_FAILURE,
    ACTION_LOGIN_SUCCESS,
    ACTION_LOGOUT,
    ACTION_REFRESH_TOKEN,
} from '../actions';

const initialState = {
    user: {
        principal: '',
        roles: [],
        token: '',
        role: '',
    },
    error: null,
    loggedIn: false,
};

export default function auth(state = initialState, action) {
    switch (action.type) {
        case ACTION_LOGIN_BEGIN:
            return {
                ...state,
                loggedIn: false,
                error: null,
            };
        case ACTION_LOGIN_SUCCESS:
            return {
                ...state,
                tokenExp: action.payload.tokenExp,
                loggedIn: true,
                user: {
                    ...action.payload,
                },
                error: null,
                redirect: action.payload.redirect,
            };
        case ACTION_LOGIN_FAILURE:
            return {
                ...state,
                loggedIn: false,
                error: action.payload,
            };
        case ACTION_LOGOUT:
            return {
                ...initialState,
            };
        case ACTION_CHANGE_ROLE:
            return {
                ...state,
                user: {
                    ...state.user,
                    role: action.payload.role,
                },
            };
        case ACTION_REFRESH_TOKEN:
            return {
                ...state,
                loggedIn: true,
                tokenExp: action.payload.tokenExp,
                user: {
                    ...state.user,
                    ...action.payload.user,
                },
                error: null,
            };
        default:
            return state;
    }
}

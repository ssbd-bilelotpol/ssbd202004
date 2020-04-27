import {
    ACTION_CHANGE_ROLE,
    ACTION_LOGIN_BEGIN,
    ACTION_LOGIN_FAILURE,
    ACTION_LOGIN_SUCCESS,
    ACTION_LOGOUT,
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
                loggedIn: true,
                user: {
                    ...action.payload,
                },
                error: null,
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
        default:
            return state;
    }
}

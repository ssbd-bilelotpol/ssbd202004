import {
    ACTION_GET_ALL_ACCOUNTS,
    ACTION_GET_ALL_ACCOUNTS_FAILURE,
    ACTION_GET_ALL_ACCOUNTS_BEGIN,
} from '../actions';

const initialState = {
    users: [],
    error: null,
    isLoadingAccounts: true,
};

export default function accounts(state = initialState, action) {
    switch (action.type) {
        case ACTION_GET_ALL_ACCOUNTS_BEGIN:
            return {
                ...state,
            };
        case ACTION_GET_ALL_ACCOUNTS:
            return {
                ...state,
                users: action.payload.users,
                isLoadingAccounts: false,
            };
        case ACTION_GET_ALL_ACCOUNTS_FAILURE:
            return {
                ...state,
                error: action.payload,
                isLoadingAccounts: false,
            };
        default:
            return state;
    }
}

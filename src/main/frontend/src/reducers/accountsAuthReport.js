import {
    ACTION_GET_ACCOUNT_AUTH_REPORT,
    ACTION_GET_ACCOUNT_AUTH_REPORT_FAILURE,
    ACTION_GET_ACCOUNT_AUTH_REPORT_BEGIN,
} from '../actions';

const initialState = {
    users: [],
    error: null,
    isLoadingAccounts: true,
};

export default function accountsAuthReport(state = initialState, action) {
    switch (action.type) {
        case ACTION_GET_ACCOUNT_AUTH_REPORT_BEGIN:
            return {
                isLoadingAccounts: true,
                ...state,
            };
        case ACTION_GET_ACCOUNT_AUTH_REPORT:
            return {
                ...state,
                users: action.payload.users.content,
                isLoadingAccounts: false,
            };
        case ACTION_GET_ACCOUNT_AUTH_REPORT_FAILURE:
            return {
                ...state,
                error: action.payload,
                isLoadingAccounts: false,
            };
        default:
            return state;
    }
}

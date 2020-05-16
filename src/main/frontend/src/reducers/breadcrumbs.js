import { ACTION_SET_BREADCRUMB_VARIABLES } from '../actions';

const initialState = {
    breadcrumbs: {
        variables: {},
    },
};

export default function breadcrumbs(state = initialState, action) {
    switch (action.type) {
        case ACTION_SET_BREADCRUMB_VARIABLES:
            return {
                breadcrumbs: {
                    ...state.breadcrumbs,
                    variables: {
                        ...state.breadcrumbs.variables,
                        ...action.payload.variables,
                    },
                },
            };
        default:
            return state;
    }
}

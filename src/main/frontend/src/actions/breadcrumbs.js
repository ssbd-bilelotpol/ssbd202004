import { ACTION_SET_BREADCRUMB_VARIABLES } from './index';

export const setBreadcrumbVariable = (variables) => (dispatch) => {
    const change = (variables) => ({
        type: ACTION_SET_BREADCRUMB_VARIABLES,
        payload: {
            variables,
        },
    });

    dispatch(change(variables));
};

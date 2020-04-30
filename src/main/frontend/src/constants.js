export const roles = {
    admin: 'admin',
    manager: 'manager',
    customerService: 'customer_service',
    client: 'client',
};

export const roleColors = {
    admin: '#b52738',
    manager: '#2e9ba8',
    customer_service: '#3b866e',
    client: '',
};

export const errors = {
    api: {
        connection: 'error.rest.connection',
    },
};

export const rolePriority = {
    [roles.admin]: 0,
    [roles.manager]: 1,
    [roles.customerService]: 2,
    [roles.client]: 3,
};

export const urls = {
    roles: {
        [roles.admin]: '/admin',
        [roles.manager]: '/manager',
        [roles.customerService]: '/customer-service',
        [roles.client]: '/',
    },
    pages: {
        admin: {
            accounts: {
                edit: '/admin/accounts/:login/edit',
            },
        },
        panel: {
            root: '/panel',
            dashboard: '/panel/dashboard',
            reservations: '/panel/reservations',
        },
        user: {
            root: '/user',
            settings: '/user/settings',
        },
        confirm: '/confirm/:token',
        resetPassword: '/resetPassword/:token',
    },
};

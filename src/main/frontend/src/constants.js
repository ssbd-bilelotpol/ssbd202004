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

export const routes = {
    [roles.admin]: {
        title: roles.admin,
        path: '/admin',
        breadcrumb: roles.admin,
        subroutes: {
            accounts: {
                path: '/accounts',
                breadcrumb: 'Accounts',
                subroutes: {
                    list: {
                        title: 'List accounts',
                        path: '/list',
                        breadcrumb: 'List accounts',
                    },
                    authReport: {
                        title: 'Accounts auth report',
                        path: '/auth-report',
                        breadcrumb: 'Accounts auth report',
                    },
                    user: {
                        path: '/:login',
                        breadcrumb: '{fullName|Loading}',
                        breadcrumbLink: false,
                        subroutes: {
                            edit: {
                                title: 'Edit profile',
                                path: '/edit',
                                breadcrumb: 'Edit',
                            },
                        },
                    },
                },
            },
        },
    },
    panel: {
        title: 'Panel',
        path: '/panel',
        breadcrumb: 'Panel',
        subroutes: {
            dashboard: {
                title: 'Dashboard',
                path: '/dashboard',
                breadcrumb: 'Dashboard',
            },
            reservations: {
                title: 'My reservations',
                path: '/reservations',
                breadcrumb: 'My reservations',
            },
        },
    },
    user: {
        path: '/user',
        breadcrumb: 'User',
        subroutes: {
            settings: {
                title: 'Settings',
                path: '/settings',
                breadcrumb: 'Settings',
                subroutes: {
                    changePassword: {
                        title: "Change account's password",
                        path: '/changePassword',
                        breadcrumb: "Change account's password",
                    },
                },
            },
        },
    },
    resetPassword: {
        path: '/resetPassword/:token',
        title: "Change account's password",
        breadcrumb: "Change account's password",
    },
    confirm: {
        path: '/confirm/:token',
        title: 'Confirm account',
        breadcrumb: 'Confirm account',
    },
    [roles.manager]: {
        path: '/manager',
    },
    [roles.customerService]: {
        path: '/customer-service',
    },
    [roles.client]: {
        path: '/',
    },
};

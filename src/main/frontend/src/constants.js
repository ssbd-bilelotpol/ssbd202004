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

export const seatClassColors = [
    'RED',
    'ORANGE',
    'YELLOW',
    'OLIVE',
    'GREEN',
    'TEAL',
    'BLUE',
    'VIOLET',
    'PURPLE',
    'PINK',
    'BROWN',
    'GREY',
    'BLACK',
];

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
    flights: {
        path: '/flights',
        subroutes: {
            purchase: {
                path: '/purchase',
                subroutes: {
                    success: {
                        path: '/success',
                    },
                },
            },
        },
    },
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
            tickets: {
                title: 'My tickets',
                path: '/tickets',
                breadcrumb: 'My tickets',
                subroutes: {
                    view: {
                        title: 'View ticket',
                        breadcrumb: 'View ticket',
                        path: '/:id',
                    },
                },
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
        title: roles.manager,
        path: '/manager',
        breadcrumb: roles.manager,
        subroutes: {
            airports: {
                path: '/airports',
                breadcrumb: 'Airports',
                subroutes: {
                    list: {
                        title: 'List airports',
                        path: '/list',
                        breadcrumb: 'List airports',
                    },
                    add: {
                        title: 'Add airport',
                        path: '/add',
                        breadcrumb: 'New airport',
                    },
                    airport: {
                        path: '/:code',
                        breadcrumb: '{airportName|Loading}',
                        breadcrumbLink: false,
                        subroutes: {
                            edit: {
                                path: '/edit',
                                breadcrumb: 'Edit airport',
                                title: 'Edit airport',
                            },
                        },
                    },
                },
            },
            connections: {
                path: '/relations',
                breadcrumb: 'Relations',
                subroutes: {
                    list: {
                        title: 'List relations',
                        path: '/list',
                        breadcrumb: 'List relations',
                    },
                    add: {
                        title: 'Add relation',
                        path: '/add',
                        breadcrumb: 'New relation',
                    },
                    connection: {
                        path: '/:id',
                        breadcrumb: '{name|Loading}',
                        breadcrumbLink: false,
                        subroutes: {
                            edit: {
                                title: 'Edit relation',
                                path: '/edit',
                                breadcrumb: 'Edit',
                            },
                        },
                    },
                },
            },
            flights: {
                path: '/flights',
                breadcrumb: 'Flights',
                subroutes: {
                    list: {
                        title: 'List flights',
                        path: '/list',
                        breadcrumb: 'List flights',
                    },
                    add: {
                        title: 'Add flight',
                        path: '/add',
                        breadcrumb: 'New flight',
                    },
                    flight: {
                        path: '/:code',
                        breadcrumb: '{code|Loading}',
                        breadcrumbLink: false,
                        subroutes: {
                            edit: {
                                title: 'Edit flight',
                                path: '/edit',
                                breadcrumb: 'Edit',
                            },
                        },
                    },
                },
            },
            seatClasses: {
                path: '/seatClasses',
                breadcrumb: 'Seat classes',
                subroutes: {
                    list: {
                        title: 'List seat classes',
                        path: '/list',
                        breadcrumb: 'List seat classes',
                    },
                    add: {
                        title: 'Add seat class',
                        path: '/add',
                        breadcrumb: 'New seat class',
                    },
                    seatClass: {
                        path: '/:name',
                        breadcrumb: '{name|Loading}',
                        breadcrumbLink: false,
                        subroutes: {
                            edit: {
                                title: 'Edit seat class',
                                path: '/edit',
                                breadcrumb: 'Edit',
                            },
                        },
                    },
                },
            },
            planes: {
                path: '/planes',
                breadcrumb: 'Planes',
                subroutes: {
                    list: {
                        title: 'List planes',
                        path: '/list',
                        breadcrumb: 'List planes',
                    },
                    add: {
                        title: 'Add plane',
                        path: '/add',
                        breadcrumb: 'New plane',
                    },
                    plane: {
                        path: '/:id',
                        breadcrumb: '{name|Loading}',
                        breadcrumbLink: false,
                        subroutes: {
                            edit: {
                                title: 'Edit plane',
                                path: '/edit',
                                breadcrumb: 'Edit',
                            },
                        },
                    },
                },
            },
        },
    },
    [roles.customerService]: {
        path: '/customer-service',
        breadcrumb: 'Customer Service',
        title: roles.customerService,
        subroutes: {
            reports: {
                path: '/reports',
                breadcrumbs: 'Reports',
                subroutes: {
                    generate: {
                        title: 'Generate report',
                        breadcrumb: 'Generate report',
                        path: '/generate',
                    },
                },
            },
        },
    },
    [roles.client]: {
        path: '/',
    },
};

export const errorColor = '#9f3a38';
export const errorLighterColor = '#e0b4b4';

export const roles = {
    admin: 'admin',
    manager: 'manager',
    customerService: 'customer_service',
    client: 'client',
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
};

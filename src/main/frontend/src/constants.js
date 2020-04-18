export const roles = {
    admin: "admin",
    manager: "manager",
    customerService: "customer_service",
    client: "client"
};

export const rolePriority = {
    [roles.admin]: 0,
    [roles.manager]: 1,
    [roles.customerService]: 2,
    [roles.client]: 3
};

export const translations = {
    roles: {
        [roles.admin]: "Administrator",
        [roles.manager]: "Menedżer",
        [roles.customerService]: "Obsługa klienta",
        [roles.client]: "Klient"
    }
};


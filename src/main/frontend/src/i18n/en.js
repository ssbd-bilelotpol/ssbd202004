import { errors, roles } from '../constants';

export default {
    en: {
        translation: {
            [roles.admin]: 'Administrator',
            [roles.manager]: 'Manager',
            [roles.customerService]: 'Customer Service',
            [roles.client]: 'Client',
            [errors.api.connection]: 'Server is down. Please, try again later',
        },
    },
};

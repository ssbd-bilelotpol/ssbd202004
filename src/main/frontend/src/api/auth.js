import { rolePriority } from '../constants';
import { post } from './index';

export const changeRoleApi = (role) => post(`/auth/change-role/${role}`);

export const registerApi = (user) => post(`/accounts`, user);

export const confirmApi = (tokenId) => post(`/accounts/confirm/${tokenId}`);

export const refreshTokenApi = async () => {
    const jwt = await post(`/auth/refresh-token`);
    return {
        token: jwt.token,
        roles: jwt.authorities.sort((a, b) => rolePriority[a] - rolePriority[b]),
    };
};

export const loginApi = async (username, password) => {
    const user = await post(`/auth`, {
        username,
        password,
    });
    user.authorities.sort((a, b) => rolePriority[a] - rolePriority[b]);
    user.roles = user.authorities;
    user.authorities = undefined;

    return {
        ...user,
        role: user.roles[0],
    };
};

export const requestPasswordResetApi = (email) =>
    post(`/accounts/${encodeURIComponent(email)}/password/reset`);

export const resetPasswordApi = (password, token) =>
    post('/accounts/password/reset', { password, token });

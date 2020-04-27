import { errors, rolePriority } from '../constants';
import store from '../store';

async function api(url, config) {
    const { token } = store.getState().auth.user;
    let response;
    try {
        response = await fetch(`${process.env.REACT_APP_API_URL}${url}`, {
            ...config,
            headers: {
                'Content-Type': 'application/json',
                Authorization: token && `Bearer ${token}`,
                ...config.headers,
            },
        });
    } catch (err) {
        throw new Error(errors.api.connection);
    }

    if (!response.ok) {
        const err = await response.json();
        throw new Error(err.message);
    }

    const text = await response.text();
    return text.length > 0 && JSON.parse(text);
}

async function post(url, body) {
    return api(url, {
        method: 'POST',
        body: body && JSON.stringify(body),
    });
}

export const changeRoleApi = (role) => post(`/auth/change-role/${role}`);

export const registerApi = (user) => post(`/accounts`, user);

export const confirmApi = (tokenId) => post(`/accounts/confirm/${tokenId}`);

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

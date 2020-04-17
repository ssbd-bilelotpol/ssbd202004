import {getStore} from "../index";

function fetchWithToken(url, config) {
    const token = getStore().getState().auth.user.token;
    return fetch(url, {...config, headers: {
            ...config.headers,
            'Content-Type': 'application/json',
            'Authorization': token &&  "Bearer " + token
        }});
}

export const changeRoleApi = role => {
    return fetchWithToken(`${process.env.REACT_APP_API_URL}/auth/change-role`, {
        method: "POST",
        body: role
    });
};

export const loginApi = (username, password) => {
    return fetch(`${process.env.REACT_APP_API_URL}/auth`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username,
            password
        })
    });
}

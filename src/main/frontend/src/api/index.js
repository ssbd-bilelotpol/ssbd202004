import { errors } from '../constants';
import store from '../store';

export async function api(url, config) {
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
    return (
        text.length > 0 && {
            content: JSON.parse(text),
            etag: response.headers.get('ETag'),
        }
    );
}

export async function get(url) {
    return api(url, {
        method: 'GET',
    });
}

export async function post(url, body) {
    return api(url, {
        method: 'POST',
        body: body && JSON.stringify(body),
    }).then((result) => result.content);
}

export async function put(url, body, etag) {
    return api(url, {
        method: 'PUT',
        body: body && JSON.stringify(body),
        headers: {
            'If-Match': etag,
        },
    }).then((result) => result.content);
}

export async function httpDelete(url, etag) {
    return api(url, {
        method: 'DELETE',
        headers: {
            'If-Match': etag,
        },
    }).then((result) => result.content);
}
